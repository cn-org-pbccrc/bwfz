package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.TaskApplication;
import org.packet.packetsimulation.core.domain.Task;

@Named
@Transactional
public class TaskApplicationImpl implements TaskApplication {

	public Task getTask(Long id) {
		return Task.get(Task.class, id);
	}
	
	public void creatTask(Task task) {
		task.save();
	}
	
	public void updateTask(Task task) {
		task .save();
	}
	
	public void removeTask(Task task) {
		if(task != null){
			task.remove();
		}
	}
	
	public void removeTasks(Set<Task> tasks) {
		for (Task task : tasks) {
			task.remove();
		}
	}
	
	public List<Task> findAllTask() {
		return Task.findAll(Task.class);
	}
	
}
