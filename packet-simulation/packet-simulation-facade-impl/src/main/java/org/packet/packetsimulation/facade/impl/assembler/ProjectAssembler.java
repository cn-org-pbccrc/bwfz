package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openkoala.security.org.core.domain.EmployeeUser;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class ProjectAssembler {
	
	public static ProjectDTO  toDTO(Project  project){
		if (project == null) {
			return null;
		}
    	ProjectDTO result  = new ProjectDTO();
	    	result.setId (project.getId());
     	    	result.setVersion (project.getVersion());
     	    	result.setProjectName (project.getProjectName());
     	    	result.setProjectCode (project.getProjectCode());
     	    	result.setProjectManager (project.getEmployeeUser().getId().toString());
     	    	result.setProjectManagerName (project.getEmployeeUser().getName());
     	    	result.setProjectstartDate (project.getProjectstartDate());
     	    	result.setProjectendDate (project.getProjectendDate());
     	    	result.setProjectRemark (project.getProjectRemark());
     	    return result;
	 }
	
	public static List<ProjectDTO>  toDTOs(Collection<Project>  projects){
		if (projects == null) {
			return null;
		}
		List<ProjectDTO> results = new ArrayList<ProjectDTO>();
		for (Project each : projects) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static Project  toEntity(ProjectDTO  projectDTO){
	 	if (projectDTO == null) {
			return null;
		}
	 	Project result  = new Project();
	 	EmployeeUser employeeUser =new EmployeeUser();
	 	employeeUser.setId(Long.valueOf(projectDTO.getProjectManager()));
        result.setId (projectDTO.getId());
         result.setVersion (projectDTO.getVersion());
         result.setProjectName (projectDTO.getProjectName());
         result.setProjectCode (projectDTO.getProjectCode());
         result.setProjectManager (projectDTO.getProjectManager());
         result.setProjectstartDate (projectDTO.getProjectstartDate());
         result.setProjectendDate (projectDTO.getProjectendDate());
         result.setProjectRemark (projectDTO.getProjectRemark());
         result.setEmployeeUser(employeeUser);
 	  	return result;
	 }
	
	public static List<Project> toEntities(Collection<ProjectDTO> projectDTOs) {
		if (projectDTOs == null) {
			return null;
		}
		
		List<Project> results = new ArrayList<Project>();
		for (ProjectDTO each : projectDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
