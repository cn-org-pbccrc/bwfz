package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.DictItemApplication;
import org.packet.packetsimulation.core.domain.DictItem;

@Named
@Transactional
public class DictItemApplicationImpl implements DictItemApplication {

	public DictItem getDictItem(Long id) {
		return DictItem.get(DictItem.class, id);
	}
	
	public void creatDictItem(DictItem dictItem) {
		dictItem.save();
	}
	
	public void updateDictItem(DictItem dictItem) {
		dictItem .save();
	}
	
	public void removeDictItem(DictItem dictItem) {
		if(dictItem != null){
			dictItem.remove();
		}
	}
	
	public void removeDictItems(Set<DictItem> dictItems) {
		for (DictItem dictItem : dictItems) {
			dictItem.remove();
		}
	}
	
	public List<DictItem> findAllDictItem() {
		return DictItem.findAll(DictItem.class);
	}
	
}
