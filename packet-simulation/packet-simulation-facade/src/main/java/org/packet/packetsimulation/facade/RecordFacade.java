package org.packet.packetsimulation.facade;

import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulationGeneration.core.domain.RecordType;

public interface RecordFacade {

	public InvokeResult getRecord(Long id);
	
	public InvokeResult creatRecord(RecordDTO record);
	
	public InvokeResult updateRecord(RecordDTO record, Long recordTypeId);
	
	public InvokeResult removeRecord(Long id);
	
	public InvokeResult removeRecords(Long[] ids);
	
	public List<RecordDTO> findAllRecord();
	
	public Page<RecordDTO> pageQueryRecord(RecordDTO record, int currentPage, int pageSize, Long submissionId);
	
	public List<RecordType> findRecordTypes();
	
	public InvokeResult getRecordForBatch(Long id);
	
	public Long queryCountOfThreeStandard(String currentUserId);
	
	public InvokeResult batchRecord(RecordDTO record, String[] ids ,String userAccount);
	
	public InvokeResult batchRecord(RecordDTO record, int start, int end, String userAccount);
}

