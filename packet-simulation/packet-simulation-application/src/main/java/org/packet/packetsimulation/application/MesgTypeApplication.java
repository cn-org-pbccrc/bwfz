package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.MesgType;

public interface MesgTypeApplication {

	public MesgType getMesgType(Long id);
	
	public void creatMesgType(MesgType mesgType);
	
	public void updateMesgType(MesgType mesgType);
	
	public void removeMesgType(MesgType mesgType);
	
	public void removeMesgTypes(Set<MesgType> mesgTypes);
	
	public List<MesgType> findAllMesgType();
	
}

