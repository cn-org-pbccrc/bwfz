package org.packet.packetsimulation.facade;

import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulationGeneration.core.domain.RecordType;

public interface RecordTypeFacade {

	public InvokeResult getRecordType(Long id);
	
	public InvokeResult creatRecordType(RecordTypeDTO recordType);
	
	public InvokeResult updateRecordType(RecordTypeDTO recordType);
	
	public InvokeResult removeRecordType(Long id);
	
	public InvokeResult removeRecordTypes(Long[] ids);
	
	public InvokeResult copyHeaderItems(RecordTypeDTO recordType, Long selectedId);
	
	public List<RecordTypeDTO> findAllRecordType();
	
	public Page<RecordTypeDTO> pageQueryRecordType(RecordTypeDTO recordType, int currentPage, int pageSize);
	
	public Page<RecordTypeDTO> pageJsonByType(RecordTypeDTO recordType, int currentPage, int pageSize);
	
	public List<RecordType> findRecordTypes();
	
	public RecordTypeDTO findRecordTypeByRecordType(Long id);
}

