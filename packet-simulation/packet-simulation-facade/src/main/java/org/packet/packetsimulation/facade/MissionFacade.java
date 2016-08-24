package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface MissionFacade {

	public InvokeResult getMission(Long id);
	
	public InvokeResult getProject(Long id);
	
	public InvokeResult creatMission(MissionDTO mission);
	
	public InvokeResult updateMission(MissionDTO mission);
	
	public InvokeResult removeMission(Long id);
	
	public InvokeResult removeMissions(Long[] ids);
	
	public List<MissionDTO> findAllMission();
	
	public InvokeResult suspend(Long missionId);
	
	public InvokeResult activate(Long missionId);
	
	public Page<MissionDTO> pageQueryMission(MissionDTO mission, int currentPage, int pageSize, Long projectId, String currentUserAccount);
	
	public Page<MissionDTO> pagingQueryMissionsByCurrentUser(int page, int pagesize, Long projectId, String currentUserAccount);

}

