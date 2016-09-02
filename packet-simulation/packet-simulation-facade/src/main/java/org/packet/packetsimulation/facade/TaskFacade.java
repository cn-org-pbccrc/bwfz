package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface TaskFacade {

	public InvokeResult getTask(Long id);
	
	public InvokeResult creatTask(TaskDTO task, Long missionId);
	
	public InvokeResult updateTask(TaskDTO task);
	
	public InvokeResult removeTask(Long id);
	
	public InvokeResult removeTasks(Long[] ids, String savePath);
	
	public InvokeResult removeAllTasks(Long[] ids, String savePath);
	
	public List<TaskDTO> findAllTask();
	
	public Page<TaskDTO> pageQueryTask(TaskDTO task, int currentPage, int pageSize, String currentUserId, Long missionId);
	

}

