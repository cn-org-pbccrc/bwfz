package org.packet.packetsimulation.facade;


import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

import com.alibaba.fastjson.JSONObject;

public interface SegmentFacade {
	
	public InvokeResult creatSegment(SegmentDTO segmentDTO);
	
	public InvokeResult updateSegment(SegmentDTO segmentDTO);
	
	public InvokeResult removeSegments(Long[] ids);
	
	public Page<JSONObject> pageQuerySegment(SegmentDTO segmentDTO, int page, int pagesize);
		
}

