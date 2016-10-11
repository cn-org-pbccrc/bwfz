package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;

import  org.packet.packetsimulationGeneration.core.domain.RecordSegment;

public interface RecordSegmentApplication {

	public RecordSegment getRecordSegment(Long id);
	
	public void creatRecordSegment(RecordSegment recordSegment);
	
	public void updateRecordSegment(RecordSegment recordSegment);
	
	public void removeRecordSegment(RecordSegment recordSegment);
	
	public void removeRecordSegments(Set<RecordSegment> recordSegments);
	
	public List<RecordSegment> findAllRecordSegment();
	
}

