package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.LotApplication;
import org.packet.packetsimulation.core.domain.Lot;

@Named
@Transactional
public class LotApplicationImpl implements LotApplication {

	public Lot getLot(Long id) {
		return Lot.get(Lot.class, id);
	}
	
	public void creatLot(Lot lot) {
		lot.save();
	}
	
	public void updateLot(Lot lot) {
		lot .save();
	}
	
	public void removeLot(Lot lot) {
		if(lot != null){
			lot.remove();
		}
	}
	
	public void removeLots(Set<Lot> lots) {
		for (Lot lot : lots) {
			lot.remove();
		}
	}
	
	public List<Lot> findAllLot() {
		return Lot.findAll(Lot.class);
	}
	
}
