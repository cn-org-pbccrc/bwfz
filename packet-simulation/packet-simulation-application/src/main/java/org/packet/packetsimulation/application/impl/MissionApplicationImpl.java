package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.MissionApplication;
import org.packet.packetsimulation.core.domain.Mission;

@Named
@Transactional
public class MissionApplicationImpl implements MissionApplication {

	public Mission getMission(Long id) {
		return Mission.get(Mission.class, id);
	}
	
	public void creatMission(Mission mission) {
		mission.save();
	}
	
	public void updateMission(Mission mission) {
		mission .save();
	}
	
	public void removeMission(Mission mission) {
		if(mission != null){
			mission.remove();
		}
	}
	
	public void removeMissions(Set<Mission> missions) {
		for (Mission mission : missions) {
			mission.remove();
		}
	}
	
	public List<Mission> findAllMission() {
		return Mission.findAll(Mission.class);
	}
	
}
