package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.RecordSegmentApplication;
import org.packet.packetsimulation.application.SegmentApplication;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.Segment;
import org.packet.packetsimulationGeneration.core.domain.RecordSegment;
import org.packet.packetsimulationGeneration.core.domain.RecordType;

@Named
@Transactional
public class SegmentApplicationImpl implements SegmentApplication {

	public Segment getSegment(Long id) {
		return Segment.get(Segment.class, id);
	}
	
	public void creatSegment(Segment segment) {
		segment.save();
	}
	
	public void creatSegments(List<Segment> segments){
		for (Segment segment : segments) {
			segment.save();
		}
	}
	
	public void updateSegment(Segment segment) {
		segment.save();
	}
	
	public void removeRecordSegment(RecordSegment recordSegment) {
		if(recordSegment != null){
			recordSegment.remove();
		}
	}
	
	public void removeSegments(Set<Segment> segments) {
		for (Segment segment : segments) {
			segment.remove();
		}
	}
	
	public List<RecordSegment> findAllRecordSegment() {
		return RecordSegment.findAll(RecordSegment.class);
	}
	
}
