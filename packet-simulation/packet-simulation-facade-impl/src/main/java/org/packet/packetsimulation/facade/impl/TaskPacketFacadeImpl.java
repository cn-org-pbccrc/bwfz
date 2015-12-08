package org.packet.packetsimulation.facade.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.io.File;
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
import org.packet.packetsimulation.application.PacketApplication;
import org.packet.packetsimulation.application.TaskApplication;
import org.packet.packetsimulation.application.TaskPacketApplication;
import org.packet.packetsimulation.core.domain.*;

@Named
public class TaskPacketFacadeImpl implements TaskPacketFacade {

	@Inject
	private TaskPacketApplication  application;
	
	@Inject
	private TaskApplication  taskApplication;

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
	
	public InvokeResult creatOutSideTaskPacket(TaskPacketDTO taskPacketDTO, String fileName) throws ParseException {
		TaskPacket taskPacket = TaskPacketAssembler.toEntity(taskPacketDTO);
		Task task = taskApplication.getTask(taskPacketDTO.getTaskId());			
		taskPacket.setTask(task);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		taskPacket.setPacketFrom("外部报文");
		taskPacket.setSelectedPacketName(fileName);
		taskPacket.setSelectedFileVersion("-");
		taskPacket.setSelectedOrigSender("-");
		String dateString = sdf.format(new Date());
		Date date=sdf.parse(dateString);
		taskPacket.setSelectedOrigSendDate(date);
		taskPacket.setSelectedDataType("-");
		taskPacket.setSelectedRecordType("-");
		taskPacket.setCompression("-");
		taskPacket.setEncryption("-");
		application.creatTaskPacket(taskPacket);
		return InvokeResult.success();
	}
	
	public InvokeResult updateOutSideTaskPacket(String fileName) throws ParseException{
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _taskPacket from TaskPacket _taskPacket  where 1=1 ");
		if (fileName != null && !"".equals(fileName)) {
			jpql.append(" and _taskPacket.selectedPacketName = ? ");
		   	conditionVals.add(fileName);
		}
		jpql.append(" and _taskPacket.packetFrom = ? ");
		conditionVals.add("外部报文");
		TaskPacket taskPacket = (TaskPacket) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
		System.out.println("111啊哈哈哈哈"+taskPacket.getId());
		//taskPacketDTO.setId(taskPacket.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(new Date());
		Date date=sdf.parse(dateString);
		taskPacket.setSelectedOrigSendDate(date);
		application.updateTaskPacket(taskPacket);
		return InvokeResult.success();
	}
	
	public InvokeResult creatTaskPackets(TaskPacketDTO taskPacketDTO, String[] values, String[] vers, String[] senders, String[] dates, String[] datTs, String[] recTs, String[] coms, String[] encs) throws ParseException {
		Set<TaskPacket> taskPackets= new HashSet<TaskPacket>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer max = findMaxSerialNumber(taskPacketDTO.getTaskId());
		Integer flag = max + 1;
		System.out.println("最大值来啦速来围观哈哈:"+max);
		for (int i =0; i < values.length; i++) {
			TaskPacket taskPacket = TaskPacketAssembler.toEntity(taskPacketDTO);
			Task task = taskApplication.getTask(taskPacketDTO.getTaskId());			
			taskPacket.setTask(task);
			taskPacket.setSelectedPacketName(values[i]);
			taskPacket.setSelectedFileVersion(vers[i]);
			taskPacket.setSelectedOrigSender(senders[i]);
			Date date=sdf.parse(dates[i]);
			taskPacket.setSelectedOrigSendDate(date);
			taskPacket.setSelectedDataType(datTs[i]);
			taskPacket.setSelectedRecordType(recTs[i]);
			taskPacket.setCompression(coms[i]);
			taskPacket.setEncryption(encs[i]);
			taskPacket.setSerialNumber(flag);
			flag++;
			//System.out.println("值:"+values[i]+"加压"+coms[i]+"加密"+encs[i]);
			taskPackets.add(taskPacket);
		}
		application.creatTaskPackets(taskPackets);
		return InvokeResult.success();
	}
	
	private Integer findMaxSerialNumber(Long taskId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select max(_taskPacket.serialNumber) from TaskPacket _taskPacket  where 1=1 ");
	   	
	   	if (taskId != null ) {
	   		jpql.append(" and _taskPacket.task.id = ? ");
	   		conditionVals.add(taskId);
	   	}
	   	//System.out.println("将军百战死:"+getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult());
	   	//System.out.println("不破楼兰终不还:"+(getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult()==null));
	   	if((getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult()==null)){
	   		return 0;
	   	}
	   	Integer max = (Integer) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return max;
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
		Set<TaskPacket> taskPackets= new HashSet<TaskPacket>();
		for (Long id : ids) {
			taskPackets.add(application.getTaskPacket(id));
			System.out.println("要删除的文件名称:"+savePath+application.getTaskPacket(id).getTask().getId()+File.separator+application.getTaskPacket(id).getSelectedPacketName());
			new File(savePath+application.getTaskPacket(id).getTask().getId()+File.separator+application.getTaskPacket(id).getSelectedPacketName()).delete();
		}
		application.removeTaskPackets(taskPackets);
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
	
	public Page<TaskPacketDTO> pageQueryTaskPacket(TaskPacketDTO queryVo, int currentPage, int pageSize, Long taskId) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _taskPacket from TaskPacket _taskPacket   where 1=1");
	   	//StringBuilder jpql = new StringBuilder("select _taskPacket from TaskPacket _taskPacket   where 1=1");
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
//	   	if (queryVo.getSelectedOrigSendDate() != null && !"".equals(queryVo.getSelectedOrigSendDate())) {
//	   		jpql.append(" and _taskPacket.selectedOrigSendDate like ?");
//	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSelectedOrigSendDate()));
//	   	}
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
		//if (queryVo.getSerialNumber() != null && !"".equals(queryVo.getSerialNumber())) {
			jpql.append("order by _taskPacket.serialNumber asc");
	   		//conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSerialNumber()));
	   	//}
		
        Page<TaskPacket> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<TaskPacketDTO>(pages.getStart(), pages.getResultCount(),pageSize, TaskPacketAssembler.toDTOs(pages.getData()));
	}
	
	
}
