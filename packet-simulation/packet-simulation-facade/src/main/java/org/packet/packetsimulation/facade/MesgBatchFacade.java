package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface MesgBatchFacade {

	public InvokeResult getMesgBatch(Long id);
	
	public InvokeResult creatMesgBatch(MesgBatchDTO mesgBatch, String ctxPath);
	
	public InvokeResult updateMesgBatch(MesgBatchDTO mesgBatch);
	
	public InvokeResult removeMesgBatch(Long id);
	
	public InvokeResult removeMesgBatchs(Long[] ids, String ctxPath);
	
	public List<MesgBatchDTO> findAllMesgBatch();
	
	public Page<MesgBatchDTO> pageQueryMesgBatch(MesgBatchDTO mesgBatch, int currentPage, int pageSize, String currentUserId);
	

}

