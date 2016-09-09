package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.springframework.web.bind.annotation.RequestParam;

public interface SegmentFacade {
	
	public InvokeResult creatSegment(SegmentDTO segment);
	
	public InvokeResult pageQuerySegment(SegmentDTO segmentDTO);
		
}

