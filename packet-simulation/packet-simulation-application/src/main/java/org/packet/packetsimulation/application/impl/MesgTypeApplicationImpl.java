package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.MesgTypeApplication;
import org.packet.packetsimulation.core.domain.MesgType;

@Named
@Transactional
public class MesgTypeApplicationImpl implements MesgTypeApplication {

	public MesgType getMesgType(Long id) {
		return MesgType.get(MesgType.class, id);
	}
	
	public void creatMesgType(MesgType mesgType) {
		mesgType.save();
	}
	
	public void updateMesgType(MesgType mesgType) {
		mesgType.save();
	}
	
	public void removeMesgType(MesgType mesgType) {
		if(mesgType != null){
			mesgType.remove();
		}
	}
	
	public void removeMesgTypes(Set<MesgType> mesgTypes) {
		for (MesgType mesgType : mesgTypes) {
			mesgType.remove();
		}
	}
	
	public List<MesgType> findAllMesgType() {
		return MesgType.findAll(MesgType.class);
	}
	
}
