package org.packet.packetsimulation.facade.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.core.domain.Authorization;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.impl.assembler.MissionAssembler;
import org.packet.packetsimulation.facade.impl.assembler.ProjectAssembler;
import org.packet.packetsimulation.facade.MissionFacade;
import org.packet.packetsimulation.facade.PacketFacade;
import org.packet.packetsimulation.facade.TaskFacade;
import org.packet.packetsimulation.application.MissionApplication;
import org.packet.packetsimulation.application.PacketApplication;
import org.packet.packetsimulation.application.ProjectApplication;
import org.packet.packetsimulation.core.domain.*;

@Named
public class MissionFacadeImpl implements MissionFacade {

	@Inject
	private MissionApplication  application;
	
	@Inject
	private PacketApplication  packetApplication;
	
	@Inject
	private ProjectApplication  projectApplication;
	
	@Inject
	private PacketFacade packetFacade;
	
	@Inject
	private TaskFacade taskFacade;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getMission(Long id) {
		return InvokeResult.success(MissionAssembler.toDTO(application.getMission(id)));
	}
	
	public InvokeResult getProject(Long id){
		Mission mission = application.getMission(id);
		Project project = projectApplication.getProject(mission.getProject().getId());
		return InvokeResult.success(ProjectAssembler.toDTO(project));
	}
	
	public InvokeResult creatMission(MissionDTO missionDTO) {
		Mission mission = MissionAssembler.toEntity(missionDTO);
		Project project = projectApplication.getProject(missionDTO.getProjectId());
		mission.setProject(project);
		mission.setTaskCreatedTime(new Date());
		mission.setTaskCreator(project.getEmployeeUser().getName());
		mission.setDisabled(false);
		application.creatMission(mission);
		return InvokeResult.success();
	}
	
	public InvokeResult updateMission(MissionDTO missionDTO) {
		Mission mission = MissionAssembler.toEntity(missionDTO);
		Project project = projectApplication.getProject(missionDTO.getProjectId());
		mission.setProject(project);
		application.updateMission(mission);
		return InvokeResult.success();
	}
	
	public InvokeResult renewMission(MissionDTO missionDTO){
		Mission mission = MissionAssembler.toEntity(missionDTO);
		Project project = projectApplication.getProject(missionDTO.getProjectId());
		mission.setProject(project);
		application.updateMission(mission);
		return InvokeResult.success();
	}
	
	public InvokeResult removeMission(Long id) {
		application.removeMission(application.getMission(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeMissions(Long[] ids, String savePath) {
		Set<Mission> missions= new HashSet<Mission>();
		for (Long id : ids) {
			missions.add(application.getMission(id));
			List<Long> packetIds = findPacketIdsByMissionId(id);
			if(packetIds != null){				
				packetFacade.removePackets(packetIds.toArray(new Long[packetIds.size()]));
			}
			List<Long> taskIds = findTaskIdsByMissionId(id);
			if(taskIds != null){
				taskFacade.removeAllTasks(taskIds.toArray(new Long[taskIds.size()]), savePath);
			}
		}
		application.removeMissions(missions);
		return InvokeResult.success();
	}
	
	public List<MissionDTO> findAllMission() {
		return MissionAssembler.toDTOs(application.findAllMission());
	}
	
	public InvokeResult suspend(Long missionId){
		Mission mission = application.getMission(missionId);
        if (mission.isDisabled()) {
            return InvokeResult.failure("任务:" + mission.getName() + "已经是禁用状态，不需要再次禁用！");
        }
		mission.setDisabled(true);
		application.updateMission(mission);
		return InvokeResult.success();
	}
	
	public InvokeResult activate(Long missionId){
		Mission mission = application.getMission(missionId);
		if (!mission.isDisabled()) {
            return InvokeResult.failure("任务:" + mission.getName() + "已经是激活状态，不需要再次激活！");
        }
		mission.setDisabled(false);
		application.updateMission(mission);
		return InvokeResult.success();
	}
	
	public Page<MissionDTO> pageQueryMission(MissionDTO queryVo, int currentPage, int pageSize, Long projectId, String currentUserAccount) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mission from Mission _mission   where 1=1 ");
	   	if(currentUserAccount != null && projectId == null){
			EmployeeUser employeeUser = findEmployeeUserByCurrentUserAccount(currentUserAccount);
			Long employeeUserId = employeeUser.getId();
			List<Authorization> authorizations = findAuthorizationsByEmployeeUserId(employeeUserId);
			int flag = 0;
			for(int i = 0; i < authorizations.size(); i++){
				if(authorizations.get(i).getAuthority().getId() == 1){
					flag = 1;
					break;
				}
			}
			if(flag == 0){
				jpql.append(" and _mission.employeeUser.id = ?");
		   		conditionVals.add(employeeUserId);
		   		jpql.append(" and _mission.disabled = ?");
		   		conditionVals.add(false);
			}
		}
	   	if (projectId != null ) {
	   		jpql.append(" and _mission.project.id = ? ");
	   		conditionVals.add(projectId);
	   	}
	   	if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
	   		jpql.append(" and _mission.name like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getName()));
	   	}		
	   	if (queryVo.getDirector() != null && !"".equals(queryVo.getDirector())) {
	   		jpql.append(" and _mission.director like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDirector()));
	   	}		
	   	if (queryVo.getStartDate() != null) {
	   		jpql.append(" and _mission.startDate between ? and ? ");
	   		conditionVals.add(queryVo.getStartDate());
	   		conditionVals.add(queryVo.getStartDateEnd());
	   	}	
	   	if (queryVo.getEndDate() != null) {
	   		jpql.append(" and _mission.endDate between ? and ? ");
	   		conditionVals.add(queryVo.getEndDate());
	   		conditionVals.add(queryVo.getEndDateEnd());
	   	}	
	   	if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
	   		jpql.append(" and _mission.status like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getStatus()));
	   	}		
	   	if (queryVo.getDescription() != null && !"".equals(queryVo.getDescription())) {
	   		jpql.append(" and _mission.description like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDescription()));
	   	}		
        Page<Mission> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<MissionDTO>(pages.getStart(), pages.getResultCount(), pageSize, MissionAssembler.toDTOs(pages.getData()));
	}
	
	public Page<MissionDTO> pagingQueryMissionsByCurrentUser(int page, int pagesize, Long projectId, String currentUserAccount){
		StringBuilder jpql = new StringBuilder("select _mission from Mission _mission where _mission.employeeUser.id = (select _employeeUser.id from EmployeeUser _employeeUser  where _employeeUser.userAccount = ?) and _mission.project.id = ? and _mission.disabled = ?");
		List<Object> conditionVals = new ArrayList<Object>();
		conditionVals.add(currentUserAccount);
		conditionVals.add(projectId);
		conditionVals.add(false);
		Page<Mission> pages = getQueryChannelService()
				   .createJpqlQuery(jpql.toString())
				   .setParameters(conditionVals)
				   .setPage(page, pagesize)
				   .pagedList();
		return new Page<MissionDTO>(pages.getStart(), pages.getResultCount(), pagesize, MissionAssembler.toDTOs(pages.getData()));
	}
	
	@SuppressWarnings("unchecked")
	private EmployeeUser findEmployeeUserByCurrentUserAccount(String currentUserAccount){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _employeeUser from EmployeeUser _employeeUser  where 1=1 ");	   	
	   	if (currentUserAccount != null ) {
	   		jpql.append(" and _employeeUser.userAccount = ? ");
	   		conditionVals.add(currentUserAccount);
	   	}
	   	EmployeeUser employeeUser = (EmployeeUser) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return employeeUser;
	}
	
	@SuppressWarnings("unchecked")
	private List<Authorization> findAuthorizationsByEmployeeUserId(Long employeeUserId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _authorization from Authorization _authorization  where 1=1 ");	   	
	   	if (employeeUserId != null ) {
	   		jpql.append(" and _authorization.actor.id = ? ");
	   		conditionVals.add(employeeUserId);
	   	}
	   	List<Authorization> authorizations = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return authorizations;
	}
	
	@SuppressWarnings("unchecked")
	private List<Long> findPacketIdsByMissionId(Long missionId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _packet.id from Packet _packet  where 1=1 ");	   	
	   	if (missionId != null ) {
	   		jpql.append(" and _packet.mission.id = ? ");
	   		conditionVals.add(missionId);
	   	}
	   	List<Long> packetIds = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return packetIds;
	}
	
	@SuppressWarnings("unchecked")
	private List<Long> findTaskIdsByMissionId(Long missionId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _task.id from Task _task  where 1=1 ");	   	
	   	if (missionId != null ) {
	   		jpql.append(" and _task.mission.id = ? ");
	   		conditionVals.add(missionId);
	   	}
	   	List<Long> taskIds = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return taskIds;
	}
}
