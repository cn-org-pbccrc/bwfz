package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;

import org.packet.packetsimulation.core.domain.Segment;
import  org.packet.packetsimulationGeneration.core.domain.RecordSegment;
import org.packet.packetsimulationGeneration.core.domain.RecordType;

public interface SegmentApplication {

	public Segment getSegment(Long id);
	
	public void creatSegment(Segment segment);
	
	public void updateSegment(Segment segment);
	
	public void removeRecordSegment(RecordSegment recordSegment);
	
	public void removeSegments(Set<Segment> segments);
	
	public List<RecordSegment> findAllRecordSegment();
	
}

