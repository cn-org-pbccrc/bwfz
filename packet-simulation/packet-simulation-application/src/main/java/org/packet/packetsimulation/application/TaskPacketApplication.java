package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.TaskPacket;

public interface TaskPacketApplication {

	public TaskPacket getTaskPacket(Long id);
	
	public void creatTaskPacket(TaskPacket taskPacket);
	
	public void creatTaskPackets(Set<TaskPacket> taskPackets);
	
	public void updateTaskPacket(TaskPacket taskPacket);
	
	public void removeTaskPacket(TaskPacket taskPacket);
	
	public void removeTaskPackets(Set<TaskPacket> taskPackets);
	
	public List<TaskPacket> findAllTaskPacket();
	
}

