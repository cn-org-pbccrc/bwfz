package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.DictItem;

public interface DictItemApplication {

	public DictItem getDictItem(Long id);
	
	public void creatDictItem(DictItem dictItem);
	
	public void updateDictItem(DictItem dictItem);
	
	public void removeDictItem(DictItem dictItem);
	
	public void removeDictItems(Set<DictItem> dictItems);
	
	public List<DictItem> findAllDictItem();
	
}

