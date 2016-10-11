package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class LotAssembler {
	
	public static LotDTO  toDTO(Lot  lot){
		if (lot == null) {
			return null;
		}
    	LotDTO result  = new LotDTO();
	    	result.setId (lot.getId());
     	    	result.setVersion (lot.getVersion());
     	    	result.setLotName (lot.getLotName());
     	    	result.setSendChannel (lot.getSendChannel());
     	    	result.setSetTime (lot.getSetTime());
     	    	result.setLotCreator (lot.getLotCreator());
     	    	result.setLotCreatedTime (lot.getLotCreatedTime());
     	    	result.setLotStatus (lot.getLotStatus());
     	    	result.setSubmissionNum (lot.getSubmissionCountOfLot(lot).toString());
     	    	result.setType (lot.getType());
     	    return result;
	 }
	
	public static List<LotDTO>  toDTOs(Collection<Lot>  lots){
		if (lots == null) {
			return null;
		}
		List<LotDTO> results = new ArrayList<LotDTO>();
		for (Lot each : lots) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static Lot  toEntity(LotDTO  lotDTO){
	 	if (lotDTO == null) {
			return null;
		}
	 	Lot result  = new Lot();
        result.setId (lotDTO.getId());
         result.setVersion (lotDTO.getVersion());
         result.setLotName (lotDTO.getLotName());
         result.setSendChannel (lotDTO.getSendChannel());
         result.setSetTime (lotDTO.getSetTime());
         result.setLotCreator (lotDTO.getLotCreator());
         result.setLotCreatedTime (lotDTO.getLotCreatedTime());
         result.setLotStatus (lotDTO.getLotStatus());
         result.setSubmissionNum (lotDTO.getSubmissionNum());
         result.setType (lotDTO.getType());
 	  	return result;
	 }
	
	public static List<Lot> toEntities(Collection<LotDTO> lotDTOs) {
		if (lotDTOs == null) {
			return null;
		}
		
		List<Lot> results = new ArrayList<Lot>();
		for (LotDTO each : lotDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
