package org.packet.packetsimulation.facade.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.application.RecordApplication;
import org.packet.packetsimulation.application.RecordSegmentApplication;
import org.packet.packetsimulation.application.RecordTypeApplication;
import org.packet.packetsimulation.application.SegmentApplication;
import org.packet.packetsimulation.core.domain.Segment;
import org.packet.packetsimulation.facade.RecordSegmentFacade;
import org.packet.packetsimulation.facade.SegmentFacade;
import org.packet.packetsimulation.facade.dto.RecordSegmentDTO;
import org.packet.packetsimulation.facade.dto.RecordTypeDTO;
import org.packet.packetsimulation.facade.dto.SegmentDTO;
import org.packet.packetsimulation.facade.impl.assembler.MesgTypeAssembler;
import org.packet.packetsimulation.facade.impl.assembler.RecordSegmentAssembler;
import org.packet.packetsimulation.facade.impl.assembler.RecordTypeAssembler;
import org.packet.packetsimulation.facade.impl.assembler.SegmentAssembler;
import org.packet.packetsimulationGeneration.core.domain.Record;
import org.packet.packetsimulationGeneration.core.domain.RecordSegment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Named
public class SegmentFacadeImpl implements SegmentFacade {

	@Inject
	private SegmentApplication  application;
	
	@Inject
	private RecordTypeApplication  recordTypeApplication;
	
	@Inject
	private RecordApplication  recordApplication;

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
	
	public InvokeResult creatSegment(SegmentDTO segmentDTO) {
		Segment segment = SegmentAssembler.toEntity(segmentDTO);
		Record record = recordApplication.getRecord(segmentDTO.getRecordId());
		segment.setRecord(record);
		application.creatSegment(segment);
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
	
	public InvokeResult pageQuerySegment(SegmentDTO segmentDTO) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _segment from Segment _segment   where 1=1 ");
	   	if(segmentDTO.getRecordId()!= null && !"".equals(segmentDTO.getRecordId())){
	   		jpql.append(" and _segment.record.id = ?");
	   		conditionVals.add(segmentDTO.getRecordId());
	   	}
	   	if(segmentDTO.getSegMark()!= null && !"".equals(segmentDTO.getSegMark())){
	   		jpql.append(" and _segment.segMark = ?");
	   		conditionVals.add(segmentDTO.getSegMark());
	   	}
	   	List<Segment> segments = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	List<JSONObject> list = new ArrayList<JSONObject>();
	   	for(int i = 0; i < segments.size(); i++){
	   		JSONObject obj = JSON.parseObject(segments.get(i).getContent());
	   		list.add(obj);
	   	}
	   	return InvokeResult.success(list);
	}
}
