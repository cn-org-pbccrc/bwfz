package org.packet.packetsimulation.facade;

import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.facade.dto.*;

public interface MesgTypeFacade {

	public InvokeResult getMesgType(Long id);
	
	public InvokeResult creatMesgType(MesgTypeDTO mesgType);
	
	public InvokeResult updateMesgType(MesgTypeDTO mesgType);
	
	public InvokeResult removeMesgType(Long id);
	
	public InvokeResult removeMesgTypes(Long[] ids);
	
	public List<MesgTypeDTO> findAllMesgType();
	
	public List<MesgType> findMesgTypes();
	
	public Page<MesgTypeDTO> pageQueryMesgType(MesgTypeDTO mesgType, int currentPage, int pageSize);
	
//	public InvokeResult getEditHtmlByMesgType(Long id,String realPath);
	
	public InvokeResult upMesgType(String sourceId, String destId);
	
	public InvokeResult downMesgType(String sourceId, String destId);
	
	public InvokeResult getEditHtmlByMesgType(Long id);
	
	public InvokeResult getEditHtmlByCode(String code, String sourceCode, String xml);
	
	public InvokeResult getEditHtmlOfChange(String code, String finanCode, String cstCode);

	public List<MesgType> findMesgTypesByCreateUser(String userName);
	
}

