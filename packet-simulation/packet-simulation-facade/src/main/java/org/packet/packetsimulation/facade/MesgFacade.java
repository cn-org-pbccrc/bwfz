package org.packet.packetsimulation.facade;

import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface MesgFacade {

	public InvokeResult getMesg(Long id);
	
	public InvokeResult creatMesg(MesgDTO mesg,String realPath);
	
	public InvokeResult verifyMesgType(Long id);
	
	public InvokeResult creatMesgs(MesgDTO mesg,String realPath,String[] values);
	
	public InvokeResult creatMesgsByInput(MesgDTO mesg,int startOfThreeStandard,int endOfThreeStandard,String currentUserId);
	
	public Long queryCountOfThreeStandard(String currentUserId);
	
	public InvokeResult creatBatch(MesgDTO mesg,String realPath,int batchNumber);
	
	public InvokeResult updateMesg(MesgDTO mesg,String realPath);
	
	public InvokeResult removeMesg(Long id);
	
	public InvokeResult removeMesgs(Long[] ids);
	
	public List<MesgDTO> findAllMesg();
	
	public Page<MesgDTO> pageQueryMesg(MesgDTO mesg, int currentPage, int pageSize,Long packetId);
	
	public InvokeResult getMesgForUpdate(Long id);
	
	public InvokeResult getMesgForBatch(Long id);
	
	public List<MesgDTO> queryMesgByPacketId(Long packetId);
	
}

