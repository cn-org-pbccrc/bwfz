package org.packet.packetsimulation.facade.impl.assembler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class ThreeStandardAssembler {
	
	public static ThreeStandardDTO  toDTO(ThreeStandard  threeStandard){
		if (threeStandard == null) {
			return null;
		}
    	ThreeStandardDTO result  = new ThreeStandardDTO();
	    	result.setId (threeStandard.getId());
     	    	result.setVersion (threeStandard.getVersion());
     	    	result.setName (threeStandard.getName());
     	    	result.setCredentialType (threeStandard.getCredentialType());
     	    	result.setCredentialNumber (threeStandard.getCredentialNumber());
     	    	result.setOrganizationCode (threeStandard.getOrganizationCode());
     	    	result.setCustomerCode (threeStandard.getCustomerCode());
     	    	result.setCreatedDate (threeStandard.getCreatedDate());
     	    	result.setCreatedBy (threeStandard.getCreatedBy());
     	    return result;
	 }
	
	public static List<ThreeStandardDTO>  toDTOs(Collection<ThreeStandard>  threeStandards){
		if (threeStandards == null) {
			return null;
		}
		List<ThreeStandardDTO> results = new ArrayList<ThreeStandardDTO>();
		for (ThreeStandard each : threeStandards) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static ThreeStandard  toEntity(ThreeStandardDTO  threeStandardDTO){
	 	if (threeStandardDTO == null) {
			return null;
		}
	 	ThreeStandard result  = new ThreeStandard();
        result.setId (threeStandardDTO.getId());
         result.setVersion (threeStandardDTO.getVersion());
         result.setName (threeStandardDTO.getName());
         result.setCredentialType (threeStandardDTO.getCredentialType());
         result.setCredentialNumber (threeStandardDTO.getCredentialNumber());
         result.setOrganizationCode (threeStandardDTO.getOrganizationCode());
         result.setCustomerCode (threeStandardDTO.getCustomerCode());
         result.setCreatedDate (threeStandardDTO.getCreatedDate());
         result.setCreatedBy (threeStandardDTO.getCreatedBy());
 	  	return result;
	 }
	
	public static List<ThreeStandard> toEntities(Collection<ThreeStandardDTO> threeStandardDTOs) {
		if (threeStandardDTOs == null) {
			return null;
		}
		
		List<ThreeStandard> results = new ArrayList<ThreeStandard>();
		for (ThreeStandardDTO each : threeStandardDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
