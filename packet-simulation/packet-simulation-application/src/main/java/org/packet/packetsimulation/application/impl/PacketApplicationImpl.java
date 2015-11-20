package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.PacketApplication;
import org.packet.packetsimulation.core.domain.Packet;

@Named
@Transactional
public class PacketApplicationImpl implements PacketApplication {

	public Packet getPacket(Long id) {
		return Packet.get(Packet.class, id);
	}
	
	public void creatPacket(Packet packet) {
		packet.save();
	}
	
	public void updatePacket(Packet packet) {
		packet.save();
	}
	
	public void removePacket(Packet packet) {
		if(packet != null){
			packet.remove();
		}
	}
	
	public void removePackets(Set<Packet> packets) {
		for (Packet packet : packets) {
			packet.remove();
		}
	}
	
	public List<Packet> findAllPacket() {
		return Packet.findAll(Packet.class);
	}
	
}
