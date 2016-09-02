package org.packet.packetsimulation.facade;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.core.domain.FileName;
import org.packet.packetsimulation.core.domain.Packet;
import org.packet.packetsimulation.facade.dto.*;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

public interface PacketFacade {

	public InvokeResult getPacket(Long id);
	
	public InvokeResult creatPacket(PacketDTO packet);
	
	public ModelAndView uploadPacket(PacketDTO packetDTO, String ctxPath, String xsdPath) throws FileNotFoundException, IOException, ParseException, ParserConfigurationException, SAXException;
	
	public InvokeResult updatePacket(PacketDTO packet);
	
	public InvokeResult saveAsPacket(PacketDTO packet, String idOfPacket);
	
	public InvokeResult removePacket(Long id);
	
	public InvokeResult removePackets(Long[] ids, String savePath);
	
	public List<PacketDTO> findAllPacket();
	
	public Page<PacketDTO> pageQueryPacket(PacketDTO packet, int currentPage, int pageSize,String currentUserId);
	
	public String downloadCSV(Long id);	
}

