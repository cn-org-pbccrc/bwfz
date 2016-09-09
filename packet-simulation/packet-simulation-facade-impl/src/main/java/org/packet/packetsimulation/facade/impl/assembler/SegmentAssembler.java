package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.packet.packetsimulation.core.domain.Segment;
import org.packet.packetsimulation.facade.dto.RecordSegmentDTO;
import org.packet.packetsimulation.facade.dto.SegmentDTO;
import org.packet.packetsimulationGeneration.core.domain.RecordSegment;


public class SegmentAssembler {
	
	public static SegmentDTO  toDTO(Segment segment){
		if (segment == null) {
			return null;
		}
		SegmentDTO result  = new SegmentDTO();
	 	result.setId(segment.getId());
		result.setVersion(segment.getVersion());
		result.setSegMark(segment.getSegMark());
		result.setContent(segment.getContent());
 	    return result;
	 }
	
	 public static Segment toEntity(SegmentDTO  segmentDTO){
	 	if (segmentDTO == null) {
			return null;
		}
	 	Segment result  = new Segment();
	 	result.setId(segmentDTO.getId());
		result.setVersion(segmentDTO.getVersion());
	 	result.setSegMark(segmentDTO.getSegMark());
	 	result.setContent(segmentDTO.getContent());
 	  	return result;
	 }
}
