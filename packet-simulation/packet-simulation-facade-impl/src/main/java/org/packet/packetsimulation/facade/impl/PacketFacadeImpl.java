package org.packet.packetsimulation.facade.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.openkoala.gqc.infra.util.ReadAppointedLine;
import org.openkoala.gqc.infra.util.XmlNode;
import org.openkoala.gqc.infra.util.XmlUtil;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.core.domain.Employee;
import org.openkoala.security.core.PacketHeadFormatException;
import org.openkoala.security.core.PacketNameIsExistedException;
import org.openkoala.security.core.UserAccountIsExistedException;
import org.openkoala.security.core.domain.User;
import org.openkoala.security.facade.impl.SecurityConfigFacadeImpl;
import org.openkoala.security.facade.impl.assembler.UserAssembler;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.packet.packetsimulation.application.MesgApplication;
import org.packet.packetsimulation.application.MesgTypeApplication;
import org.packet.packetsimulation.application.MissionApplication;
import org.packet.packetsimulation.application.PacketApplication;
import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.Mission;
import org.packet.packetsimulation.core.domain.PACKETCONSTANT;
import org.packet.packetsimulation.core.domain.Packet;
import org.packet.packetsimulation.core.domain.TaskPacket;
import org.packet.packetsimulation.facade.PacketFacade;
import org.packet.packetsimulation.facade.dto.PacketDTO;
import org.packet.packetsimulation.facade.impl.assembler.PacketAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Named
public class PacketFacadeImpl implements PacketFacade {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PacketFacadeImpl.class);
	
	@Inject
	private PacketApplication  application;
	
	@Inject
	private MesgApplication  mesgApplication;
	
	@Inject
	private MissionApplication  missionApplication;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getPacket(Long id) {
		return InvokeResult.success(PacketAssembler.toDTO(application.getPacket(id)));
	}
	
	public InvokeResult creatPacket(PacketDTO packetDTO, Long missionId) {
		packetDTO.setDataType(0);
		Packet packet = PacketAssembler.toEntity(packetDTO);
		Mission mission = missionApplication.getMission(missionId);
		packet.setMesgNum(0L);
		packet.setMission(mission);
		application.creatPacket(packet);
		return InvokeResult.success();
	}
	
	public InvokeResult updatePacket(PacketDTO packetDTO) {
		Packet packet = PacketAssembler.toEntity(packetDTO);
		Mission mission = missionApplication.getMission(packetDTO.getMissionId());
		packet.setMission(mission);
		application.updatePacket(packet);
		return InvokeResult.success();
	}
	
	public InvokeResult saveAsPacket(PacketDTO packetDTO, String idOfPacket){
		Packet packet = PacketAssembler.toEntity(packetDTO);
		application.updatePacket(packet);
		List<Mesg> mesgsOfSourcePacket = findMesgsByIdOfPacket(Long.valueOf(idOfPacket));
		List<Mesg> mesgs= new ArrayList<Mesg>();
		for(int i = 0; i < mesgsOfSourcePacket.size(); i++){
			Mesg mesg = new Mesg();
			mesg.setMesgType(mesgsOfSourcePacket.get(i).getMesgType());
			mesg.setPacket(packet);
			mesg.setContent(mesgsOfSourcePacket.get(i).getContent());
			mesgs.add(mesg);
		}
		mesgApplication.creatMesgs(mesgs);
		return InvokeResult.success();
	}
	
	public List<Mesg> findMesgsByIdOfPacket(Long idOfPacket){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesg from Mesg _mesg  where 1=1 ");
	   	if (idOfPacket != null ) {
	   		jpql.append(" and _mesg.packet.id = ? ");
	   		conditionVals.add(idOfPacket);
	   	}
	   	List<Mesg> mesgs = (List<Mesg>) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return mesgs;
	}
	
	public InvokeResult removePacket(Long id) {
		application.removePacket(application.getPacket(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removePackets(Long[] ids) {
		Set<Packet> packets= new HashSet<Packet>();
		for (Long id : ids) {
			packets.add(application.getPacket(id));
			List<Mesg> mesgList = findMesgsByPacketId(id);
			Set<Mesg> mesgs = new HashSet<Mesg>();
			mesgs.addAll(mesgList);
			mesgApplication.removeMesgs(mesgs);
		}		
		application.removePackets(packets);
		return InvokeResult.success();
	}
	
	public List<PacketDTO> findAllPacket() {
		return PacketAssembler.toDTOs(application.findAllPacket());
	}
	
	@SuppressWarnings("unchecked")
	public Page<PacketDTO> pageQueryPacket(PacketDTO queryVo, int currentPage, int pageSize, String currentUserId, Long missionId) {
		//String userAccount = CurrentUser.getUserAccount();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _packet from Packet _packet where 1=1 ");	
	   	if (queryVo.getOrigSender() != null && !"".equals(queryVo.getOrigSender())) {
	   		jpql.append(" and _packet.origSender like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getOrigSender()));
	   	}		
	   	if (queryVo.getOrigSendDate() != null) {
	   		jpql.append(" and _packet.origSendDate between ? and ? ");
	   		conditionVals.add(queryVo.getOrigSendDate());
	   		conditionVals.add(queryVo.getOrigSendDateEnd());
	   	}
	   	if (queryVo.getPacketName() != null && !"".equals(queryVo.getPacketName())) {
	   		jpql.append(" and _packet.packetName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPacketName()));
	   	}
	 	if (queryVo.getDataType() != null && !"".equals(queryVo.getDataType())) {
	   		jpql.append(" and _packet.dataType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDataType()));	   			 
	   	}
	 	if (queryVo.getBizType() != null && !"".equals(queryVo.getBizType())) {
	   		jpql.append(" and _packet.bizType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getBizType()));	   			 
	   	}
	 	if (queryVo.getRecordType() != null && !"".equals(queryVo.getRecordType())) {
	   		jpql.append(" and _packet.recordType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRecordType()));	   			 
	   	}
		if (queryVo.getMesgNum() != null && !"".equals(queryVo.getMesgNum())) {
	   		jpql.append(" and _packet.mesgNum like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getMesgNum()));	   			 
	   	}
	   	if (currentUserId != null) {
	   		jpql.append(" and _packet.createdBy = ?");
	   		conditionVals.add(currentUserId);
	   	}
	   	if (missionId != null) {
	   		jpql.append(" and _packet.mission.id = ?");
	   		conditionVals.add(missionId);
	   	}
        Page<Packet> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<PacketDTO>(pages.getStart(), pages.getResultCount(),pageSize, PacketAssembler.toDTOs(pages.getData()));
	}
	
	@Override
	public String downloadCSV(Long packetId) {	
		Packet packet = application.getPacket(packetId);
		Date origSendDate = packet.getOrigSendDate();
   		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
   		String counter;
   		String result;
	   	List<Mesg> mesgList = findMesgsByPacketId(packetId);
	   	if(null!=mesgList && mesgList.size()>0){
	   		counter = fillStringToHead(8,""+mesgList.size(),"0");
	   		result = PACKETCONSTANT.HEADER_START + packet.getFileVersion() + packet.getOrigSender() + dateFormat.format(origSendDate) + packet.getRecordType() + counter + PACKETCONSTANT.HEADER_RESERVED + "\r\n";
	   		for(Mesg mesg : mesgList){  			
				result = result + mesg.getContent() + "\r\n";
	   		}
	   	}else{
	   		counter = fillStringToHead(8,"0","0");
	   		result = PACKETCONSTANT.HEADER_START + packet.getFileVersion() + packet.getOrigSender() + dateFormat.format(origSendDate) + packet.getRecordType() + counter + PACKETCONSTANT.HEADER_RESERVED + "\r\n";
	   	}
		return result;
	}
		
	@SuppressWarnings("unchecked")
	private List<Mesg> findMesgsByPacketId(Long packetId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesg from Mesg _mesg  where 1=1 ");
	   	
	   	if (packetId != null ) {
	   		jpql.append(" and _mesg.packet.id = ? ");
	   		conditionVals.add(packetId);
	   	}
	   	List<Mesg> mesgList = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return mesgList;
	}
	
	private String fillStringToHead(int length,String target,String filler){
		if(null!=target && target.length()<length && null!=filler && !"".equals(filler)){
			String result = "";
			for(int i=target.length();i<length;i++){
				result = result + filler;
			}
			result = result + target;
			return result;
		}
		return target;
	}
	
	@Transactional
	@Override
	public ModelAndView uploadPacket(PacketDTO packetDTO, String ctxPath, String xsdPath, Long missionId) throws IOException, ParseException, ParserConfigurationException, SAXException {
		ModelAndView modelAndView = new ModelAndView("index");
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		Map<String,String> attributes = new HashMap();
		File uploadFile = new File(ctxPath + packetDTO.getPacketName());
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ctxPath + packetDTO.getPacketName()), "UTF-8"));  
		String line = br.readLine();	
		if(line.length() != 85){
			br.close();
			uploadFile.delete();
			attributes.put("error","文件头长度应为85位!");
			view.setAttributesMap(attributes);
			modelAndView.setView(view);
			return modelAndView;
		}
		if(!line.substring(0,1).equals("A")){
			br.close();
			uploadFile.delete();
			attributes.put("error","文件头标识应为'A'!");
			view.setAttributesMap(attributes);
			modelAndView.setView(view);
			return modelAndView;
		}
		String fileVersion = line.substring(1,4);
		if(!fileVersion.matches("^[0-9]+[.][0-9]+$")){
			br.close();
			uploadFile.delete();
			attributes.put("error","文件格式版本号应该为N.N格式数字!");
			view.setAttributesMap(attributes);
			modelAndView.setView(view);			
			return modelAndView;
		}
		packetDTO.setFileVersion(fileVersion);
		String origSender = line.substring(4,18);
		if(!origSender.matches("^[0-9]{14}$")){
			br.close();
			uploadFile.delete();
			attributes.put("error","数据提供机构代码应为14位数字!");
			view.setAttributesMap(attributes);
			modelAndView.setView(view);			
			return modelAndView;
		}
		packetDTO.setOrigSender(origSender);
		String date = line.substring(18,32);
		String dateForVerify = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+"-"+date.substring(10,12)+"-"+date.substring(12,14);
		String reg = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3])-([0-5][0-9])-([0-5][0-9])$";  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
		String dateFormat = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
		Date origSendDate = sdf.parse(dateFormat);
		if(!dateForVerify.matches(reg)){
			br.close();
			uploadFile.delete();
			attributes.put("error","文件生成时间格式不正确!");
			view.setAttributesMap(attributes);
			modelAndView.setView(view);
			return modelAndView;
		}
		packetDTO.setOrigSendDate(origSendDate);
		String recordType = line.substring(32,47);
		if(!recordType.matches("^[.0-9a-z]{15}$")){
			br.close();
			uploadFile.delete();
			attributes.put("error","文件类型应为15位数字!");
			view.setAttributesMap(attributes);
			modelAndView.setView(view);
			return modelAndView;
		}
		if(MesgType.getByCode(recordType) != null && recordType.equals("daic.004.001.01")){
			br.close();
			uploadFile.delete();
			attributes.put("error","文件类型不存在!");
			view.setAttributesMap(attributes);
			modelAndView.setView(view);
			return modelAndView;
		}
		packetDTO.setRecordType(recordType);
		packetDTO.setDataType(0);
		String mesgNum = line.substring(47,55);
		if(!mesgNum.matches("^[0-9]{8}$")){
			br.close();
			uploadFile.delete();
			attributes.put("error","信息记录数应为8位数字!");
			view.setAttributesMap(attributes);
			modelAndView.setView(view);
			return modelAndView;
		}
		packetDTO.setMesgNum(Long.parseLong(mesgNum));
		int totalLines = ReadAppointedLine.getTotalLines(uploadFile);
		if(Integer.parseInt(mesgNum) != (totalLines - 1)){
			br.close();
			uploadFile.delete();
			attributes.put("error","信息记录数和文件中记录行数不一致!");
			view.setAttributesMap(attributes);
			modelAndView.setView(view);
			return modelAndView;
		}
		MesgType mesgType = findMesgTypeByRecordType(recordType);
		Packet packet = PacketAssembler.toEntity(packetDTO);
		Mission mission = missionApplication.getMission(missionId);
		packet.setMission(mission);
		int n = 1;
		List<Mesg> mesgs = new ArrayList<Mesg>();
		while((line=br.readLine())!=null){
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc;
			try{
				doc = (Document) db.parse(new InputSource(new ByteArrayInputStream(line.getBytes("utf-8"))));
			}catch(Exception e){
				e.printStackTrace();
				br.close();
				uploadFile.delete();
				attributes.put("error", "第" + n + "条记录xml格式不正确:" + e.getMessage());
				view.setAttributesMap(attributes);
				modelAndView.setView(view);
				return modelAndView;
			}
			try{
				Validatexml(xsdPath + recordType + ".xsd", line);
				Mesg mesg = new Mesg();
				mesg.setMesgType(mesgType);
				mesg.setPacket(packet);
				mesg.setContent(line);
				mesg.setCreateBy(packet.getCreatedBy());
				mesg.setMesgFrom(0);
				mesgs.add(mesg);
			}catch(Exception e){
				e.printStackTrace();
				br.close();
				uploadFile.delete();
				attributes.put("error", "第" + n + "条记录schema校验不通过:" + e.getMessage());
				view.setAttributesMap(attributes);
				modelAndView.setView(view);
				return modelAndView;
			}
			n++;
		}
		String bizType = "";
		if(!mesgType.getBizType().equals("")){
			bizType = mesgType.getBizType();
		}else{
			String xmlContent = ReadAppointedLine.readAppointedLineNumber(new File(ctxPath + packetDTO.getPacketName()), 2 ,n);
			org.dom4j.Document document;
			try {
				document = org.dom4j.DocumentHelper.parseText(xmlContent);
				org.dom4j.Element root = document.getRootElement();
				Iterator iter1 = root.elementIterator();
				org.dom4j.Element element1 = (org.dom4j.Element) iter1.next();
				Iterator iter2 = element1.elementIterator();
				org.dom4j.Element element2 = (org.dom4j.Element) iter2.next();
				Iterator iter3 = element2.elementIterator();
				org.dom4j.Element element3 = (org.dom4j.Element) iter3.next();
				bizType = element3.getText();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		packet.setBizType(bizType);
		br.close();
		uploadFile.delete();
		application.creatPacket(packet);
		mesgApplication.creatMesgs(mesgs);
		attributes.put("data","上传并解析成功!");
		view.setAttributesMap(attributes);
		modelAndView.setView(view);
		return modelAndView;
	}
	
	private MesgType findMesgTypeByRecordType(String recordType){
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _mesgType from MesgType _mesgType  where 1=1 ");
		if (recordType != null && !"".equals(recordType)) {
			jpql.append(" and _mesgType.code = ? ");
			conditionVals.add(recordType);
		}
		MesgType mesgType = (MesgType) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
		return mesgType;
	}
	
	public static void Validatexml(String xsdpath, String xml) throws SAXException, IOException {
		// 建立schema工厂
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		// 建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
		File schemaFile = new File(xsdpath);
		// 利用schema工厂，接收验证文档文件对象生成Schema对象
		Schema schema = schemaFactory.newSchema(schemaFile);
		// 通过Schema产生针对于此Schema的验证器，利用schenaFile进行验证
		Validator validator = schema.newValidator();
		// 得到验证的数据源
		Reader reader  = new StringReader(xml);
		Source source = new StreamSource(reader);
		// 开始验证，成功输出success!!!，失败输出fail
		validator.validate(source);
	}
}

