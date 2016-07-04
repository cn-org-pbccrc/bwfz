package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface RecordTypeFacade {

	public InvokeResult getRecordType(Long id);
	
	public InvokeResult creatRecordType(RecordTypeDTO recordType);
	
	public InvokeResult updateRecordType(RecordTypeDTO recordType);
	
	public InvokeResult removeRecordType(Long id);
	
	public InvokeResult removeRecordTypes(Long[] ids);
	
	public List<RecordTypeDTO> findAllRecordType();
	
	public Page<RecordTypeDTO> pageQueryRecordType(RecordTypeDTO recordType, int currentPage, int pageSize);
	

}

