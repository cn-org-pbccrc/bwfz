package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class PacketAssembler {
	
	public static PacketDTO  toDTO(Packet  packet){
		if (packet == null) {
			return null;
		}
		PacketDTO result  = new PacketDTO();
	    	result.setId (packet.getId());
     	    result.setVersion (packet.getVersion());
     	    result.setPacketName(packet.getPacketName());
     	    result.setFileVersion(packet.getFileVersion());
     	    result.setDataType(packet.getDataType());
     	    result.setRecordType(packet.getRecordType());
     	    result.setMesgNum(packet.getMesgCountOfPacket(packet).toString());
     	    result.setReserve(packet.getReserve());
     	    result.setCreatedBy(packet.getCreatedBy());
     	   	result.setOrigSendDate(new Date(packet.getOrigSendDate().getTime()));
     	    result.setOrigSender(packet.getOrigSender());
     	return result;
	 }
	
	public static List<PacketDTO>  toDTOs(Collection<Packet>  mesgs){
		if (mesgs == null) {
			return null;
		}
		List<PacketDTO> results = new ArrayList<PacketDTO>();
		for (Packet each : mesgs) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static Packet  toEntity(PacketDTO  packetDTO){
	 	if (packetDTO == null) {
			return null;
		}
	 	Packet result  = new Packet();
        result.setId (packetDTO.getId());
        result.setVersion (packetDTO.getVersion());
        result.setPacketName(packetDTO.getPacketName());
        result.setFileVersion(packetDTO.getFileVersion());
  	    result.setDataType(packetDTO.getDataType());
  	    result.setRecordType(packetDTO.getRecordType());
  	    result.setMesgNum(packetDTO.getMesgNum());
  	    result.setReserve(packetDTO.getReserve()); 
  	    result.setCreatedBy(packetDTO.getCreatedBy());
  	   	result.setOrigSendDate(packetDTO.getOrigSendDate());
  	    result.setOrigSender(packetDTO.getOrigSender());
 	  	return result;
	 }
	 
	public static List<Packet> toEntities(Collection<PacketDTO> packetDTOs) {
		if (packetDTOs == null) {
			return null;
		}
		
		List<Packet> results = new ArrayList<Packet>();
		for (PacketDTO each : packetDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
