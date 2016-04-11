package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.MesgBatchApplication;
import org.packet.packetsimulation.core.domain.MesgBatch;

@Named
@Transactional
public class MesgBatchApplicationImpl implements MesgBatchApplication {

	public MesgBatch getMesgBatch(Long id) {
		return MesgBatch.get(MesgBatch.class, id);
	}
	
	public void creatMesgBatch(MesgBatch mesgBatch) {
		mesgBatch.save();
	}
	
	public void updateMesgBatch(MesgBatch mesgBatch) {
		mesgBatch .save();
	}
	
	public void removeMesgBatch(MesgBatch mesgBatch) {
		if(mesgBatch != null){
			mesgBatch.remove();
		}
	}
	
	public void removeMesgBatchs(Set<MesgBatch> mesgBatchs) {
		for (MesgBatch mesgBatch : mesgBatchs) {
			mesgBatch.remove();
		}
	}
	
	public void creatMesgBatchs(Set<MesgBatch> mesgBatchs) {
		for (MesgBatch mesgBatch : mesgBatchs) {
			mesgBatch.save();
		}
	}
	
	public List<MesgBatch> findAllMesgBatch() {
		return MesgBatch.findAll(MesgBatch.class);
	}
	
}
