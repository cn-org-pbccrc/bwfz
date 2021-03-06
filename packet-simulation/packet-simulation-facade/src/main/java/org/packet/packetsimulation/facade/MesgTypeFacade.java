package org.packet.packetsimulation.facade;

import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface MesgTypeFacade {

	public InvokeResult getMesgType(Long id);
	
	public InvokeResult creatMesgType(MesgTypeDTO mesgType);
	
	public InvokeResult updateMesgType(MesgTypeDTO mesgType);
	
	public InvokeResult removeMesgType(Long id);
	
	public InvokeResult removeMesgTypes(Long[] ids);
	
	public List<MesgTypeDTO> findAllMesgType();
	
	public Page<MesgTypeDTO> pageQueryMesgType(MesgTypeDTO mesgType, int currentPage, int pageSize);
	
	public InvokeResult getEditHtmlByMesgType(Long id,String realPath);
	
}

