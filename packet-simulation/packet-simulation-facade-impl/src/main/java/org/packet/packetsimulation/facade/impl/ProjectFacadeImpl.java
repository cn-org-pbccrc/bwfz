package org.packet.packetsimulation.facade.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.application.ProjectApplication;
import org.packet.packetsimulation.core.domain.Project;
import org.packet.packetsimulation.facade.ProjectFacade;
import org.packet.packetsimulation.facade.dto.ProjectDTO;
import org.packet.packetsimulation.facade.impl.assembler.ProjectAssembler;

@Named
public class ProjectFacadeImpl implements ProjectFacade {

	@Inject
	private ProjectApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getProject(Long id) {
		return InvokeResult.success(ProjectAssembler.toDTO(application.getProject(id)));
	}
	
	public InvokeResult creatProject(ProjectDTO projectDTO) {
		application.creatProject(ProjectAssembler.toEntity(projectDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateProject(ProjectDTO projectDTO) {
		application.updateProject(ProjectAssembler.toEntity(projectDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeProject(Long id) {
		application.removeProject(application.getProject(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeProjects(Long[] ids) {
		Set<Project> projects= new HashSet<Project>();
		for (Long id : ids) {
			projects.add(application.getProject(id));
		}
		application.removeProjects(projects);
		return InvokeResult.success();
	}
	
	public List<ProjectDTO> findAllProject() {
		return ProjectAssembler.toDTOs(application.findAllProject());
	}
	
	public Page<ProjectDTO> pageQueryProject(ProjectDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _project from Project _project   where 1=1 ");
	   	if (queryVo.getProjectName() != null && !"".equals(queryVo.getProjectName())) {
	   		jpql.append(" and _project.projectName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getProjectName()));
	   	}		
	   	if (queryVo.getProjectCode() != null && !"".equals(queryVo.getProjectCode())) {
	   		jpql.append(" and _project.projectCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getProjectCode()));
	   	}		
	   	if (queryVo.getProjectManager() != null && !"".equals(queryVo.getProjectManager())) {
	   		jpql.append(" and _project.projectManager like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getProjectManager()));
	   	}		
	   	if (queryVo.getProjectstartDate() != null) {
	   		jpql.append(" and _project.projectstartDate between ? and ? ");
	   		conditionVals.add(queryVo.getProjectstartDate());
	   		conditionVals.add(queryVo.getProjectstartDateEnd());
	   	}	
	   	if (queryVo.getProjectendDate() != null) {
	   		jpql.append(" and _project.projectendDate between ? and ? ");
	   		conditionVals.add(queryVo.getProjectendDate());
	   		conditionVals.add(queryVo.getProjectendDateEnd());
	   	}	
	   	if (queryVo.getProjectRemark() != null && !"".equals(queryVo.getProjectRemark())) {
	   		jpql.append(" and _project.projectRemark like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getProjectRemark()));
	   	}		
        Page<Project> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<ProjectDTO>(pages.getStart(), pages.getResultCount(),pageSize, ProjectAssembler.toDTOs(pages.getData()));
	}
	
	
}
