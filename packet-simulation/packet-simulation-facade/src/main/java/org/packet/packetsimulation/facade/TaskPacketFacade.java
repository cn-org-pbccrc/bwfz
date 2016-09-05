package org.packet.packetsimulation.facade;

import java.text.ParseException;
import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.bind.annotation.RequestParam;

public interface TaskPacketFacade {

	public InvokeResult getTaskPacket(Long id);
	
	public InvokeResult creatTaskPacket(TaskPacketDTO taskPacket);
	
	public InvokeResult creatTaskPackets(TaskPacketDTO taskPacket, String ctxPath, String[] flags, String[] coms, String[] encs) throws ParseException;
	
	public InvokeResult updateTaskPacket(TaskPacketDTO taskPacket);
	
	public ModelAndView uploadTaskPacket(TaskPacketDTO taskPacketDTO, String ctxPath);
	
	public InvokeResult removeTaskPacket(Long id);
	
	public InvokeResult removeTaskPackets(Long[] ids, String savePath);
	
	public InvokeResult upTaskPacket(String sourceId, String destId);
	
	public InvokeResult downTaskPacket(String sourceId, String destId);
	
	public String showPacketContent(Long id, String ctxPath);
	
	public List<TaskPacketDTO> findAllTaskPacket();
	
	public Page<TaskPacketDTO> pageQueryTaskPacket(TaskPacketDTO taskPacket, int currentPage, int pageSize, Long taskId);
	
	public Page<TaskPacketDTO> pageQueryTaskPacket(TaskPacketDTO queryVo, int currentPage, int pageSize, int taskPacketFrom, Long missionId);
}

