package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.DictType;

public interface DictTypeApplication {

	public DictType getDictType(Long id);
	
	public void creatDictType(DictType dictType);
	
	public void updateDictType(DictType dictType);
	
	public void removeDictType(DictType dictType);
	
	public void removeDictTypes(Set<DictType> dictTypes);
	
	public List<DictType> findAllDictType();
	
}

