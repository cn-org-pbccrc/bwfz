package org.packet.packetsimulation.facade.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.openkoala.gqc.infra.util.XmlNode;
import org.openkoala.gqc.infra.util.XmlUtil;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.packet.packetsimulation.application.MesgApplication;
import org.packet.packetsimulation.application.MesgTypeApplication;
import org.packet.packetsimulation.application.MissionApplication;
import org.packet.packetsimulation.application.PacketApplication;
import org.packet.packetsimulation.application.TaskApplication;
import org.packet.packetsimulation.application.TaskPacketApplication;
import org.packet.packetsimulation.application.ThreeStandardApplication;
import org.packet.packetsimulation.core.domain.BatchConfig;
import org.packet.packetsimulation.core.domain.BatchRule;
import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.Mission;
import org.packet.packetsimulation.core.domain.PACKETCONSTANT;
import org.packet.packetsimulation.core.domain.Packet;
import org.packet.packetsimulation.core.domain.Task;
import org.packet.packetsimulation.core.domain.TaskPacket;
import org.packet.packetsimulation.core.domain.ThreeStandard;
import org.packet.packetsimulation.facade.MesgFacade;
import org.packet.packetsimulation.facade.dto.MesgDTO;
import org.packet.packetsimulation.facade.dto.TaskDTO;
import org.packet.packetsimulation.facade.dto.TaskPacketDTO;
import org.packet.packetsimulation.facade.impl.assembler.MesgAssembler;
import org.packet.packetsimulation.facade.impl.assembler.TaskAssembler;
import org.packet.packetsimulation.facade.impl.assembler.TaskPacketAssembler;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Named
public class MesgFacadeImpl implements MesgFacade {

	@Inject
	private MesgApplication  application;
	
	@Inject
	private TaskApplication  taskApplication;
	
	@Inject
	private TaskPacketApplication  taskPacketApplication;
	
	@Inject
	private MesgTypeApplication  mesgTypeApplication;
	
	@Inject
	private PacketApplication  packetApplication;
	
	@Inject
	private ThreeStandardApplication threeStandardApplication;
	
	@Inject
	private MissionApplication  missionApplication;
	
	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getMesg(Long id) {
		MesgDTO dto = MesgAssembler.toDTO(application.getMesg(id));
		MesgType mesgType = mesgTypeApplication.getMesgType(dto.getMesgType());
		try {
			XmlNode xmlNode = XmlUtil.getXmlNodeByXmlContent(dto.getContent(),mesgType.getCountTag());
			dto.setContent(xmlNode.toHtmlTabString(mesgType.getCode()));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return InvokeResult.success(dto);
	}
	
	public InvokeResult creatMesg(MesgDTO mesgDTO) {
		Mesg mesg = MesgAssembler.toEntity(mesgDTO);
		MesgType mesgType = mesgTypeApplication.getMesgType(mesgDTO.getMesgType());
		mesg.setMesgType(mesgType);
		if(mesgDTO.getPacketId()!=null){
			Packet packet = packetApplication.getPacket(mesgDTO.getPacketId());
			packet.setMesgNum(packet.getMesgNum() + 1);
			packetApplication.updatePacket(packet);
			mesg.setPacket(packet);
		}
		application.creatMesg(mesg);
		return InvokeResult.success();
	}
	
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	public InvokeResult creatBatch(MesgDTO mesgDTO,String realPath,int batchNumber) {
		for(int i = 0; i<batchNumber; i++){
			Mesg mesg = MesgAssembler.toEntity(mesgDTO);
			MesgType mesgType = mesgTypeApplication.getMesgType(mesgDTO.getMesgType());
			mesg.setMesgType(mesgType);
			Packet packet = packetApplication.getPacket(mesgDTO.getPacketId());
			mesg.setPacket(packet);
			mesg.setContent(mesgDTO.getNodeValues());
			application.creatMesg(mesg);
		}
		return InvokeResult.success();
	}
	
	/**
	 * 根据选择的三标信息批量
	 */
	public InvokeResult batchMesg(MesgDTO mesgDTO, String[] values,	String userAccount) {
		List<Mesg> mesgs = new ArrayList<Mesg>();
		Mesg mesgById = application.getMesg(mesgDTO.getId());
		String content = mesgById.getContent();
		BatchConfig bc = getBatchConfig(mesgById.getMesgType().getId(), mesgById.getCreateBy());
		List<BatchRule> rules = bc.getBatchRules();
		Packet packet = mesgById.getPacket();
		for (int i = 0; i < values.length; i++) {
			Mesg mesg = new Mesg();
			ThreeStandard threeStandard = threeStandardApplication.getThreeStandard(Long.parseLong(values[i]));
			try {
				content = fillBatchRule(content, rules, i, threeStandard);
			} catch (DocumentException e) {
				e.printStackTrace();
				throw new RuntimeException("xml格式转换失败！", e);
			}
			mesg.setMesgType(mesgById.getMesgType());
			mesg.setPacket(packet);
			mesg.setContent(content);
			mesg.setCreateBy(userAccount);
			mesg.setMesgFrom(0);
			mesgs.add(mesg);
		}
		packet.setMesgNum(packet.getMesgNum() + values.length);
		packetApplication.updatePacket(packet);
		application.creatMesgs(mesgs);
		return InvokeResult.success();
	}
	
	/**
	 * 根据行号起始进行批量
	 */
	public InvokeResult batchMesg(MesgDTO mesgDTO, int startOfThreeStandard, int endOfThreeStandard, String userAccount){
		List<Mesg> mesgs = new ArrayList<Mesg>();
		Mesg mesgById = application.getMesg(mesgDTO.getId());
		String content = mesgById.getContent();
		BatchConfig bc = getBatchConfig(mesgById.getMesgType().getId(), mesgById.getCreateBy());
		List<BatchRule> rules = bc.getBatchRules();
		List<ThreeStandard> list = queryThreeStandardByInput(startOfThreeStandard, endOfThreeStandard, userAccount);		
		Packet packet = mesgById.getPacket();
		for (int i = 0; i < list.size(); i++) {
			Mesg mesg = new Mesg();
			ThreeStandard threeStandard = list.get(i);
			try {
				content = fillBatchRule(content, rules, i, threeStandard);
			} catch (DocumentException e) {
				e.printStackTrace();
				throw new RuntimeException("xml格式转换失败！", e);
			}
			mesg.setMesgType(mesgById.getMesgType());
			mesg.setPacket(packet);
			mesg.setContent(content);
			mesg.setCreateBy(userAccount);
			mesg.setMesgFrom(0);
			mesgs.add(mesg);
		}
		packet.setMesgNum(packet.getMesgNum() + endOfThreeStandard - startOfThreeStandard + 1);
		packetApplication.updatePacket(packet);
		application.creatMesgs(mesgs);
		return InvokeResult.success();
	}
	
	private String fillBatchRule(String content, List<BatchRule> rules, int rowNum , ThreeStandard threeStandard) throws DocumentException{
		Document document = DocumentHelper.parseText(content); 
		
		for (BatchRule rule : rules) {
			if(!rule.getInUse()){
				continue;
			}
			int ruleType = rule.getRuleType();
			String prop = rule.getRuleProperties();
			JSONObject obj = JSON.parseObject(prop);
			Element element = (Element) document.selectNodes("/Document/"+rule.getXpath().replace(".", "/")).get(0);
			switch (ruleType) {
			case 0://自增
				Long start = obj.getLong("startValue");//起始
				int size = obj.getIntValue("stepSize");//步长
				Long total = start + size*rowNum;
				element.setText(total.toString());
				break;
			case 1://数据集
				obj.getString("dickName");
				break;
			case 2://自定义
				JSONObject vProp = JSON.parseObject(obj.getString("custom"));
				String templete =  vProp.getString("templete");//变量模板
				JSONArray variables = vProp.getJSONArray("variables");//变量配置
				for (int i = 0; i < variables.size(); i++) {
					JSONObject variable = (JSONObject) variables.get(i);
					int vType = variable.getInteger("vType");//变量类型
					String vName = variable.getString("vName");//变量名称
					switch (vType) {
					case 0://自增
						Long initValue = variable.getLong("initValue");//初值
						Integer increment = variable.getInteger("increment");//增量
						Long vTotal = initValue + increment*rowNum;
						if(variable.getBoolean("isWidth")){//是否设置宽度
							Integer dataWidth = variable.getInteger("dataWidth");//数值宽度
							String sn=String.format("%0"+dataWidth+"d", vTotal);	    
							templete = templete.replace(vName, sn);
						}else{
							templete = templete.replace(vName, vTotal.toString());
						}
						break;
					case 1://随机
						String randomArea = variable.getString("randomArea");
						String[] values = randomArea.split(",");
						int n =(int) (Math.random()*values.length);
						templete = templete.replace(vName, values[n]);
						break;

					default:
						break;
					}
				}
				element.setText(templete);
				break;
			case 3://三标信息字段
				String col = obj.getString("threeStandardColoum");
				 //反射get方法       
                Method meth;
				try {
					meth = threeStandard.getClass().getMethod(                      
					        "get" 
					+ col.substring(0, 1).toUpperCase()
					+ col.substring(1));
					//执行get方法获取字段值
					String colValue = meth.invoke(threeStandard).toString();   
					element.setText(colValue);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}   
				
			default:
				break;
			}
		}
        System.out.println(document.asXML().replace("\n", ""));
		return document.asXML().replace("\n", "");
	}
	
	public InvokeResult updateMesg(MesgDTO mesgDTO) {		
		Mesg mesg = MesgAssembler.toEntity(mesgDTO);		
		MesgType mesgType = mesgTypeApplication.getMesgType(mesgDTO.getMesgType());
		mesg.setMesgType(mesgType);
		if(mesgDTO.getPacketId()!=null){
			Packet packet = packetApplication.getPacket(mesgDTO.getPacketId());
			mesg.setPacket(packet);
		}
		application.updateMesg(mesg);
		return InvokeResult.success();
	}
	
	public InvokeResult removeMesg(Long id) {
		application.removeMesg(application.getMesg(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeMesgs(Long[] ids) {
		Set<Mesg> mesgs = new HashSet<Mesg>();	
		Mesg mesg = application.getMesg(ids[0]);
		for (Long id : ids) {
			mesgs.add(application.getMesg(id));
		}
		if(mesg.getPacket()!= null){
			Packet packet = packetApplication.getPacket(application.getMesg(ids[0]).getPacket().getId());
			packet.setMesgNum(packet.getMesgNum() - new Long(mesgs.size()));
			packetApplication.updatePacket(packet);
		}else{
			application.removeMesgs(mesgs);
		}
		application.removeMesgs(mesgs);
		return InvokeResult.success();
	}
	
	public List<MesgDTO> findAllMesg() {
		return MesgAssembler.toDTOs(application.findAllMesg());
	}
	
	@SuppressWarnings("unchecked")
	public Page<MesgDTO> pageQueryMesg(MesgDTO queryVo, int currentPage, int pageSize, Long packetId, Integer mesgFrom) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesg from Mesg _mesg   where 1=1 ");
	   	if(!"".equals(queryVo.getMesgFrom())){
	   		jpql.append(" and _mesg.mesgFrom = ? ");
	   		conditionVals.add(mesgFrom);
	   	}	   	
	   	if (packetId != null ) {
	   		jpql.append(" and _mesg.packet.id = ? ");
	   		conditionVals.add(packetId);
	   	}
	   	if (queryVo.getCreateBy() != null && !"".equals(queryVo.getCreateBy().trim()) ) {
	   		jpql.append(" and _mesg.createBy = ? ");
	   		conditionVals.add(queryVo.getCreateBy());
	   	}
	   	if (queryVo.getMesgType() != null ) {
	   		jpql.append(" and _mesg.mesgType.id = ? ");
	   		conditionVals.add(queryVo.getMesgType());
	   	}
	   	if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark().trim())) {
	   		jpql.append(" and _mesg.remark like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
	   	}	
	   	if (queryVo.getContent() != null && !"".equals(queryVo.getContent())) {
	   		jpql.append(" and _mesg.content like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getContent()));
	   	}
        Page<Mesg> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<MesgDTO>(pages.getStart(), pages.getResultCount(),pageSize, MesgAssembler.toDTOs(pages.getData()));
	}
	
	@Override
	public InvokeResult getMesgForUpdate(Long id) {
		Mesg mesg = application.getMesg(id);
		MesgType mesgType = mesgTypeApplication.getMesgType(mesg.getMesgType().getId());
		String content = null;
		try {
			XmlNode xmlNode = XmlUtil.getXmlNodeByXmlContent(mesgType.getXml(),mesgType.getCountTag());
			XmlNode xmlNodeForUpdate = XmlUtil.getXmlNodeByXmlContent(mesg.getContent(),mesgType.getCountTag());
			content = xmlNode.toEditHtmlTabStringForUpdate(mesgType.getCode(), xmlNodeForUpdate);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MesgDTO dto = MesgAssembler.toDTO(mesg);
		dto.setContent(content);
		return InvokeResult.success(dto);
	}

	@Override
	public InvokeResult getMesgForBatch(Long id) {
		MesgDTO dto = MesgAssembler.toDTO(application.getMesg(id));
		return InvokeResult.success(dto);
	}
	
	@SuppressWarnings("unchecked")
	public List<MesgDTO> queryMesgByPacketId(Long packetId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesg from Mesg _mesg   where 1=1 ");
	   	
	   	if (packetId != null ) {
	   		jpql.append(" and _mesg.packet.id = ? ");
	   		conditionVals.add(packetId);
	   	}
	   	List<Mesg> list = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	List<MesgDTO> dtolist = MesgAssembler.toDTOs(list);
	   	return dtolist;
	}
	
	public Long queryCountOfThreeStandard(String currentUserId){
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select count(_threeStandard) from ThreeStandard _threeStandard where 1=1");
		if (currentUserId != null ) {
	   		jpql.append(" and _threeStandard.createdBy = ? ");
	   		conditionVals.add(currentUserId);
	   	}
		Long result = (Long) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
		return result;
	}
	
	public List<ThreeStandard> queryThreeStandardByInput(int startOfThreeStandard,int endOfThreeStandard,String currentUserId){
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _threeStandard from ThreeStandard _threeStandard  where 1=1");
		if (currentUserId != null ) {
	   		jpql.append(" and _threeStandard.createdBy = ? ");
	   		conditionVals.add(currentUserId);
	   	}
		List<ThreeStandard> result = getQueryChannelService().createJpqlQuery(jpql.toString()).setFirstResult(startOfThreeStandard-1).setPageSize(endOfThreeStandard-startOfThreeStandard+1).setParameters(conditionVals).list();
		return result;
	}

	@Override
	public String getMesgForSend(Long[] ids, String mesgType, String userAccount) {
		EmployeeUser employeeUser = findEmployeeUserByCreatedBy(userAccount);
		StringBuffer mesgBuffer = new StringBuffer("");
		String currentOrgNO = employeeUser.getDepartment()!=null?employeeUser.getDepartment().getSn():employeeUser.getCompany().getSn();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String currentDate = dateFormat.format(new Date());
		String counter = String.format("%07d", ids.length);
		mesgBuffer.append("A050").append(mesgType).append("2.0.0").append("10").append(currentOrgNO).append(currentDate).append("0").append(counter).append("\r\n");
		for (Long id : ids) {
			Mesg mesg = application.getMesg(id);
			mesgBuffer.append(mesg.getContent()).append("\r\n");
		}
		return mesgBuffer.toString();
	}
	
	@Override
	public String getFileHeaderForSend(String mesgType, String userAccount) {
		EmployeeUser employeeUser = findEmployeeUserByCreatedBy(userAccount);
		StringBuffer mesgBuffer = new StringBuffer("");
		String currentOrgNO = employeeUser.getDepartment()!=null?employeeUser.getDepartment().getSn():employeeUser.getCompany().getSn();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String currentDate = dateFormat.format(new Date());
		Integer dateType = PACKETCONSTANT.TASKPACKET_DATATYPE_NORMAL;
		String counter = String.format("%010d", 1);
		mesgBuffer.append("A").append(PACKETCONSTANT.TASKPACKET_FILEVERSION).append(currentOrgNO).append(currentDate).append(mesgType).append(dateType).append(counter).append("\r\n");
		return mesgBuffer.toString();
	}
	
	@Transactional
	@Override
	public InvokeResult createTask(TaskDTO taskDTO, TaskPacketDTO taskPacketDTO, String mesgContent, String oriMesgType, String filePath) {
		Task task = TaskAssembler.toEntity(taskDTO);
		Mission mission = missionApplication.getMission(taskDTO.getMissionId());
		task.setTaskFrom(1);
		task.setMission(mission);
		taskApplication.creatTask(task);
		taskPacketDTO.setTaskId(task.getId());		
		EmployeeUser employeeUser = findEmployeeUserByCreatedBy(taskDTO.getTaskCreator());
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		String orgCode = employeeUser.getDepartment()!=null?employeeUser.getDepartment().getSn():employeeUser.getCompany().getSn();
		String selectedBizType;
		if(!oriMesgType.equals("")){
			MesgType mesgType = findMesgTypeByCode(oriMesgType);
			selectedBizType = mesgType.getBizType();
		}else{
			selectedBizType = taskPacketDTO.getSelectedBizType();
		}
		String frontPosition = orgCode
				+ dateFormat.format(new Date())
				+ taskPacketDTO.getSelectedRecordType()
				+ PACKETCONSTANT.TASKPACKET_TRANSPORTDIRECTION_REPORT;
		Integer maxPacketNo = findMaxPacketNumberByFrontPositionAndCreatedBy(frontPosition, taskPacketDTO.getCreatedBy());
		if(maxPacketNo > 998){
			return InvokeResult.failure("流水号最大值为999");
		}
	    String sn = String.format("%03d", maxPacketNo + 1);	    
	    createPacketFile(filePath, frontPosition + sn + "0", mesgContent);
		TaskPacket taskPacket = TaskPacketAssembler.toEntity(taskPacketDTO);
		taskPacket.setTask(task);
		taskPacket.setSelectedOrigSender(orgCode);
		taskPacket.setFrontPosition(frontPosition);
		taskPacket.setPacketNumber(maxPacketNo + 1);
		taskPacket.setSelectedPacketName(frontPosition + sn + "0");
		taskPacket.setSelectedOrigSendDate(new Date());
		taskPacket.setPacketFrom(PACKETCONSTANT.TASKPACKET_PACKETFROM_EASYSEND);
		taskPacket.setSelectedDataType(0);
		taskPacket.setSelectedFileVersion(PACKETCONSTANT.TASKPACKET_FILEVERSION);
		Integer max = findMaxSerialNumber(taskPacketDTO.getTaskId());
		Integer flag = max + 1;
		taskPacket.setSerialNumber(flag);
		taskPacketApplication.creatTaskPacket(taskPacket);
		return InvokeResult.success();
	}
	
	private void createPacketFile(String path, String fileName, String mesgContent) {
		File f = new File(path + fileName + ".csv");//新建一个文件对象
        FileWriter fw;
        try {
        	fw = new FileWriter(f);	    
        	fw.write(mesgContent);
        	fw.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	
	private Integer findMaxPacketNumberByFrontPositionAndCreatedBy(String frontPosition, String createdBy){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select max(_taskPacket.packetNumber) from TaskPacket _taskPacket  where 1=1 ");
	   	
	   	if (frontPosition != null ) {
	   		jpql.append(" and _taskPacket.frontPosition = ? ");
	   		conditionVals.add(frontPosition);
	   	}
	   	if (createdBy != null ) {
	   		jpql.append(" and _taskPacket.createdBy = ? ");
	   		conditionVals.add(createdBy);
	   	}
	   	if((getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult()==null)){
	   		return 0;
	   	}
	   	Integer max = (Integer) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return max;
	}
	
	@SuppressWarnings("unchecked")
	private EmployeeUser findEmployeeUserByCreatedBy(String createdBy){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _employeeUser from EmployeeUser _employeeUser  where 1=1 ");
	   	
	   	if (createdBy != null ) {
	   		jpql.append(" and _employeeUser.userAccount = ? ");
	   		conditionVals.add(createdBy);
	   	}
	   	EmployeeUser employeeUser = (EmployeeUser) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return employeeUser;
	}
	
	private Integer findMaxSerialNumber(Long taskId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select max(_taskPacket.serialNumber) from TaskPacket _taskPacket  where 1=1 ");
	   	
	   	if (taskId != null ) {
	   		jpql.append(" and _taskPacket.task.id = ? ");
	   		conditionVals.add(taskId);
	   	}
	   	if((getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult()==null)){
	   		return 0;
	   	}
	   	Integer max = (Integer) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return max;
	}
	
	public MesgType findMesgTypeByCode(String code) {
		List<Object> conditionVals = new ArrayList<Object>();
	 	StringBuilder jpql = new StringBuilder("select _mesgType from MesgType _mesgType   where 1=1");
	 	if (code != null ) {
	   		jpql.append(" and _mesgType.code =? ");
	   		conditionVals.add(code);
	   	}
	   	MesgType mesgType = (MesgType) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return mesgType;
	}
	
	/**
	 * 查询批量配置
	 * @param mesgTypeId 模板类型ID
	 * @param createBy 创建人
	 * @return BatchConfig
	 */
	@SuppressWarnings("unchecked")
	public BatchConfig getBatchConfig(Long mesgTypeId, String createBy) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _batchConfig from BatchConfig _batchConfig   where 1=1 ");
	   		jpql.append(" and _batchConfig.createdBy = ?");
	   		conditionVals.add(createBy);
	   		jpql.append(" and _batchConfig.mesgType.id = ? ");
	   		conditionVals.add(mesgTypeId);
		return (BatchConfig) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	}
}
