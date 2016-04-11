package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.MesgBatch;

public interface MesgBatchApplication {

	public MesgBatch getMesgBatch(Long id);
	
	public void creatMesgBatch(MesgBatch mesgBatch);
	
	public void updateMesgBatch(MesgBatch mesgBatch);
	
	public void removeMesgBatch(MesgBatch mesgBatch);
	
	public void removeMesgBatchs(Set<MesgBatch> mesgBatchs);
	
	public void creatMesgBatchs(Set<MesgBatch> mesgBatchs);
	
	public List<MesgBatch> findAllMesgBatch();
	
}

