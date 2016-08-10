package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class DictItemAssembler {

	public static DictItemDTO toDTO(DictItem dictItem) {
		if (dictItem == null) {
			return null;
		}
		DictItemDTO result = new DictItemDTO();
		result.setId(dictItem.getId());
		result.setVersion(dictItem.getVersion());
		result.setDictId(dictItem.getDictId());
		result.setDictItemCode(dictItem.getDictItemCode());
		result.setDictItemName(dictItem.getDictItemName());
		result.setItemSort(dictItem.getItemSort());
		result.setDelFlag(dictItem.getDelFlag());
		result.setRemark(dictItem.getRemark());

		return result;
	}

	public static List<DictItemDTO> toDTOs(Collection<DictItem> dictItems) {
		if (dictItems == null) {
			return null;
		}
		List<DictItemDTO> results = new ArrayList<DictItemDTO>();
		for (DictItem each : dictItems) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static DictItem toEntity(DictItemDTO dictItemDTO) {
		if (dictItemDTO == null) {
			return null;
		}

		//System.out.println("item sort is " + dictItemDTO.getItemSort());

		DictItem result = new DictItem();
		result.setId(dictItemDTO.getId());
		result.setVersion(dictItemDTO.getVersion());
		result.setDictId(dictItemDTO.getDictId());
		result.setDictItemCode(dictItemDTO.getDictItemCode());
		result.setDictItemName(dictItemDTO.getDictItemName());
		if (dictItemDTO.getItemSort() != null) {
			result.setItemSort(dictItemDTO.getItemSort());
		}
		result.setDelFlag(dictItemDTO.getDelFlag());
		result.setRemark(dictItemDTO.getRemark());
		return result;
	}

	public static List<DictItem> toEntities(Collection<DictItemDTO> dictItemDTOs) {
		if (dictItemDTOs == null) {
			return null;
		}

		List<DictItem> results = new ArrayList<DictItem>();
		for (DictItemDTO each : dictItemDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
