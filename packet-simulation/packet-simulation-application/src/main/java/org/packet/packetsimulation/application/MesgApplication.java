package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.Mesg;

public interface MesgApplication {

	public Mesg getMesg(Long id);
	
	public void creatMesg(Mesg mesg);
	
	public void creatMesgs(List<Mesg> mesg);
	
	public void updateMesgs(Set<Mesg> mesg);
	
	public void updateMesg(Mesg mesg);
	
	public void removeMesg(Mesg mesg);
	
	public void removeMesgs(Set<Mesg> mesgs);
	
	public List<Mesg> findAllMesg();
	
}

