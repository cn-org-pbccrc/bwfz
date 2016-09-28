package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class QuantityConfigAssembler {
	
	public static QuantityConfigDTO  toDTO(QuantityConfig  quantityConfig){
		if (quantityConfig == null) {
			return null;
		}
    	QuantityConfigDTO result  = new QuantityConfigDTO();
	    	result.setId (quantityConfig.getId());
     	    	result.setVersion (quantityConfig.getVersion());
     	    	result.setCreatedBy (quantityConfig.getCreatedBy());
     	    	result.setCreateDate (quantityConfig.getCreateDate());
     	    	result.setQuantityRules (QuantityRuleAssembler.toDTOList(quantityConfig.getQuantityRules()));
     	    	result.setRecordTypeDTO(RecordTypeAssembler.toDTO(quantityConfig.getRecordType()));
     	    return result;
	 }
	
	public static List<QuantityConfigDTO>  toDTOs(Collection<QuantityConfig>  quantityConfigs){
		if (quantityConfigs == null) {
			return null;
		}
		List<QuantityConfigDTO> results = new ArrayList<QuantityConfigDTO>();
		for (QuantityConfig each : quantityConfigs) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static QuantityConfig  toEntity(QuantityConfigDTO  quantityConfigDTO){
	 	if (quantityConfigDTO == null) {
			return null;
		}
	 	QuantityConfig result  = new QuantityConfig();
        result.setId (quantityConfigDTO.getId());
         result.setVersion (quantityConfigDTO.getVersion());
         result.setCreatedBy (quantityConfigDTO.getCreatedBy());
         result.setCreateDate (quantityConfigDTO.getCreateDate());
         result.setQuantityRules (QuantityRuleAssembler.toEntityList(quantityConfigDTO.getQuantityRules()));
         result.setRecordType(RecordTypeAssembler.toEntity(quantityConfigDTO.getRecordTypeDTO()));
 	  	return result;
	 }
	
	public static List<QuantityConfig> toEntities(Collection<QuantityConfigDTO> quantityConfigDTOs) {
		if (quantityConfigDTOs == null) {
			return null;
		}
		
		List<QuantityConfig> results = new ArrayList<QuantityConfig>();
		for (QuantityConfigDTO each : quantityConfigDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
