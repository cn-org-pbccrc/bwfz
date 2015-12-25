package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.ThreeStandardApplication;
import org.packet.packetsimulation.core.domain.TaskPacket;
import org.packet.packetsimulation.core.domain.ThreeStandard;

@Named
@Transactional
public class ThreeStandardApplicationImpl implements ThreeStandardApplication {

	public ThreeStandard getThreeStandard(Long id) {
		return ThreeStandard.get(ThreeStandard.class, id);
	}
	
	public void creatThreeStandard(ThreeStandard threeStandard) {
		threeStandard.save();
	}
	
	public void creatThreeStandards(List<ThreeStandard> threeStandards) {
		for (ThreeStandard threeStandard : threeStandards) {
			threeStandard.save();
		}
	}
	
	public void updateThreeStandard(ThreeStandard threeStandard) {
		threeStandard .save();
	}
	
	public void removeThreeStandard(ThreeStandard threeStandard) {
		if(threeStandard != null){
			threeStandard.remove();
		}
	}
	
	public void removeThreeStandards(Set<ThreeStandard> threeStandards) {
		for (ThreeStandard threeStandard : threeStandards) {
			threeStandard.remove();
		}
	}
	
	public List<ThreeStandard> findAllThreeStandard() {
		return ThreeStandard.findAll(ThreeStandard.class);
	}
	
}
