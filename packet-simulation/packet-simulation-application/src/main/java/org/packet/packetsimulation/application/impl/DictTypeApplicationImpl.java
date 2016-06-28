package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.DictTypeApplication;
import org.packet.packetsimulation.core.domain.DictType;

@Named
@Transactional
public class DictTypeApplicationImpl implements DictTypeApplication {

	public DictType getDictType(Long id) {
		return DictType.get(DictType.class, id);
	}
	
	public void creatDictType(DictType dictType) {
		dictType.save();
	}
	
	public void updateDictType(DictType dictType) {
		dictType .save();
	}
	
	public void removeDictType(DictType dictType) {
		if(dictType != null){
			dictType.remove();
		}
	}
	
	public void removeDictTypes(Set<DictType> dictTypes) {
		for (DictType dictType : dictTypes) {
			dictType.remove();
		}
	}
	
	public List<DictType> findAllDictType() {
		return DictType.findAll(DictType.class);
	}
	
}
