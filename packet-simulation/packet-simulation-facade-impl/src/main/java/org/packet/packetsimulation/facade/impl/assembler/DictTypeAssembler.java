package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class DictTypeAssembler {

	public static DictTypeDTO toDTO(DictType dictType) {
		if (dictType == null) {
			return null;
		}
		DictTypeDTO result = new DictTypeDTO();
		result.setId(dictType.getId());
		result.setVersion(dictType.getVersion());
		result.setUpDictId(dictType.getUpDictId());
		result.setDictName(dictType.getDictName());
		result.setDictClassify(dictType.getDictClassify());
		result.setCreateUserId(dictType.getCreateUserId());
		result.setCreateTime(dictType.getCreateTime());
		result.setMendUserId(dictType.getMendUserId());
		result.setMendTime(dictType.getMendTime());
		result.setRemark(dictType.getRemark());
		result.setDictItemSet(DictItemAssembler.toDTOs(dictType.getDictItemSet()));
		return result;
	}

	public static List<DictTypeDTO> toDTOs(Collection<DictType> dictTypes) {
		if (dictTypes == null) {
			return null;
		}
		List<DictTypeDTO> results = new ArrayList<DictTypeDTO>();
		for (DictType each : dictTypes) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static DictType toEntity(DictTypeDTO dictTypeDTO) {
		if (dictTypeDTO == null) {
			return null;
		}
		DictType result = new DictType();
		if(dictTypeDTO.getId()!=null){
			result.setId(dictTypeDTO.getId());
		}
		result.setVersion(dictTypeDTO.getVersion());
		if(dictTypeDTO.getUpDictId()!=null){
			result.setUpDictId(dictTypeDTO.getUpDictId());
		}
		result.setDictName(dictTypeDTO.getDictName());
		if(dictTypeDTO.getDictClassify()!=null){
			result.setDictClassify(dictTypeDTO.getDictClassify());
		}
	    if(dictTypeDTO.getCreateUserId()!=null){
			result.setCreateUserId(dictTypeDTO.getCreateUserId());

	    }
		result.setCreateTime(dictTypeDTO.getCreateTime());
		if(dictTypeDTO.getMendUserId()!=null){
			result.setMendUserId(dictTypeDTO.getMendUserId());
		}
		result.setMendTime(dictTypeDTO.getMendTime());
		result.setRemark(dictTypeDTO.getRemark());
		result.setDictItemSet(DictItemAssembler.toEntities(dictTypeDTO.getDictItemSet()));
		return result;
	}

	public static List<DictType> toEntities(Collection<DictTypeDTO> dictTypeDTOs) {
		if (dictTypeDTOs == null) {
			return null;
		}

		List<DictType> results = new ArrayList<DictType>();
		for (DictTypeDTO each : dictTypeDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
