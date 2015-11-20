package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.Task;

public interface TaskApplication {

	public Task getTask(Long id);
	
	public void creatTask(Task task);
	
	public void updateTask(Task task);
	
	public void removeTask(Task task);
	
	public void removeTasks(Set<Task> tasks);
	
	public List<Task> findAllTask();
	
}

