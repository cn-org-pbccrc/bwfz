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
import org.packet.packetsimulation.core.domain.Segment;
import org.packet.packetsimulation.facade.RecordSegmentFacade;
import org.packet.packetsimulation.facade.dto.RecordSegmentDTO;
import org.packet.packetsimulation.facade.dto.RecordTypeDTO;
import org.packet.packetsimulation.facade.impl.assembler.MesgTypeAssembler;
import org.packet.packetsimulation.facade.impl.assembler.RecordSegmentAssembler;
import org.packet.packetsimulation.facade.impl.assembler.RecordTypeAssembler;
import org.packet.packetsimulationGeneration.core.domain.RecordItem;
import org.packet.packetsimulationGeneration.core.domain.RecordSegment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
	
	public InvokeResult getRecordSegment(Long recordSegmentId) {
		return InvokeResult.success(RecordSegmentAssembler.toDTO(application.getRecordSegment(recordSegmentId)));
	}
	
	public InvokeResult getUpdateRecordSegment(Long recordSegmentId, Long segmentId){
		Segment segment = findSegmentById(segmentId);
		String content = segment.getContent();
		JSONObject obj = JSON.parseObject(content);		
		RecordSegment recordSegment = application.getRecordSegment(recordSegmentId);
		List<RecordItem> recordItems = recordSegment.getRecordItems();
		for(int i = 0; i < recordItems.size(); i++){
			RecordItem recordItem = recordItems.get(i);
			recordItem.setItemValue(obj.getString(recordItem.getItemId()));
		}
		return InvokeResult.success(RecordSegmentAssembler.toDTO(recordSegment));
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
		RecordSegment recordSegment = RecordSegmentAssembler.toEntity(recordSegmentDTO);
		recordSegment.setRecordType(recordTypeApplication
				.getRecordType(recordSegmentDTO.getRecordTypeDTO().getId()));
		application.updateRecordSegment(recordSegment);
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
	public List<RecordSegmentDTO> findRecordSegmentByRecordType(Long id) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _segment from RecordSegment _segment  where 1=1 ");	   	
	   	if (id != null ) {
	   		jpql.append(" and _segment.recordType.id = ? ");
	   		conditionVals.add(id);
	   	}
	   	List<RecordSegment> segmentList = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return  RecordSegmentAssembler.toDTOs(segmentList);		
	}
	
	public Segment findSegmentById(Long id) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _segment from Segment _segment  where 1=1 ");	   	
	   	if (id != null ) {
	   		jpql.append(" and _segment.id = ? ");
	   		conditionVals.add(id);
	   	}
	   	Segment segment = (Segment) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return segment;		
	}
}
