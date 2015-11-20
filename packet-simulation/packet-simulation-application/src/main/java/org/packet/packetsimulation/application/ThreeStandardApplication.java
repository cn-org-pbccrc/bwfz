package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;

import org.packet.packetsimulation.core.domain.TaskPacket;
import  org.packet.packetsimulation.core.domain.ThreeStandard;

public interface ThreeStandardApplication {

	public ThreeStandard getThreeStandard(Long id);
	
	public void creatThreeStandard(ThreeStandard threeStandard);
	
	public void creatThreeStandards(Set<ThreeStandard> threeStandards);
	
	public void updateThreeStandard(ThreeStandard threeStandard);
	
	public void removeThreeStandard(ThreeStandard threeStandard);
	
	public void removeThreeStandards(Set<ThreeStandard> threeStandards);
	
	public List<ThreeStandard> findAllThreeStandard();
	
}

