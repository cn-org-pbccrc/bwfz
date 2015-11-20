package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class TaskPacketAssembler {
	
	public static TaskPacketDTO  toDTO(TaskPacket  taskPacket){
		if (taskPacket == null) {
			return null;
		}
    	TaskPacketDTO result  = new TaskPacketDTO();
	    	result.setId (taskPacket.getId());
     	    	result.setVersion (taskPacket.getVersion());
     	    	result.setTaskId(taskPacket.getTask().getId());
     	    	result.setPacketFrom(taskPacket.getPacketFrom());
     	    	result.setSelectedPacketName (taskPacket.getSelectedPacketName());
     	    	result.setSelectedFileVersion(taskPacket.getSelectedFileVersion());
     	    	result.setSelectedOrigSender(taskPacket.getSelectedOrigSender());
     	    	result.setSelectedOrigSendDate(taskPacket.getSelectedOrigSendDate());
     	    	result.setSelectedDataType(taskPacket.getSelectedDataType());
     	    	result.setSelectedRecordType(taskPacket.getSelectedRecordType());
     	    	result.setCompression (taskPacket.getCompression());
     	    	result.setEncryption (taskPacket.getEncryption());
     	    return result;
	 }
	
	public static List<TaskPacketDTO>  toDTOs(Collection<TaskPacket>  taskPackets){
		if (taskPackets == null) {
			return null;
		}
		List<TaskPacketDTO> results = new ArrayList<TaskPacketDTO>();
		for (TaskPacket each : taskPackets) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static TaskPacket  toEntity(TaskPacketDTO  taskPacketDTO){
	 	if (taskPacketDTO == null) {
			return null;
		}
	 	TaskPacket result  = new TaskPacket();
        result.setId (taskPacketDTO.getId());
         result.setVersion (taskPacketDTO.getVersion());
         result.setPacketFrom(taskPacketDTO.getPacketFrom());
         result.setSelectedPacketName (taskPacketDTO.getSelectedPacketName());
         result.setSelectedFileVersion(taskPacketDTO.getSelectedFileVersion());
         result.setSelectedOrigSender(taskPacketDTO.getSelectedOrigSender());
         result.setSelectedOrigSendDate(taskPacketDTO.getSelectedOrigSendDate());
         result.setSelectedDataType(taskPacketDTO.getSelectedDataType());
         result.setSelectedRecordType(taskPacketDTO.getSelectedRecordType());
         result.setCompression (taskPacketDTO.getCompression());
         result.setEncryption (taskPacketDTO.getEncryption());
 	  	return result;
	 }
	
	public static List<TaskPacket> toEntities(Collection<TaskPacketDTO> taskPacketDTOs) {
		if (taskPacketDTOs == null) {
			return null;
		}
		
		List<TaskPacket> results = new ArrayList<TaskPacket>();
		for (TaskPacketDTO each : taskPacketDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
