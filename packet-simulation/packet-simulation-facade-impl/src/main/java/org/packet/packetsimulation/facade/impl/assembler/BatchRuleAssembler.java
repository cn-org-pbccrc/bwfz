package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.List;

import org.openkoala.gqc.core.domain.PreQueryCondition;
import org.openkoala.gqc.core.domain.QueryOperation;
import org.openkoala.gqc.facade.dto.PreQueryConditionDTO;
import org.packet.packetsimulation.core.domain.BatchRule;
import org.packet.packetsimulation.facade.dto.BatchRuleDTO;

public class BatchRuleAssembler {
	public static BatchRule toEntity(BatchRuleDTO dto){
		BatchRule br = new BatchRule();
		br.setEnName(dto.getEnName());
		br.setCnName(dto.getCnName());
		br.setRuleType(dto.getRuleType());
		br.setXpath(dto.getXpath());
		br.setRuleProperties(dto.getRuleProperties());
		br.setInUse(dto.getInUse());
		return br;
	}
	
	public static BatchRuleDTO toDTO(BatchRule entity){
		BatchRuleDTO dto = new BatchRuleDTO();
		dto.setEnName(entity.getEnName());
		dto.setCnName(entity.getCnName());
		dto.setRuleType(entity.getRuleType());
		dto.setXpath(entity.getXpath());
		dto.setRuleProperties(entity.getRuleProperties());
		dto.setInUse(entity.getInUse());
		return dto;
	}
	
	public static List<BatchRule> toEntityList(List<BatchRuleDTO> list){
		ArrayList<BatchRule> result = new ArrayList<BatchRule>(list.size());
		for(BatchRuleDTO dto : list){
			result.add(toEntity(dto));
		}
		return result;
	}
	public static List<BatchRuleDTO> toDTOList(List<BatchRule> list){
		ArrayList<BatchRuleDTO> result = new ArrayList<BatchRuleDTO>(list.size());
		for(BatchRule entity : list){
			result.add(toDTO(entity));
		}
		return result;	
	}
	

}
