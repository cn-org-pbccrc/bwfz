package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

public interface SegmentFacade {
	
	public InvokeResult creatSegment(SegmentDTO segmentDTO);
	
	public InvokeResult updateSegment(SegmentDTO segmentDTO);
	
	public Page<JSONObject> pageQuerySegment(SegmentDTO segmentDTO, int page, int pagesize);
		
}

