package org.packet.packetsimulation.facade;

import java.util.List;
import java.util.Map;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface DictTypeFacade {

	public InvokeResult getDictType(Long id);
	
	public InvokeResult creatDictType(DictTypeDTO dictType);
	
	public InvokeResult updateDictType(DictTypeDTO dictType);
	
	public InvokeResult removeDictType(Long id);
	
	public InvokeResult removeDictTypes(Long[] ids);

	public List<DictTypeDTO> findAllDictType();
	
	public Page<DictTypeDTO> pageQueryDictType(DictTypeDTO dictType, int currentPage, int pageSize);
			
	public Page<DictItemDTO> findDictItemSetByDictType(Long id, int currentPage, int pageSize);		
	
	public DictTypeDTO loadDictType();

	public InvokeResult deleteAllByParent(long id);
}

