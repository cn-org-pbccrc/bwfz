package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.List;

import org.openkoala.gqc.core.domain.PreQueryCondition;
import org.openkoala.gqc.core.domain.QueryOperation;
import org.openkoala.gqc.facade.dto.PreQueryConditionDTO;
import org.packet.packetsimulation.core.domain.BatchRule;
import org.packet.packetsimulation.core.domain.QuantityRule;
import org.packet.packetsimulation.facade.dto.BatchRuleDTO;
import org.packet.packetsimulation.facade.dto.QuantityRuleDTO;

public class QuantityRuleAssembler {
	public static QuantityRule toEntity(QuantityRuleDTO dto){
		QuantityRule qr = new QuantityRule();
		qr.setCnName(dto.getCnName());
		qr.setRuleType(dto.getRuleType());
		qr.setRuleProperties(dto.getRuleProperties());
		qr.setInUse(dto.getInUse());
		qr.setSegMark(dto.getSegMark());
		qr.setItemId(dto.getItemId());
		qr.setItemLength(dto.getItemLength());
		qr.setItemType(dto.getItemType());
		return qr;
	}
	
	public static QuantityRuleDTO toDTO(QuantityRule entity){
		QuantityRuleDTO dto = new QuantityRuleDTO();
		dto.setCnName(entity.getCnName());
		dto.setRuleType(entity.getRuleType());
		dto.setRuleProperties(entity.getRuleProperties());
		dto.setInUse(entity.getInUse());
		dto.setSegMark(entity.getSegMark());
		dto.setItemId(entity.getItemId());
		dto.setItemLength(entity.getItemLength());
		dto.setItemType(dto.getRuleType());
		return dto;
	}
	
	public static List<QuantityRule> toEntityList(List<QuantityRuleDTO> list){
		ArrayList<QuantityRule> result = new ArrayList<QuantityRule>(list.size());
		for(QuantityRuleDTO dto : list){
			result.add(toEntity(dto));
		}
		return result;
	}
	public static List<QuantityRuleDTO> toDTOList(List<QuantityRule> list){
		ArrayList<QuantityRuleDTO> result = new ArrayList<QuantityRuleDTO>(list.size());
		for(QuantityRule entity : list){
			result.add(toDTO(entity));
		}
		return result;	
	}
}
