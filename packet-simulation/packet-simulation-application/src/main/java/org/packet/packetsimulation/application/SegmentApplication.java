package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;

import org.packet.packetsimulation.core.domain.Segment;
import  org.packet.packetsimulationGeneration.core.domain.RecordSegment;
import org.packet.packetsimulationGeneration.core.domain.RecordType;

public interface SegmentApplication {

	public RecordSegment getRecordSegment(Long id);
	
	public void creatSegment(Segment segment);
	
	public void updateRecordSegment(RecordSegment recordSegment);
	
	public void removeRecordSegment(RecordSegment recordSegment);
	
	public void removeRecordSegments(Set<RecordSegment> recordSegments);
	
	public List<RecordSegment> findAllRecordSegment();
	
}

