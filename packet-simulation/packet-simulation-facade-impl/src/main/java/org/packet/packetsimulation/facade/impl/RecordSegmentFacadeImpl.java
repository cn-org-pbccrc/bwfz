package org.packet.packetsimulation.facade.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.application.RecordSegmentApplication;
import org.packet.packetsimulation.application.RecordTypeApplication;
import org.packet.packetsimulation.facade.RecordSegmentFacade;
import org.packet.packetsimulation.facade.dto.RecordSegmentDTO;
import org.packet.packetsimulation.facade.dto.RecordTypeDTO;
import org.packet.packetsimulation.facade.impl.assembler.RecordSegmentAssembler;
import org.packet.packetsimulationGeneration.core.domain.RecordSegment;

@Named
public class RecordSegmentFacadeImpl implements RecordSegmentFacade {

	@Inject
	private RecordSegmentApplication  application;
	@Inject
	private RecordTypeApplication  recordTypeApplication;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getRecordSegment(Long id) {
		return InvokeResult.success(RecordSegmentAssembler.toDTO(application.getRecordSegment(id)));
	}
	
	public InvokeResult creatRecordSegment(RecordSegmentDTO recordSegmentDTO) {
		RecordSegment recordSegment = RecordSegmentAssembler
				.toEntity(recordSegmentDTO);
		recordSegment.setRecordType(recordTypeApplication
				.getRecordType(recordSegmentDTO.getRecordTypeDTO().getId()));
		application.creatRecordSegment(recordSegment);
		return InvokeResult.success();
	}
	
	public InvokeResult updateRecordSegment(RecordSegmentDTO recordSegmentDTO) {
		application.updateRecordSegment(RecordSegmentAssembler.toEntity(recordSegmentDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeRecordSegment(Long id) {
		application.removeRecordSegment(application.getRecordSegment(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeRecordSegments(Long[] ids) {
		Set<RecordSegment> recordSegments= new HashSet<RecordSegment>();
		for (Long id : ids) {
			recordSegments.add(application.getRecordSegment(id));
		}
		application.removeRecordSegments(recordSegments);
		return InvokeResult.success();
	}
	
	public List<RecordSegmentDTO> findAllRecordSegment() {
		return RecordSegmentAssembler.toDTOs(application.findAllRecordSegment());
	}
	
	public Page<RecordSegmentDTO> pageQueryRecordSegment(RecordSegmentDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _recordSegment from RecordSegment _recordSegment   where 1=1 ");
	   	if(queryVo.getRecordTypeDTO().getId()!= null && !"".equals(queryVo.getRecordTypeDTO().getId())){
	   		jpql.append(" and _recordSegment.recordType.id = ?");
	   		conditionVals.add(queryVo.getRecordTypeDTO().getId());
	   	}
	   	if (queryVo.getSegName() != null && !"".equals(queryVo.getSegName())) {
	   		jpql.append(" and _recordSegment.segName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSegName()));
	   	}		
	   	if (queryVo.getSegMark() != null && !"".equals(queryVo.getSegMark())) {
	   		jpql.append(" and _recordSegment.segMark like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSegMark()));
	   	}		
	   	if (queryVo.getSegDesc() != null && !"".equals(queryVo.getSegDesc())) {
	   		jpql.append(" and _recordSegment.describe like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSegDesc()));
	   	}		
	   	if (queryVo.getAppearTimes() != null && !"".equals(queryVo.getAppearTimes())) {
	   		jpql.append(" and _recordSegment.appearTimes like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getAppearTimes()));
	   	}		
	   	if (queryVo.getState() != null && !"".equals(queryVo.getState())) {
	   		jpql.append(" and _recordSegment.state like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getState()));
	   	}		
        Page<RecordSegment> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<RecordSegmentDTO>(pages.getStart(), pages.getResultCount(),pageSize, RecordSegmentAssembler.toDTOs(pages.getData()));
	}

	@Override
	public RecordTypeDTO findRecordTypeByRecordSegment(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
