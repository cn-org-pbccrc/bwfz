package org.packet.packetsimulation.facade;

import java.util.List;



import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface MesgFacade {

	public InvokeResult getMesg(Long id);
	
	public InvokeResult creatMesg(MesgDTO mesg);
	
	public InvokeResult verifyMesgType(Long id);
	
	public InvokeResult creatMesgs(MesgDTO mesg,String realPath,String[] values);
	
	public InvokeResult creatMesgsByInput(MesgDTO mesg,int startOfThreeStandard,int endOfThreeStandard,String currentUserId);
	
	public Long queryCountOfThreeStandard(String currentUserId);
	
	public InvokeResult creatBatch(MesgDTO mesg,String realPath,int batchNumber);
	
	public InvokeResult updateMesg(MesgDTO mesg);
	
	public InvokeResult removeMesg(Long id);
	
	public InvokeResult removeMesgs(Long[] ids);
	
	public List<MesgDTO> findAllMesg();
	
	public Page<MesgDTO> pageQueryMesg(MesgDTO mesg, int currentPage, int pageSize, Long packetId, Integer mesgFrom);
	
	public InvokeResult getMesgForUpdate(Long id);
	
	public InvokeResult getMesgForBatch(Long id);
	
	public List<MesgDTO> queryMesgByPacketId(Long packetId);
	
	/**
	 * 获取发送前文件内容
	 * @param ids
	 * @param mesgType
	 * @param userAccount
	 * @return
	 */
	public String getMesgForSend(Long[] ids,String mesgType,String userAccount);

	/**
	 * 快速创建任务
	 * @param taskDTO
	 * @param taskPacketDTO
	 * @param mesgContent
	 * @param filePath
	 * @return
	 */
	public InvokeResult createTask(TaskDTO taskDTO, TaskPacketDTO taskPacketDTO, String mesgContent,String filePath);
	
	/**
	 * 获取发送前文件头内容
	 * @param mesgType
	 * @param userAccount
	 * @return
	 */
	public String getFileHeaderForSend(String mesgType, String userAccount);
	
}

