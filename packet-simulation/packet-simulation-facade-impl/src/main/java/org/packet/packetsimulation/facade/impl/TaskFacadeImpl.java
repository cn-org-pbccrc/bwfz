package org.packet.packetsimulation.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.text.MessageFormat;
import javax.inject.Inject;
import javax.inject.Named;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.impl.assembler.TaskAssembler;
import org.packet.packetsimulation.facade.TaskFacade;
import org.packet.packetsimulation.application.TaskApplication;

import org.packet.packetsimulation.core.domain.*;

@Named
public class TaskFacadeImpl implements TaskFacade {

	@Inject
	private TaskApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getTask(Long id) {
		return InvokeResult.success(TaskAssembler.toDTO(application.getTask(id)));
	}
	
	public InvokeResult creatTask(TaskDTO taskDTO) {
		application.creatTask(TaskAssembler.toEntity(taskDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateTask(TaskDTO taskDTO) {
		application.updateTask(TaskAssembler.toEntity(taskDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeTask(Long id) {
		application.removeTask(application.getTask(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeTasks(Long[] ids) {
		Set<Task> tasks= new HashSet<Task>();
		for (Long id : ids) {
			tasks.add(application.getTask(id));
		}
		application.removeTasks(tasks);
		return InvokeResult.success();
	}
	
	public List<TaskDTO> findAllTask() {
		return TaskAssembler.toDTOs(application.findAllTask());
	}
	
	public Page<TaskDTO> pageQueryTask(TaskDTO queryVo, int currentPage, int pageSize, String currentUserId) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _task from Task _task   where 1=1 ");
	   	if (queryVo.getTaskName() != null && !"".equals(queryVo.getTaskName())) {
	   		jpql.append(" and _task.taskName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTaskName()));
	   	}		
	   	if (queryVo.getSendChannel() != null && !"".equals(queryVo.getSendChannel())) {
	   		jpql.append(" and _task.sendChannel like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSendChannel()));
	   	}		
	   	if (queryVo.getSetTime() != null && !"".equals(queryVo.getSetTime())) {
	   		jpql.append(" and _task.setTime like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSetTime()));
	   	}		
	   	if (queryVo.getTaskCreator() != null && !"".equals(queryVo.getTaskCreator())) {
	   		jpql.append(" and _task.taskCreator like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTaskCreator()));
	   	}		
	   	if (queryVo.getTaskCreatedTime() != null) {
	   		jpql.append(" and _task.taskCreatedTime between ? and ? ");
	   		conditionVals.add(queryVo.getTaskCreatedTime());
	   		conditionVals.add(queryVo.getTaskCreatedTimeEnd());
	   	}	
	   	if (queryVo.getTaskStatus() != null && !"".equals(queryVo.getTaskStatus())) {
	   		jpql.append(" and _task.taskStatus like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTaskStatus()));
	   	}		
	   	if (queryVo.getPacketNum() != null && !"".equals(queryVo.getPacketNum())) {
	   		jpql.append(" and _task.packetNum like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPacketNum()));
	   	}	
	  	if (currentUserId != null) {
	   		jpql.append(" and _task.taskCreator = ?");
	   		conditionVals.add(currentUserId);
	   	}	
        Page<Task> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<TaskDTO>(pages.getStart(), pages.getResultCount(),pageSize, TaskAssembler.toDTOs(pages.getData()));
	}
	
	
}
