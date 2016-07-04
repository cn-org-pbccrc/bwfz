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
import org.packet.packetsimulation.facade.impl.assembler.RecordTypeAssembler;
import org.packet.packetsimulation.facade.RecordTypeFacade;
import org.packet.packetsimulation.application.RecordSegmentApplication;
import org.packet.packetsimulation.application.RecordTypeApplication;
import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulationGeneration.core.domain.*;

@Named
public class RecordTypeFacadeImpl implements RecordTypeFacade {

	@Inject
	private RecordTypeApplication  application;
	@Inject
	private RecordSegmentApplication recordSegmentApplication  ;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getRecordType(Long id) {
		return InvokeResult.success(RecordTypeAssembler.toDTO(application.getRecordType(id)));
	}
	
	public InvokeResult creatRecordType(RecordTypeDTO recordTypeDTO) {
		application.creatRecordType(RecordTypeAssembler.toEntity(recordTypeDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateRecordType(RecordTypeDTO recordTypeDTO) {
		application.updateRecordType(RecordTypeAssembler.toEntity(recordTypeDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeRecordType(Long id) {
		application.removeRecordType(application.getRecordType(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeRecordTypes(Long[] ids) {
		Set<RecordType> recordTypes= new HashSet<RecordType>();
		for (Long id : ids) {
			recordTypes.add(application.getRecordType(id));
			Set<RecordSegment> segments = new HashSet<RecordSegment>();
			segments.addAll(findRecordSegmentsByRecordTypeId(id));
			recordSegmentApplication.removeRecordSegments(segments);;
		}
		application.removeRecordTypes(recordTypes);
		return InvokeResult.success();
	}
	
	@SuppressWarnings("unchecked")
	private List<RecordSegment> findRecordSegmentsByRecordTypeId(Long recordTypeId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _segment from RecordSegment _segment  where 1=1 ");
	   	
	   	if (recordTypeId != null ) {
	   		jpql.append(" and _segment.recordType.id = ? ");
	   		conditionVals.add(recordTypeId);
	   	}
	   	List<RecordSegment> segmentList = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return segmentList;
	}
	
	public List<RecordTypeDTO> findAllRecordType() {
		return RecordTypeAssembler.toDTOs(application.findAllRecordType());
	}
	
	public Page<RecordTypeDTO> pageQueryRecordType(RecordTypeDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _recordType from RecordType _recordType   where 1=1 ");
	   	if (queryVo.getRecordType() != null && !"".equals(queryVo.getRecordType())) {
	   		jpql.append(" and _recordType.recordType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRecordType()));
	   	}		
	   	if (queryVo.getCode() != null && !"".equals(queryVo.getCode())) {
	   		jpql.append(" and _recordType.code like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCode()));
	   	}		
	   	if (queryVo.getRecordTemp() != null && !"".equals(queryVo.getRecordTemp())) {
	   		jpql.append(" and _recordType.recordTemp like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRecordTemp()));
	   	}		
	   	if (queryVo.getCreatedBy() != null && !"".equals(queryVo.getCreatedBy())) {
	   		jpql.append(" and _recordType.createdBy like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreatedBy()));
	   	}		
	   	if (queryVo.getTransform() != null && !"".equals(queryVo.getTransform())) {
	   		jpql.append(" and _recordType.transform like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTransform()));
	   	}		
        Page<RecordType> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<RecordTypeDTO>(pages.getStart(), pages.getResultCount(),pageSize, RecordTypeAssembler.toDTOs(pages.getData()));
	}
	
	
}
