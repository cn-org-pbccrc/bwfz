package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.ProjectApplication;
import org.packet.packetsimulation.core.domain.Project;

@Named
@Transactional
public class ProjectApplicationImpl implements ProjectApplication {

	public Project getProject(Long id) {
		return Project.get(Project.class, id);
	}
	
	public void creatProject(Project project) {
		project.save();
	}
	
	public void updateProject(Project project) {
		project .save();
	}
	
	public void removeProject(Project project) {
		if(project != null){
			project.remove();
		}
	}
	
	public void removeProjects(Set<Project> projects) {
		for (Project project : projects) {
			project.remove();
		}
	}
	
	public List<Project> findAllProject() {
		return Project.findAll(Project.class);
	}
	
}
