package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class MesgBatchAssembler {
	
	public static MesgBatchDTO  toDTO(MesgBatch  mesgBatch){
		if (mesgBatch == null) {
			return null;
		}
    	MesgBatchDTO result  = new MesgBatchDTO();
	    	result.setId (mesgBatch.getId());
     	    	result.setVersion (mesgBatch.getVersion());
     	    	result.setPacketName (mesgBatch.getPacketName());
     	    	result.setFileVersion (mesgBatch.getFileVersion());
     	    	result.setOrigSender (mesgBatch.getOrigSender());
     	    	result.setOrigSendDate (mesgBatch.getOrigSendDate());
     	    	result.setDataType (mesgBatch.getDataType());
     	    	result.setXml (mesgBatch.getXml());
     	    	result.setMesgNum (mesgBatch.getMesgNum());
     	    	result.setReserve (mesgBatch.getReserve());
     	    	result.setMesgType(mesgBatch.getMesgType().getId());
//     	    	result.setFrontPosition (mesgBatch.getFrontPosition());
//     	    	result.setSerialNumber (mesgBatch.getSerialNumber());
     	    	result.setCreatedBy (mesgBatch.getCreatedBy());
     	    	result.setPacketNum(mesgBatch.getPacketNum());
     	    	result.setStart(mesgBatch.getStart());
     	    return result;
	 }
	
	public static List<MesgBatchDTO>  toDTOs(Collection<MesgBatch>  mesgBatchs){
		if (mesgBatchs == null) {
			return null;
		}
		List<MesgBatchDTO> results = new ArrayList<MesgBatchDTO>();
		for (MesgBatch each : mesgBatchs) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static MesgBatch  toEntity(MesgBatchDTO  mesgBatchDTO){
	 	if (mesgBatchDTO == null) {
			return null;
		}
	 	MesgBatch result  = new MesgBatch();
        result.setId (mesgBatchDTO.getId());
         result.setVersion (mesgBatchDTO.getVersion());
         result.setPacketName (mesgBatchDTO.getPacketName());
         result.setFileVersion (mesgBatchDTO.getFileVersion());
         result.setOrigSender (mesgBatchDTO.getOrigSender());
         result.setOrigSendDate (mesgBatchDTO.getOrigSendDate());
         result.setDataType (mesgBatchDTO.getDataType());
         result.setXml (mesgBatchDTO.getXml());
         result.setMesgNum (mesgBatchDTO.getMesgNum());
         result.setReserve (mesgBatchDTO.getReserve());
//         result.setFrontPosition (mesgBatchDTO.getFrontPosition());
//         result.setSerialNumber (mesgBatchDTO.getSerialNumber());
         result.setCreatedBy (mesgBatchDTO.getCreatedBy());
         result.setPacketNum(mesgBatchDTO.getPacketNum());
         result.setStart(mesgBatchDTO.getStart());
 	  	return result;
	 }
	
	public static List<MesgBatch> toEntities(Collection<MesgBatchDTO> mesgBatchDTOs) {
		if (mesgBatchDTOs == null) {
			return null;
		}
		
		List<MesgBatch> results = new ArrayList<MesgBatch>();
		for (MesgBatchDTO each : mesgBatchDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
