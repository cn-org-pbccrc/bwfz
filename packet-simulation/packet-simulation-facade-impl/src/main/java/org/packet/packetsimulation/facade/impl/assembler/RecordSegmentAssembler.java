package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.packet.packetsimulation.facade.dto.RecordSegmentDTO;
import org.packet.packetsimulationGeneration.core.domain.RecordSegment;


public class RecordSegmentAssembler {
	
	public static RecordSegmentDTO  toDTO(RecordSegment  RecordSegment){
		if (RecordSegment == null) {
			return null;
		}
		RecordSegmentDTO result  = new RecordSegmentDTO();
	 	result.setId(RecordSegment.getId());
		result.setVersion(RecordSegment.getVersion());
		result.setAppearTimes(RecordSegment.getAppearTimes());
		result.setSegDesc(RecordSegment.getSegDesc());
		result.setRecordItems(RecordItemAssembler.toDTOList(RecordSegment.getRecordItems()));
		result.setSegLength(RecordSegment.getSegLength());
		result.setSegMark(RecordSegment.getSegMark());
		result.setSegName(RecordSegment.getSegName());
		result.setState(RecordSegment.getState());
 	    return result;
	 }
	
	public static List<RecordSegmentDTO>  toDTOs(Collection<RecordSegment>  RecordSegments){
		if (RecordSegments == null) {
			return null;
		}
		List<RecordSegmentDTO> results = new ArrayList<RecordSegmentDTO>();
		for (RecordSegment each : RecordSegments) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static RecordSegment  toEntity(RecordSegmentDTO  RecordSegmentDTO){
	 	if (RecordSegmentDTO == null) {
			return null;
		}
	 	RecordSegment result  = new RecordSegment();
	 	result.setId(RecordSegmentDTO.getId());
		result.setVersion(RecordSegmentDTO.getVersion());
	 	result.setAppearTimes(RecordSegmentDTO.getAppearTimes());
		result.setSegDesc(RecordSegmentDTO.getSegDesc());
		result.setRecordItems(RecordItemAssembler.toEntityList(RecordSegmentDTO.getRecordItems()));
		result.setSegLength(RecordSegmentDTO.getSegLength());
		result.setSegMark(RecordSegmentDTO.getSegMark());
		result.setSegName(RecordSegmentDTO.getSegName());
		result.setState(RecordSegmentDTO.getState());
 	  	return result;
	 }
	
	public static List<RecordSegment> toEntities(Collection<RecordSegmentDTO> RecordSegmentDTOs) {
		if (RecordSegmentDTOs == null) {
			return null;
		}
		
		List<RecordSegment> results = new ArrayList<RecordSegment>();
		for (RecordSegmentDTO each : RecordSegmentDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
