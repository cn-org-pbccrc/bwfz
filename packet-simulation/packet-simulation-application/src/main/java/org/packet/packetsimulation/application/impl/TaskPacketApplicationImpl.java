package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.TaskPacketApplication;
import org.packet.packetsimulation.core.domain.TaskPacket;

@Named
@Transactional
public class TaskPacketApplicationImpl implements TaskPacketApplication {

	public TaskPacket getTaskPacket(Long id) {
		return TaskPacket.get(TaskPacket.class, id);
	}
	
	public void creatTaskPacket(TaskPacket taskPacket) {
		taskPacket.save();
	}
	
	public void creatTaskPackets(Set<TaskPacket> taskPackets) {
		for (TaskPacket taskPacket : taskPackets) {
			taskPacket.save();
		}
	}
	
	public void updateTaskPacket(TaskPacket taskPacket) {
		taskPacket .save();
	}
	
	public void removeTaskPacket(TaskPacket taskPacket) {
		if(taskPacket != null){
			taskPacket.remove();
		}
	}
	
	public void removeTaskPackets(Set<TaskPacket> taskPackets) {
		for (TaskPacket taskPacket : taskPackets) {
			taskPacket.remove();
		}
	}
	
	public List<TaskPacket> findAllTaskPacket() {
		return TaskPacket.findAll(TaskPacket.class);
	}
	
}
