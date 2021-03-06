package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulation.facade.dto.MesgDTO;

public class MesgAssembler {
	
	public static MesgDTO  toDTO(Mesg  mesg){
		if (mesg == null) {
			return null;
		}
    	MesgDTO result  = new MesgDTO();
	    	result.setId (mesg.getId());
     	    	result.setVersion (mesg.getVersion());
     	    	result.setPacketId(mesg.getPacket().getId());
     	    	result.setContent(mesg.getContent());
     	    	result.setMesgType(mesg.getMesgType().getId());
     	    	result.setMesgTypeStr(mesg.getMesgType().getMesgType());
     	    	result.setRemark(mesg.getRemark());
//     	    	result.setMesgDirection(mesg.getMesgDirection());
//     	    	result.setMesgPriority(mesg.getMesgPriority());
//     	    	result.setReserve(mesg.getReserve());
     	    	result.setMesgId(mesg.getMesgId());
     	    return result;
	 }
	
	public static List<MesgDTO>  toDTOs(Collection<Mesg>  mesgs){
		if (mesgs == null) {
			return null;
		}
		List<MesgDTO> results = new ArrayList<MesgDTO>();
		for (Mesg each : mesgs) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static Mesg  toEntity(MesgDTO  mesgDTO){
	 	if (mesgDTO == null) {
			return null;
		}
	 	Mesg result  = new Mesg();
        result.setId (mesgDTO.getId());
        result.setVersion (mesgDTO.getVersion());
        result.setRemark(mesgDTO.getRemark());
//        result.setMesgDirection(mesgDTO.getMesgDirection());
//	    result.setMesgPriority(mesgDTO.getMesgPriority());
//	    result.setReserve(mesgDTO.getReserve());
	    result.setMesgId(mesgDTO.getMesgId());	
	    
 	  	return result;
	 }
	
	public static List<Mesg> toEntities(Collection<MesgDTO> mesgDTOs) {
		if (mesgDTOs == null) {
			return null;
		}
		
		List<Mesg> results = new ArrayList<Mesg>();
		for (MesgDTO each : mesgDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
