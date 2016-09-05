package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface RecordFacade {

	public InvokeResult getRecord(Long id);
	
	public InvokeResult creatRecord(RecordDTO record);
	
	public InvokeResult updateRecord(RecordDTO record);
	
	public InvokeResult removeRecord(Long id);
	
	public InvokeResult removeRecords(Long[] ids);
	
	public List<RecordDTO> findAllRecord();
	
	public Page<RecordDTO> pageQueryRecord(RecordDTO record, int currentPage, int pageSize);
	

}

