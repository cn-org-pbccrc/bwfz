package org.packet.packetsimulation.facade;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.core.domain.FileName;
import org.packet.packetsimulation.facade.dto.*;

public interface PacketFacade {

	public InvokeResult getPacket(Long id);
	
	public InvokeResult creatPacket(PacketDTO packet);
	
	public InvokeResult uploadPacket(PacketDTO packetDTO, String path, String ctxPath) throws FileNotFoundException, IOException, ParseException;
	
	public InvokeResult updateUploadPacket(String fileName, String ctxPath) throws IOException, ParseException;
	
	public InvokeResult updatePacket(PacketDTO packet);
	
	public InvokeResult removePacket(Long id);
	
	public InvokeResult removePackets(Long[] ids, String savePath);
	
	public List<PacketDTO> findAllPacket();
	
	public Page<PacketDTO> pageQueryPacket(PacketDTO packet, int currentPage, int pageSize,String currentUserId);
	
	public Page<PacketDTO> pageQueryPacket(PacketDTO packet, int currentPage, int pageSize);
	
	public String downloadCSV(Long id);
	
	public InvokeResult creatFileName(String frontPosition, String serialNumber);
	
	public InvokeResult updateFileName(Long id, String serialNumber);
	
	public FileName findIdByFrontPosition(String frontPosition);
	
	//public InvokeResult getPacketView(int id);
	
}

