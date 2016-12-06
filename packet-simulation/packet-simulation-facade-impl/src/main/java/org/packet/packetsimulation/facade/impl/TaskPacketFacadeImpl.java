package org.packet.packetsimulation.facade.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.impl.assembler.MesgAssembler;
import org.packet.packetsimulation.facade.impl.assembler.TaskPacketAssembler;
import org.packet.packetsimulation.facade.TaskPacketFacade;
import org.packet.packetsimulation.application.MissionApplication;
import org.packet.packetsimulation.application.PacketApplication;
import org.packet.packetsimulation.application.TaskApplication;
import org.packet.packetsimulation.application.TaskPacketApplication;
import org.packet.packetsimulation.core.domain.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Named
public class TaskPacketFacadeImpl implements TaskPacketFacade {

	@Inject
	private TaskPacketApplication  application;
	
	@Inject
	private TaskApplication  taskApplication;
	
	@Inject
	private PacketApplication  packetApplication;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getTaskPacket(Long id) {
		return InvokeResult.success(TaskPacketAssembler.toDTO(application.getTaskPacket(id)));
	}
	
	public InvokeResult creatTaskPacket(TaskPacketDTO taskPacketDTO) {
		TaskPacket taskPacket = TaskPacketAssembler.toEntity(taskPacketDTO);
		Task task = taskApplication.getTask(taskPacketDTO.getTaskId());
		taskPacket.setTask(task);
		application.creatTaskPacket(taskPacket);
		return InvokeResult.success();
	}
	
	public InvokeResult creatTaskPackets(TaskPacketDTO taskPacketDTO, String ctxPath, String[] flags, String[] coms, String[] encs) throws ParseException {
		Set<TaskPacket> taskPackets = new HashSet<TaskPacket>();
		Integer maxSerialNumber = findMaxSerialNumber(taskPacketDTO.getTaskId());
		for (int i = 0; i < flags.length; i++) {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");			
			TaskPacket taskPacket = TaskPacketAssembler.toEntity(taskPacketDTO);
			Task task = taskApplication.getTask(taskPacketDTO.getTaskId());			
			taskPacket.setTask(task);
			Packet packet = packetApplication.getPacket(Long.parseLong(flags[i]));
			String frontPosition = packet.getOrigSender() + dateFormat1.format(packet.getOrigSendDate()) + packet.getRecordType() + "0";
			Integer maxPacketNumber = findMaxPacketNumberByFrontPositionAndCreatedBy(frontPosition, packet.getCreatedBy());
			if(maxPacketNumber > 998){
				return InvokeResult.failure("流水号最大值为999");
			}
			String sn = String.format("%03d", maxPacketNumber + 1);
			taskPacket.setSelectedPacketName(frontPosition + sn + "0");
			taskPacket.setSelectedFileVersion(packet.getFileVersion());
			taskPacket.setSelectedOrigSender(packet.getOrigSender());
			taskPacket.setSelectedOrigSendDate(packet.getOrigSendDate());
			taskPacket.setSelectedDataType(packet.getDataType());
			taskPacket.setSelectedBizType(packet.getBizType());
			taskPacket.setSelectedRecordType(packet.getRecordType());
			taskPacket.setCompression(Integer.valueOf(coms[i]));
			taskPacket.setEncryption(Integer.valueOf(encs[i]));
			taskPacket.setFrontPosition(frontPosition);
			taskPacket.setPacketNumber(maxPacketNumber + 1);
			taskPacket.setSerialNumber(maxSerialNumber + 1);
			taskPacket.setCreatedBy(packet.getCreatedBy());
			maxSerialNumber++;
			taskPackets.add(taskPacket);			
			DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");  
		   	List<Mesg> mesgList = findMesgsByPacketId(Long.parseLong(flags[i]));
			String result = PACKETCONSTANT.HEADER_START + "050" + packet.getRecordType() + packet.getFileVersion() + "10" + packet.getOrigSender() + dateFormat2.format(packet.getOrigSendDate()) + "0" + String.format("%07d", mesgList.size()) + "\r\n";
			if(mesgList != null && mesgList.size() > 0){
		   		for(Mesg mesg : mesgList){  			
					result = result + mesg.getContent() + "\r\n";
		   		}
		   	}
		    File f = new File(ctxPath + frontPosition + sn + ".csv");//新建一个文件对象
	        FileWriter fw;
	        try {
	        	fw = new FileWriter(f);//新建一个FileWriter	    
	        	fw.write(result);//将字符串写入到指定的路径下的文件中
	        	fw.close();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
		application.creatTaskPackets(taskPackets);
		return InvokeResult.success();
	}
	
	public ModelAndView uploadTaskPacket(TaskPacketDTO taskPacketDTO, String ctxPath){
		ModelAndView modelAndView = new ModelAndView("index");
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		Map<String,String> attributes = new HashMap();
		File uploadFile = new File(ctxPath + taskPacketDTO.getSelectedPacketName());
		TaskPacket taskPacket = findTaskPacketBySelectedPacketNameAndPacketFrom(taskPacketDTO.getSelectedPacketName());
		if(taskPacket == null){
			taskPacket = TaskPacketAssembler.toEntity(taskPacketDTO);
			Integer max = findMaxSerialNumber(taskPacketDTO.getTaskId());
			Task task = taskApplication.getTask(taskPacketDTO.getTaskId());			
			taskPacket.setTask(task);
			taskPacket.setCreatedBy(task.getTaskCreator());			
			taskPacket.setPacketFrom(PACKETCONSTANT.TASKPACKET_PACKETFROM_OUTSIDE);
			taskPacket.setSelectedFileVersion("-");
			taskPacket.setSelectedOrigSender("-");			
			taskPacket.setSelectedDataType(0);
			taskPacket.setSelectedBizType("-");
			taskPacket.setSelectedRecordType("-");			
			taskPacket.setCompression(1);
			taskPacket.setEncryption(1);
			taskPacket.setSerialNumber(max + 1);			
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = sdf.format(new Date());
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			uploadFile.delete();
			attributes.put("error","日期转换错误!");
			view.setAttributesMap(attributes);
			modelAndView.setView(view);
			return modelAndView;
		}
		taskPacket.setSelectedOrigSendDate(date);
		application.creatTaskPacket(taskPacket);
		attributes.put("data","上传成功!");
		view.setAttributesMap(attributes);
		modelAndView.setView(view);
		return modelAndView;
	}
	
	public String showPacketContent(Long id, String ctxPath){
		TaskPacket taskPacket = application.getTaskPacket(id);
		String path;
		if(taskPacket.getPacketFrom() == 0){
			path = ctxPath + taskPacket.getTask().getId() + File.separator + "insideFiles" + File.separator + taskPacket.getSelectedPacketName() + ".txt";
		}else if(taskPacket.getPacketFrom() == 1){
			path = ctxPath + taskPacket.getTask().getId() + File.separator + "outsideFiles" + File.separator + taskPacket.getSelectedPacketName();
		}else{
			path = ctxPath + "easySendFiles" + File.separator + taskPacket.getSelectedPacketName() + ".txt";
		}
		StringBuffer sb = new StringBuffer();
		try {
			readToBuffer(sb, path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return sb.toString();
	}
	
	public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }
	
	@SuppressWarnings("unchecked")
	private List<Mesg> findMesgsByPacketId(Long id){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesg from Mesg _mesg  where 1=1 ");
	   	
	   	if (id != null ) {
	   		jpql.append(" and _mesg.packet.id = ? ");
	   		conditionVals.add(id);
	   	}
	   	List<Mesg> mesgList = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return mesgList;
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
	
	@SuppressWarnings("unchecked")
	private TaskPacket findTaskPacketBySelectedPacketNameAndPacketFrom(String selectedPacketName){
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _taskPacket from TaskPacket _taskPacket  where 1=1 ");
		if (selectedPacketName != null && !"".equals(selectedPacketName)) {
			jpql.append(" and _taskPacket.selectedPacketName = ? ");
		   	conditionVals.add(selectedPacketName);
		}
		jpql.append(" and _taskPacket.packetFrom = ? ");
		conditionVals.add(PACKETCONSTANT.TASKPACKET_PACKETFROM_OUTSIDE);
		TaskPacket taskPacket = (TaskPacket) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
		return taskPacket;
	}
	
	public InvokeResult updateTaskPacket(TaskPacketDTO taskPacketDTO) {
		TaskPacket taskPacket = TaskPacketAssembler.toEntity(taskPacketDTO);
		Task task = taskApplication.getTask(taskPacketDTO.getTaskId());
		taskPacket.setTask(task);
		application.updateTaskPacket(taskPacket);
		return InvokeResult.success();
	}
	
	public InvokeResult removeTaskPacket(Long id) {
		application.removeTaskPacket(application.getTaskPacket(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeTaskPackets(Long[] ids, String savePath) {
		for (Long id : ids) {
			TaskPacket taskPacket = application.getTaskPacket(id);
			int packetFrom = (int) taskPacket.getPacketFrom();
			if(packetFrom==PACKETCONSTANT.TASKPACKET_PACKETFROM_INSIDE){
				new File(savePath+taskPacket.getTask().getId()+File.separator+"insideFiles"+File.separator+taskPacket.getSelectedPacketName()+".csv").delete();
				application.removeTaskPacket(taskPacket);
			}else if(packetFrom==PACKETCONSTANT.TASKPACKET_PACKETFROM_OUTSIDE){
				new File(savePath+taskPacket.getTask().getId()+File.separator+"outsideFiles"+File.separator+taskPacket.getSelectedPacketName()).delete();
				application.removeTaskPacket(taskPacket);
			}else{
				new File(savePath+"easySendFiles"+File.separator+taskPacket.getSelectedPacketName()+".csv").delete();
				application.removeTaskPacket(taskPacket);
				taskApplication.removeTask(taskPacket.getTask());
			}						
		}
		return InvokeResult.success();
	}
	
	public List<TaskPacketDTO> findAllTaskPacket() {
		return TaskPacketAssembler.toDTOs(application.findAllTaskPacket());
	}
	
	public InvokeResult upTaskPacket(String sourceId, String destId){
		TaskPacket sourceTaskPacket = application.getTaskPacket(Long.parseLong(sourceId));
		TaskPacket destTaskPacket = application.getTaskPacket(Long.parseLong(destId));
		Integer sourceSerialNumber = sourceTaskPacket.getSerialNumber();
		Integer destSerialNumber = destTaskPacket.getSerialNumber();
		sourceTaskPacket.setSerialNumber(destSerialNumber);
		destTaskPacket.setSerialNumber(sourceSerialNumber);
		application.updateTaskPacket(sourceTaskPacket);
		application.updateTaskPacket(destTaskPacket);
		return InvokeResult.success();
	}
	
	public InvokeResult downTaskPacket(String sourceId, String destId){
		TaskPacket sourceTaskPacket = application.getTaskPacket(Long.parseLong(sourceId));
		TaskPacket destTaskPacket = application.getTaskPacket(Long.parseLong(destId));
		Integer sourceSerialNumber = sourceTaskPacket.getSerialNumber();
		Integer destSerialNumber = destTaskPacket.getSerialNumber();
		sourceTaskPacket.setSerialNumber(destSerialNumber);
		destTaskPacket.setSerialNumber(sourceSerialNumber);
		application.updateTaskPacket(sourceTaskPacket);
		application.updateTaskPacket(destTaskPacket);
		return InvokeResult.success();
	}
	
	@SuppressWarnings("unchecked")
	public Page<TaskPacketDTO> pageQueryTaskPacket(TaskPacketDTO queryVo, int currentPage, int pageSize, Long taskId) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _taskPacket from TaskPacket _taskPacket   where 1=1");
	   	if (taskId != null ) {
	   		jpql.append(" and _taskPacket.task.id = ? ");
	   		conditionVals.add(taskId);
	   	}
	   	if (queryVo.getPacketFrom() != null && !"".equals(queryVo.getPacketFrom())) {
	   		jpql.append(" and _taskPacket.packetFrom like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPacketFrom()));
	   	}	
	   	if (queryVo.getSelectedPacketName() != null && !"".equals(queryVo.getSelectedPacketName())) {
	   		jpql.append(" and _taskPacket.selectedPacketName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSelectedPacketName()));
	   	}
	   	if (queryVo.getSelectedFileVersion() != null && !"".equals(queryVo.getSelectedFileVersion())) {
	   		jpql.append(" and _taskPacket.selectedFileVersion like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSelectedFileVersion()));
	   	}
	   	if (queryVo.getSelectedOrigSender() != null && !"".equals(queryVo.getSelectedOrigSender())) {
	   		jpql.append(" and _taskPacket.selectedOrigSender like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSelectedOrigSender()));
	   	}
	   	if (queryVo.getSelectedOrigSendDate() != null ) {
	   		jpql.append(" and _taskPacket.selectedOrigSendDate between ? and ? ");
	   		conditionVals.add(queryVo.getSelectedOrigSendDate());
	   		conditionVals.add(queryVo.getSelectedOrigSendDateEnd());
	   	}
	   	if (queryVo.getSelectedDataType() != null && !"".equals(queryVo.getSelectedDataType())) {
	   		jpql.append(" and _taskPacket.selectedDataType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSelectedDataType()));
	   	}
	   	if (queryVo.getSelectedRecordType() != null && !"".equals(queryVo.getSelectedRecordType())) {
	   		jpql.append(" and _taskPacket.selectedRecordType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSelectedRecordType()));
	   	}	
	   	if (queryVo.getCompression() != null && !"".equals(queryVo.getCompression())) {
	   		jpql.append(" and _taskPacket.compression like ?");
	   		conditionVals.add(queryVo.getCompression());
	   	}		
	   	if (queryVo.getEncryption() != null && !"".equals(queryVo.getEncryption())) {
	   		jpql.append(" and _taskPacket.encryption like ?");
	   		conditionVals.add(queryVo.getEncryption());
	   	}
		if (queryVo.getSerialNumber() != null && !"".equals(queryVo.getSerialNumber())) {
	   		jpql.append(" and _taskPacket.serialNumber like ?");
	   		conditionVals.add(queryVo.getSerialNumber());
	   	}	
		if(queryVo.getCreatedBy()!=null&&!"".equals(queryVo.getCreatedBy())){
			jpql.append(" and _taskPacket.createdBy = ?");
			conditionVals.add(queryVo.getCreatedBy());			
		}
		jpql.append("order by  _taskPacket.serialNumber asc");		
        Page<TaskPacket> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();		   
        return new Page<TaskPacketDTO>(pages.getStart(), pages.getResultCount(),pageSize, TaskPacketAssembler.toDTOs(pages.getData()));
	}
	
	@SuppressWarnings("unchecked")
	public Page<TaskPacketDTO> pageQueryTaskPacket(TaskPacketDTO queryVo, int currentPage, int pageSize, int taskPacketFrom, Long missionId) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _taskPacket from TaskPacket _taskPacket   where 1=1");
		if (missionId != null) {
	   		jpql.append(" and _taskPacket.task.mission.id = ?");
	   		conditionVals.add(missionId);
	   	}
		if (!"".equals(queryVo.getPacketFrom())) {
			jpql.append(" and _taskPacket.packetFrom = ?");
			conditionVals.add(taskPacketFrom);
		}
		if (queryVo.getSelectedPacketName() != null && !"".equals(queryVo.getSelectedPacketName())) {
			jpql.append(" and _taskPacket.selectedPacketName like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSelectedPacketName()));
		}
		if (queryVo.getSelectedFileVersion() != null && !"".equals(queryVo.getSelectedFileVersion())) {
			jpql.append(" and _taskPacket.selectedFileVersion like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSelectedFileVersion()));
		}
		if (queryVo.getSelectedOrigSender() != null && !"".equals(queryVo.getSelectedOrigSender())) {
			jpql.append(" and _taskPacket.selectedOrigSender like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSelectedOrigSender()));
		}
		if (queryVo.getSelectedOrigSendDate() != null ) {
			jpql.append(" and _taskPacket.selectedOrigSendDate between ? and ? ");
			conditionVals.add(queryVo.getSelectedOrigSendDate());
			conditionVals.add(queryVo.getSelectedOrigSendDateEnd());
		}
		if (queryVo.getSelectedDataType() != null && !"".equals(queryVo.getSelectedDataType())) {
			jpql.append(" and _taskPacket.selectedDataType like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSelectedDataType()));
		}
		if (queryVo.getSelectedRecordType() != null && !"".equals(queryVo.getSelectedRecordType())) {
			jpql.append(" and _taskPacket.selectedRecordType like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSelectedRecordType()));
		}	
		if (queryVo.getCompression() != null && !"".equals(queryVo.getCompression())) {
			jpql.append(" and _taskPacket.compression like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCompression()));
		}		
		if (queryVo.getEncryption() != null && !"".equals(queryVo.getEncryption())) {
			jpql.append(" and _taskPacket.encryption like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getEncryption()));
		}
		if (queryVo.getSerialNumber() != null && !"".equals(queryVo.getSerialNumber())) {
			jpql.append(" and _taskPacket.serialNumber like ?");
			conditionVals.add(queryVo.getSerialNumber());
		}	
		if(queryVo.getCreatedBy()!=null&&!"".equals(queryVo.getCreatedBy())){
			jpql.append(" and _taskPacket.createdBy = ?");
			conditionVals.add(queryVo.getCreatedBy());
			
		}
		jpql.append("order by _taskPacket.selectedOrigSendDate desc");
		Page<TaskPacket> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString())
				.setParameters(conditionVals)
				.setPage(currentPage, pageSize)
				.pagedList();
		
		return new Page<TaskPacketDTO>(pages.getStart(), pages.getResultCount(),pageSize, TaskPacketAssembler.toDTOs(pages.getData()));
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
}
