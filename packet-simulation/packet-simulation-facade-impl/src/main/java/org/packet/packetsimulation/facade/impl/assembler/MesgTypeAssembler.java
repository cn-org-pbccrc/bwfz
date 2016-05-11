package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class MesgTypeAssembler {
	
	public static MesgTypeDTO  toDTO(MesgType  mesgType){
		if (mesgType == null) {
			return null;
		}
    	MesgTypeDTO result  = new MesgTypeDTO();
	    	result.setId (mesgType.getId());
     	    	result.setVersion (mesgType.getVersion());
     	    	result.setMesgType(mesgType.getMesgType());
     	    	result.setSort(mesgType.getSort());
     	    	result.setCode(mesgType.getCode());
     	    	result.setCountTag(mesgType.getCountTag());
     	    	result.setTransform(mesgType.getTransform());
     	    	result.setXml(mesgType.getXml());
     	    	result.setCreatedBy(mesgType.getCreatedBy());
     	    return result;
	 }
	
	public static List<MesgTypeDTO>  toDTOs(Collection<MesgType>  mesgs){
		if (mesgs == null) {
			return null;
		}
		List<MesgTypeDTO> results = new ArrayList<MesgTypeDTO>();
		for (MesgType each : mesgs) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static MesgType  toEntity(MesgTypeDTO  mesgTypeDTO){
	 	if (mesgTypeDTO == null) {
			return null;
		}
	 	MesgType result  = new MesgType();
        result.setId (mesgTypeDTO.getId());
         result.setVersion (mesgTypeDTO.getVersion());
         result.setMesgType (mesgTypeDTO.getMesgType());
         result.setSort(mesgTypeDTO.getSort());       
	     result.setCode(mesgTypeDTO.getCode());
	     result.setCountTag(mesgTypeDTO.getCountTag());
	     result.setTransform(mesgTypeDTO.getTransform());
	     result.setXml(mesgTypeDTO.getXml());
	     result.setCreatedBy(mesgTypeDTO.getCreatedBy());
 	  	return result;
	 }
	
	public static List<MesgType> toEntities(Collection<MesgTypeDTO> mesgTypeDTOs) {
		if (mesgTypeDTOs == null) {
			return null;
		}
		
		List<MesgType> results = new ArrayList<MesgType>();
		for (MesgTypeDTO each : mesgTypeDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
