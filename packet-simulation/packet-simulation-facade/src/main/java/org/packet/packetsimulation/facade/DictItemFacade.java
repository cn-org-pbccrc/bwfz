package org.packet.packetsimulation.facade;

import java.util.List;
import java.util.Map;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface DictItemFacade {

	public InvokeResult getDictItem(Long id);
	
	public InvokeResult creatDictItem(DictItemDTO dictItem);
	
	public InvokeResult updateDictItem(DictItemDTO dictItem);
	
	public InvokeResult removeDictItem(Long id);
	
	public InvokeResult removeDictItems(Long[] ids);
	
	public List<DictItemDTO> findAllDictItem();
	
	public Page<DictItemDTO> pageQueryDictItem(DictItemDTO dictItem, int currentPage, int pageSize);
	
	public Map loadDictItem();

}

