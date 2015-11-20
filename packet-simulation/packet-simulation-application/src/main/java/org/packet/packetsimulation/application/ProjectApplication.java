package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.Project;

public interface ProjectApplication {

	public Project getProject(Long id);
	
	public void creatProject(Project project);
	
	public void updateProject(Project project);
	
	public void removeProject(Project project);
	
	public void removeProjects(Set<Project> projects);
	
	public List<Project> findAllProject();
	
}

