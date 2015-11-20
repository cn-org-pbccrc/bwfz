package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface TaskFacade {

	public InvokeResult getTask(Long id);
	
	public InvokeResult creatTask(TaskDTO task);
	
	public InvokeResult updateTask(TaskDTO task);
	
	public InvokeResult removeTask(Long id);
	
	public InvokeResult removeTasks(Long[] ids);
	
	public List<TaskDTO> findAllTask();
	
	public Page<TaskDTO> pageQueryTask(TaskDTO task, int currentPage, int pageSize, String currentUserId);
	

}

