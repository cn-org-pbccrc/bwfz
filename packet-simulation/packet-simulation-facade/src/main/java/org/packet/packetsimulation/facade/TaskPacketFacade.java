package org.packet.packetsimulation.facade;

import java.text.ParseException;
import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
//import org.springframework.web.bind.annotation.RequestParam;

public interface TaskPacketFacade {

	public InvokeResult getTaskPacket(Long id);
	
	public InvokeResult creatTaskPacket(TaskPacketDTO taskPacket);
	
	public InvokeResult creatOutSideTaskPacket(TaskPacketDTO taskPacket, String fileName) throws ParseException;
	
	public InvokeResult updateOutSideTaskPacket(String fileName) throws ParseException;
	
	public InvokeResult creatTaskPackets(TaskPacketDTO taskPacket, String[] values, String[] vers, String[] senders, String[] dates, String[] datTs, String[] recTs, String[] coms, String[] encs) throws ParseException;
	
	public InvokeResult updateTaskPacket(TaskPacketDTO taskPacket);
	
	public InvokeResult removeTaskPacket(Long id);
	
	public InvokeResult removeTaskPackets(Long[] ids, String savePath);
	
	public InvokeResult upTaskPacket(String sourceId, String destId);
	
	public InvokeResult downTaskPacket(String sourceId, String destId);
	
	public List<TaskPacketDTO> findAllTaskPacket();
	
	public Page<TaskPacketDTO> pageQueryTaskPacket(TaskPacketDTO taskPacket, int currentPage, int pageSize,Long taskId);
	

}

