package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openkoala.security.org.core.domain.EmployeeUser;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class MissionAssembler {
	
	public static MissionDTO  toDTO(Mission  mission){
		if (mission == null) {
			return null;
		}
    	MissionDTO result  = new MissionDTO();
	    	result.setId (mission.getId());
     	    	result.setVersion (mission.getVersion());
     	    	result.setName (mission.getName());
     	    	result.setDirector (mission.getEmployeeUser().getId().toString());
     	    	result.setStartDate (mission.getStartDate());
     	    	result.setEndDate (mission.getEndDate());
     	    	result.setStatus (mission.getStatus());
     	    	result.setDescription (mission.getDescription());
     	    	result.setTaskCreatedTime(mission.getTaskCreatedTime());
     	    	result.setTaskCreator(mission.getTaskCreator());
     	    	result.setDirectorName(mission.getEmployeeUser().getName());
     	    	result.setDisabled(mission.isDisabled());
     	    	if(mission.getProject() != null){
     	    		result.setProjectId(mission.getProject().getId());     	    		
     	    	}
     	    return result;
	 }
	
	public static List<MissionDTO>  toDTOs(Collection<Mission>  missions){
		if (missions == null) {
			return null;
		}
		List<MissionDTO> results = new ArrayList<MissionDTO>();
		for (Mission each : missions) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static Mission  toEntity(MissionDTO  missionDTO){
	 	if (missionDTO == null) {
			return null;
		}
	 	Mission result  = new Mission();
	 	EmployeeUser employeeUser = new EmployeeUser();
	 	employeeUser.setId(Long.valueOf(missionDTO.getDirector()));
        result.setId (missionDTO.getId());
         result.setVersion (missionDTO.getVersion());
         result.setName (missionDTO.getName());
         result.setDirector (missionDTO.getDirector());
         result.setStartDate (missionDTO.getStartDate());
         result.setEndDate (missionDTO.getEndDate());
         result.setStatus (missionDTO.getStatus());
         result.setDescription (missionDTO.getDescription());
         result.setEmployeeUser(employeeUser);
         result.setTaskCreatedTime(missionDTO.getTaskCreatedTime());
         result.setTaskCreator(missionDTO.getTaskCreator());
 	  	return result;
	 }
	
	public static List<Mission> toEntities(Collection<MissionDTO> missionDTOs) {
		if (missionDTOs == null) {
			return null;
		}
		
		List<Mission> results = new ArrayList<Mission>();
		for (MissionDTO each : missionDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
