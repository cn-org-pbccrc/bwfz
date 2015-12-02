package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;

import org.packet.packetsimulation.core.domain.FileName;
import  org.packet.packetsimulation.core.domain.Packet;

public interface PacketApplication {

	public Packet getPacket(Long id);
	
	public FileName getFileName(Long id);
	
	public void creatPacket(Packet packet);
	
	public void updatePacket(Packet packet);
	
	public void removePacket(Packet packet);
	
	public void removePackets(Set<Packet> packets);
	
	public List<Packet> findAllPacket();
	
	public void creatFileName(FileName fileName);
	
	public void updateFileName(FileName fileName);
	
}

