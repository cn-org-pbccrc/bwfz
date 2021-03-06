package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface ProjectFacade {

	public InvokeResult getProject(Long id);
	
	public InvokeResult creatProject(ProjectDTO project);
	
	public InvokeResult updateProject(ProjectDTO project);
	
	public InvokeResult removeProject(Long id);
	
	public InvokeResult removeProjects(Long[] ids);
	
	public List<ProjectDTO> findAllProject();
	
	public Page<ProjectDTO> pageQueryProject(ProjectDTO project, int currentPage, int pageSize);
	

}

