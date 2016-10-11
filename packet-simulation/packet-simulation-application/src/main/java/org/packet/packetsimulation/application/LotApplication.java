package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.Lot;

public interface LotApplication {

	public Lot getLot(Long id);
	
	public void creatLot(Lot lot);
	
	public void updateLot(Lot lot);
	
	public void removeLot(Lot lot);
	
	public void removeLots(Set<Lot> lots);
	
	public List<Lot> findAllLot();
	
}

