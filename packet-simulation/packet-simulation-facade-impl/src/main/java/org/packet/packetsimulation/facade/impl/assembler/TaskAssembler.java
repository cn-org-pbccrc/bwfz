package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class TaskAssembler {
	
	public static TaskDTO  toDTO(Task  task){
		if (task == null) {
			return null;
		}
    	TaskDTO result  = new TaskDTO();
	    	result.setId (task.getId());
     	    	result.setVersion (task.getVersion());
     	    	result.setTaskName (task.getTaskName());
     	    	result.setSendChannel (task.getSendChannel());
     	    	result.setSetTime (task.getSetTime());
     	    	result.setTaskCreator (task.getTaskCreator());
     	    	result.setTaskCreatedTime (task.getTaskCreatedTime());
     	    	result.setTaskStatus (task.getTaskStatus());
     	    	result.setPacketNum (task.getPacketCountOfTask(task).toString());
     	    	result.setTaskFrom(task.getTaskFrom());
     	    return result;
	 }
	
	public static List<TaskDTO>  toDTOs(Collection<Task>  tasks){
		if (tasks == null) {
			return null;
		}
		List<TaskDTO> results = new ArrayList<TaskDTO>();
		for (Task each : tasks) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static Task  toEntity(TaskDTO  taskDTO){
	 	if (taskDTO == null) {
			return null;
		}
	 	Task result  = new Task();
        result.setId (taskDTO.getId());
         result.setVersion (taskDTO.getVersion());
         result.setTaskName (taskDTO.getTaskName());
         result.setSendChannel (taskDTO.getSendChannel());
         result.setSetTime (taskDTO.getSetTime());
         result.setTaskCreator (taskDTO.getTaskCreator());
         result.setTaskCreatedTime (taskDTO.getTaskCreatedTime());
         result.setTaskStatus (taskDTO.getTaskStatus());
         result.setPacketNum (taskDTO.getPacketNum());
         result.setTaskFrom(taskDTO.getTaskFrom());
 	  	return result;
	 }
	
	public static List<Task> toEntities(Collection<TaskDTO> taskDTOs) {
		if (taskDTOs == null) {
			return null;
		}
		
		List<Task> results = new ArrayList<Task>();
		for (TaskDTO each : taskDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
