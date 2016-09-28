package org.packet.packetsimulation.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.impl.assembler.BatchConfigAssembler;
import org.packet.packetsimulation.facade.impl.assembler.BatchRuleAssembler;
import org.packet.packetsimulation.facade.impl.assembler.MesgTypeAssembler;
import org.packet.packetsimulation.facade.impl.assembler.QuantityConfigAssembler;
import org.packet.packetsimulation.facade.impl.assembler.QuantityRuleAssembler;
import org.packet.packetsimulation.facade.impl.assembler.RecordTypeAssembler;
import org.packet.packetsimulation.facade.QuantityConfigFacade;
import org.packet.packetsimulation.application.QuantityConfigApplication;
import org.packet.packetsimulation.application.RecordTypeApplication;
import org.packet.packetsimulation.core.domain.*;
import org.packet.packetsimulationGeneration.core.domain.RecordSegment;
import org.packet.packetsimulationGeneration.core.domain.RecordType;

@Named
public class QuantityConfigFacadeImpl implements QuantityConfigFacade {

	@Inject
	private QuantityConfigApplication  application;
	
	@Inject
	private RecordTypeApplication  recordTypeApplication;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getQuantityConfig(Long id) {
		return InvokeResult.success(QuantityConfigAssembler.toDTO(application.getQuantityConfig(id)));
	}
	
	public InvokeResult creatQuantityConfig(QuantityConfigDTO quantityConfigDTO) {
		quantityConfigDTO.setRecordTypeDTO(RecordTypeAssembler.toDTO(recordTypeApplication.getRecordType(quantityConfigDTO.getRecordTypeDTO().getId())));
		application.creatQuantityConfig(QuantityConfigAssembler.toEntity(quantityConfigDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateQuantityConfig(QuantityConfigDTO quantityConfigDTO) {
		try {
			QuantityConfig quantityConfig = application.getQuantityConfig(quantityConfigDTO.getId());
			quantityConfig.setQuantityRules(QuantityRuleAssembler.toEntityList(quantityConfigDTO.getQuantityRules()));
			application.updateQuantityConfig(quantityConfig);
			return InvokeResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return InvokeResult.failure("修改批量规则失败");
		}
	}
	
	public InvokeResult removeQuantityConfig(Long id) {
		application.removeQuantityConfig(application.getQuantityConfig(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeQuantityConfigs(Long[] ids) {
		Set<QuantityConfig> quantityConfigs= new HashSet<QuantityConfig>();
		for (Long id : ids) {
			quantityConfigs.add(application.getQuantityConfig(id));
		}
		application.removeQuantityConfigs(quantityConfigs);
		return InvokeResult.success();
	}
	
	public List<QuantityConfigDTO> findAllQuantityConfig() {
		return QuantityConfigAssembler.toDTOs(application.findAllQuantityConfig());
	}
	
	public InvokeResult getRecordSegments(Long id){
		List<RecordSegment> recordSegments = findRecordSegmentsByRecordTypeId(id);
		return InvokeResult.success(recordSegments);
	}
	
	public Page<QuantityConfigDTO> pageQueryQuantityConfig(QuantityConfigDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _quantityConfig from QuantityConfig _quantityConfig   where 1=1 ");
	   	if (queryVo.getCreatedBy() != null && !"".equals(queryVo.getCreatedBy())) {
	   		jpql.append(" and _quantityConfig.createdBy like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreatedBy()));
	   	}		
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _quantityConfig.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
        Page<QuantityConfig> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<QuantityConfigDTO>(pages.getStart(), pages.getResultCount(),pageSize, QuantityConfigAssembler.toDTOs(pages.getData()));
	}
	
	public List<QuantityConfigDTO> queryQuantityConfig(QuantityConfigDTO quantityConfigDTO){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _quantityConfig from QuantityConfig _quantityConfig where 1=1 ");
	   	if (quantityConfigDTO.getCreatedBy() != null && !"".equals(quantityConfigDTO.getCreatedBy())) {
	   		jpql.append(" and _quantityConfig.createdBy = ?");
	   		conditionVals.add(quantityConfigDTO.getCreatedBy());
	   	}		
	   	if (quantityConfigDTO.getRecordTypeDTO().getId() != null) {
	   		jpql.append(" and _quantityConfig.recordType.id = ? ");
	   		conditionVals.add(quantityConfigDTO.getRecordTypeDTO().getId());
	   	}	
		return QuantityConfigAssembler.toDTOs(getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list());
	}
	
	@SuppressWarnings("unchecked")
	private List<RecordSegment> findRecordSegmentsByRecordTypeId(Long recordTypeId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _recordSegment from RecordSegment _recordSegment  where 1=1 ");
	   	
	   	if (recordTypeId != null ) {
	   		jpql.append(" and _recordSegment.recordType.id = ? ");
	   		conditionVals.add(recordTypeId);
	   	}
	   	List<RecordSegment> recordSegmentList = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return recordSegmentList;
	}
}
