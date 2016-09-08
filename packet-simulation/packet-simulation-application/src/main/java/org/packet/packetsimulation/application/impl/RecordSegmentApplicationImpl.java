package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.RecordSegmentApplication;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulationGeneration.core.domain.RecordSegment;
import org.packet.packetsimulationGeneration.core.domain.RecordType;

@Named
@Transactional
public class RecordSegmentApplicationImpl implements RecordSegmentApplication {

	public RecordSegment getRecordSegment(Long id) {
		return RecordSegment.get(RecordSegment.class, id);
	}
	
	public void creatRecordSegment(RecordSegment recordSegment) {
		recordSegment.save();
	}
	
	public void updateRecordSegment(RecordSegment recordSegment) {
		recordSegment .save();
	}
	
	public void removeRecordSegment(RecordSegment recordSegment) {
		if(recordSegment != null){
			recordSegment.remove();
		}
	}
	
	public void removeRecordSegments(Set<RecordSegment> recordSegments) {
		for (RecordSegment recordSegment : recordSegments) {
			recordSegment.remove();
		}
	}
	
	public List<RecordSegment> findAllRecordSegment() {
		return RecordSegment.findAll(RecordSegment.class);
	}
	
}
