package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface RecordSegmentFacade {

	public InvokeResult getRecordSegment(Long id);
	
	public InvokeResult creatRecordSegment(RecordSegmentDTO recordSegment);
	
	public InvokeResult updateRecordSegment(RecordSegmentDTO recordSegment);
	
	public InvokeResult removeRecordSegment(Long id);
	
	public InvokeResult removeRecordSegments(Long[] ids);
	
	public List<RecordSegmentDTO> findAllRecordSegment();
	
	public Page<RecordSegmentDTO> pageQueryRecordSegment(RecordSegmentDTO recordSegment, int currentPage, int pageSize);
	
	public RecordTypeDTO findRecordTypeByRecordSegment(Long id);


	
}

