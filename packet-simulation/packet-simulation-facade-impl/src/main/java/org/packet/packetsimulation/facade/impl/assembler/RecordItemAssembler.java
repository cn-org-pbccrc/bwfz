package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.List;

import org.packet.packetsimulationGeneration.core.domain.RecordItem;
import org.packet.packetsimulationGeneration.facade.dto.RecordItemDTO;

public class RecordItemAssembler {

	public static RecordItem toEntity(RecordItemDTO dto){
		RecordItem ri = new RecordItem();
		ri.setItemDesc(dto.getItemDesc());
		ri.setItemId(dto.getItemId());
		ri.setItemLength(dto.getItemLength());
		ri.setItemLocation(dto.getItemLocation());
		ri.setItemName(dto.getItemName());
		ri.setItemType(dto.getItemType());
		ri.setState(dto.getState());
		return ri;
	}
	
	public static RecordItemDTO toDTO(RecordItem entity){
		RecordItemDTO dto = new RecordItemDTO();
		dto.setItemDesc(entity.getItemDesc());
		dto.setItemId(entity.getItemId());
		dto.setItemLength(entity.getItemLength());
		dto.setItemLocation(entity.getItemLocation());
		dto.setItemName(entity.getItemName());
		dto.setItemType(entity.getItemType());
		dto.setState(entity.getState());
		return dto;
	}
	
	public static List<RecordItem> toEntityList(List<RecordItemDTO> list){
		ArrayList<RecordItem> result = new ArrayList<RecordItem>(list.size());
		for(RecordItemDTO dto : list){
			result.add(toEntity(dto));
		}
		return result;
	}
	public static List<RecordItemDTO> toDTOList(List<RecordItem> list){
		ArrayList<RecordItemDTO> result = new ArrayList<RecordItemDTO>(list.size());
		for(RecordItem entity : list){
			result.add(toDTO(entity));
		}
		return result;	
	}
	


}
