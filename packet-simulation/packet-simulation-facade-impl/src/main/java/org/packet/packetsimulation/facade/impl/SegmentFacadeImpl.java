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
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.Segment;
import org.packet.packetsimulation.facade.RecordSegmentFacade;
import org.packet.packetsimulation.facade.SegmentFacade;
import org.packet.packetsimulation.facade.dto.MesgTypeDTO;
import org.packet.packetsimulation.facade.dto.RecordSegmentDTO;
import org.packet.packetsimulation.facade.dto.RecordTypeDTO;
import org.packet.packetsimulation.facade.dto.SegmentDTO;
import org.packet.packetsimulation.facade.impl.assembler.MesgTypeAssembler;
import org.packet.packetsimulation.facade.impl.assembler.RecordSegmentAssembler;
import org.packet.packetsimulation.facade.impl.assembler.RecordTypeAssembler;
import org.packet.packetsimulation.facade.impl.assembler.SegmentAssembler;
import org.packet.packetsimulationGeneration.core.domain.Record;
import org.packet.packetsimulationGeneration.core.domain.RecordSegment;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	public InvokeResult getSegment(Long id) {
		return InvokeResult.success(SegmentAssembler.toDTO(application.getSegment(id)));
	}
	
	public InvokeResult creatSegment(SegmentDTO segmentDTO) {
		Segment segment = SegmentAssembler.toEntity(segmentDTO);
		Record record = recordApplication.getRecord(segmentDTO.getRecordId());
		segment.setRecord(record);
		application.creatSegment(segment);
		return InvokeResult.success();
	}
	
	public InvokeResult updateSegment(SegmentDTO segmentDTO) {
		Segment segment = SegmentAssembler.toEntity(segmentDTO);
		Record record = recordApplication.getRecord(segmentDTO.getRecordId());
		segment.setRecord(record);
		application.updateSegment(segment);
		return InvokeResult.success();
	}
	
	public InvokeResult removeRecordSegments(Long[] ids) {
		Set<Segment> segments= new HashSet<Segment>();
		for (Long id : ids) {
			segments.add(application.getSegment(id));
		}
		application.removeSegments(segments);
		return InvokeResult.success();
	}
	
	public List<RecordSegmentDTO> findAllRecordSegment() {
		return RecordSegmentAssembler.toDTOs(application.findAllRecordSegment());
	}
	
//	public InvokeResult pageQuerySegment(SegmentDTO segmentDTO) {
//		List<Object> conditionVals = new ArrayList<Object>();
//	   	StringBuilder jpql = new StringBuilder("select _segment from Segment _segment   where 1=1 ");
//	   	if(segmentDTO.getRecordId()!= null && !"".equals(segmentDTO.getRecordId())){
//	   		jpql.append(" and _segment.record.id = ?");
//	   		conditionVals.add(segmentDTO.getRecordId());
//	   	}
//	   	if(segmentDTO.getSegMark()!= null && !"".equals(segmentDTO.getSegMark())){
//	   		jpql.append(" and _segment.segMark = ?");
//	   		conditionVals.add(segmentDTO.getSegMark());
//	   	}
//	   	List<Segment> segments = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
//	   	List<JSONObject> list = new ArrayList<JSONObject>();
//	   	for(int i = 0; i < segments.size(); i++){
//	   		JSONObject obj = JSON.parseObject(segments.get(i).getContent());
//	   		list.add(obj);
//	   	}
//	   	return InvokeResult.success(list);
//	}
	
//	public Page<JSONObject> pageQuerySegment(SegmentDTO segmentDTO, int page, int pagesize) {
//		List<Object> conditionVals = new ArrayList<Object>();
//	   	StringBuilder jpql = new StringBuilder("select _segment.content from Segment _segment   where 1=1 ");
//	   	if(segmentDTO.getRecordId()!= null && !"".equals(segmentDTO.getRecordId())){
//	   		jpql.append(" and _segment.record.id = ?");
//	   		conditionVals.add(segmentDTO.getRecordId());
//	   	}
//	   	if(segmentDTO.getSegMark()!= null && !"".equals(segmentDTO.getSegMark())){
//	   		jpql.append(" and _segment.segMark = ?");
//	   		conditionVals.add(segmentDTO.getSegMark());
//	   	}
//	   	List<String> contents = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
//	   	List<JSONObject> list = new ArrayList<JSONObject>();
//	   	
//	   	for(int i = page * pagesize; i < page * pagesize + pagesize; i++){
//	   		if(i < contents.size()){
//	   			JSONObject obj = JSON.parseObject(contents.get(i));
//		   		list.add(obj);
//	   		}else{
//	   			break;
//	   		}	   		
//	   	}
//	   	return new Page<JSONObject>(page * pagesize, contents.size(), pagesize, list);
//	}
	
	public Page<JSONObject> pageQuerySegment(SegmentDTO segmentDTO, int page, int pagesize) {
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
	   	List<Segment> contents = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	List<JSONObject> list = new ArrayList<JSONObject>();
	   	
	   	for(int i = page * pagesize; i < page * pagesize + pagesize; i++){
	   		if(i < contents.size()){
	   			String content = contents.get(i).getContent();
	   			content = content.substring(0, content.length() - 1) + ",\"id\" : \"" + contents.get(i).getId() + "\"}";
	   			JSONObject obj = JSON.parseObject(content);
		   		list.add(obj);
	   		}else{
	   			break;
	   		}	   		
	   	}
	   	return new Page<JSONObject>(page * pagesize, contents.size(), pagesize, list);
	}
}
