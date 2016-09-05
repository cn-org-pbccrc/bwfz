package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



import org.packet.packetsimulation.facade.dto.RecordTypeDTO;
import org.packet.packetsimulationGeneration.core.domain.RecordType;

public class RecordTypeAssembler {
	
	public static RecordTypeDTO  toDTO(RecordType  RecordType){
		if (RecordType == null) {
			return null;
		}
		RecordTypeDTO result  = new RecordTypeDTO();
		result.setId(RecordType.getId());
		result.setVersion(RecordType.getVersion());
		result.setHeaderItems(RecordItemAssembler.toDTOList(RecordType.getHeaderItems()));
		result.setCode(RecordType.getCode());
		result.setCreatedBy(RecordType.getCreatedBy());
//		result.setRecordSegments(RecordSegmentAssembler.toDTOs(RecordType.getRecordSegments()));
		result.setRecordTemp(RecordType.getRecordTemp());
		result.setRecordType(RecordType.getRecordType());
		result.setTransform(RecordType.getTransform());
 	    return result;
	 }
	
	public static List<RecordTypeDTO>  toDTOs(Collection<RecordType>  RecordTypes){
		if (RecordTypes == null) {
			return null;
		}
		List<RecordTypeDTO> results = new ArrayList<RecordTypeDTO>();
		for (RecordType each : RecordTypes) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static RecordType  toEntity(RecordTypeDTO  RecordTypeDTO){
	 	if (RecordTypeDTO == null) {
			return null;
		}
	 	RecordType result  = new RecordType();
	 	result.setId(RecordTypeDTO.getId());
		result.setVersion(RecordTypeDTO.getVersion());
		result.setHeaderItems(RecordItemAssembler.toEntityList(RecordTypeDTO.getHeaderItems()));
	 	result.setCode(RecordTypeDTO.getCode());
		result.setCreatedBy(RecordTypeDTO.getCreatedBy());
//		result.setRecordSegments(RecordSegmentAssembler.toEntities(RecordTypeDTO.getRecordSegments()));
		result.setRecordTemp(RecordTypeDTO.getRecordTemp());
		result.setRecordType(RecordTypeDTO.getRecordType());
		result.setTransform(RecordTypeDTO.getTransform());
 	  	return result;
	 }
	
	public static List<RecordType> toEntities(Collection<RecordTypeDTO> RecordTypeDTOs) {
		if (RecordTypeDTOs == null) {
			return null;
		}
		
		List<RecordType> results = new ArrayList<RecordType>();
		for (RecordTypeDTO each : RecordTypeDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
