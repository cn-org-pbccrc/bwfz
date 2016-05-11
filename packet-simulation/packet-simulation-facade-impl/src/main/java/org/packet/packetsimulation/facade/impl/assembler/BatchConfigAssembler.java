package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class BatchConfigAssembler {
	
	public static BatchConfigDTO  toDTO(BatchConfig  batchConfig){
		if (batchConfig == null) {
			return null;
		}
    	BatchConfigDTO result  = new BatchConfigDTO();
	    	result.setId (batchConfig.getId());
     	    	result.setVersion (batchConfig.getVersion());
     	    	result.setCreatedBy (batchConfig.getCreatedBy());
     	    	result.setCreateDate (batchConfig.getCreateDate());
     	    	result.setBatchRules (BatchRuleAssembler.toDTOList(batchConfig.getBatchRules()));
     	    	result.setMesgTypeDTO(MesgTypeAssembler.toDTO(batchConfig.getMesgType()));
     	    return result;
	 }
	
	public static List<BatchConfigDTO>  toDTOs(Collection<BatchConfig>  batchConfigs){
		if (batchConfigs == null) {
			return null;
		}
		List<BatchConfigDTO> results = new ArrayList<BatchConfigDTO>();
		for (BatchConfig each : batchConfigs) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static BatchConfig  toEntity(BatchConfigDTO  batchConfigDTO){
	 	if (batchConfigDTO == null) {
			return null;
		}
	 	BatchConfig result  = new BatchConfig();
        result.setId (batchConfigDTO.getId());
         result.setVersion (batchConfigDTO.getVersion());
         result.setCreatedBy (batchConfigDTO.getCreatedBy());
         result.setCreateDate (batchConfigDTO.getCreateDate());
         result.setBatchRules (BatchRuleAssembler.toEntityList(batchConfigDTO.getBatchRules()));
         result.setMesgType(MesgTypeAssembler.toEntity(batchConfigDTO.getMesgTypeDTO()));
 	  	return result;
	 }
	
	public static List<BatchConfig> toEntities(Collection<BatchConfigDTO> batchConfigDTOs) {
		if (batchConfigDTOs == null) {
			return null;
		}
		
		List<BatchConfig> results = new ArrayList<BatchConfig>();
		for (BatchConfigDTO each : batchConfigDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
