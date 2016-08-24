package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.Mission;

public interface MissionApplication {

	public Mission getMission(Long id);
	
	public void creatMission(Mission mission);
	
	public void updateMission(Mission mission);
	
	public void removeMission(Mission mission);
	
	public void removeMissions(Set<Mission> missions);
	
	public List<Mission> findAllMission();
	
}

