package org.packet.packetsimulation.facade.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.packet.packetsimulation.facade.impl.assembler.SubmissionAssembler;
import org.packet.packetsimulation.facade.RecordFacade;
import org.packet.packetsimulation.facade.SubmissionFacade;
import org.packet.packetsimulation.application.LotApplication;
import org.packet.packetsimulation.application.RecordTypeApplication;
import org.packet.packetsimulation.application.SubmissionApplication;
import org.packet.packetsimulation.core.domain.Lot;
import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulation.core.domain.Segment;
import org.packet.packetsimulationGeneration.core.domain.*;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

@Named
public class SubmissionFacadeImpl implements SubmissionFacade {

	@Inject
	private SubmissionApplication  application;
	
	@Inject
	private LotApplication  lotApplication;
	
	@Inject
	private RecordTypeApplication recordTypeApplication;
	
	@Inject
	private RecordFacade recordFacade;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getSubmission(Long id) {
		Submission submission = application.getSubmission(id);
		String content = submission.getContent();
		JSONObject obj = JSON.parseObject(content);
		RecordType recordType = submission.getRecordType();
		List<RecordItem> recordItems = recordType.getHeaderItems();
		for(int i = 0; i < recordItems.size(); i++){
			RecordItem recordItem = recordItems.get(i);
			recordItem.setItemValue(obj.getString(recordItem.getItemId()));
		}
		return InvokeResult.success(SubmissionAssembler.toDTO(submission));
	}
	
	public InvokeResult creatSubmission(SubmissionDTO submissionDTO, Long recordTypeId) {
		Submission submission = SubmissionAssembler.toEntity(submissionDTO);
		RecordType recordType = recordTypeApplication.getRecordType(recordTypeId);
		submission.setRecordType(recordType);
		application.creatSubmission(submission);
		return InvokeResult.success();
	}
	
	public InvokeResult updateSubmission(SubmissionDTO submissionDTO, Long recordTypeId) {
		Submission submission = SubmissionAssembler.toEntity(submissionDTO);
		RecordType recordType = recordTypeApplication.getRecordType(recordTypeId);
		submission.setRecordType(recordType);
		application.updateSubmission(submission);
		return InvokeResult.success();
	}
	
	public InvokeResult removeSubmission(Long id) {
		application.removeSubmission(application.getSubmission(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeSubmissions(Long[] ids) {
		Set<Submission> submissions= new HashSet<Submission>();
		for (Long id : ids) {
			submissions.add(application.getSubmission(id));
			List<Long> recordIds = findRecordIdsBySubmissionId(id);
			if(recordIds.size() != 0){
				recordFacade.removeRecords(recordIds.toArray(new Long[recordIds.size()]));
			}
		}
		application.removeSubmissions(submissions);
		return InvokeResult.success();
	}
	
	public String exportSubmission(Long id, Integer type){
		String exportSubmission = "";
		String head = "";
		String body = "";			
		Submission submission = application.getSubmission(id);
		RecordType recordType = submission.getRecordType();
		List<RecordItem> headerItems = recordType.getHeaderItems();
		JSONObject jsonHead = JSON.parseObject(submission.getContent());
		for(RecordItem recordItem : headerItems){
			head += adjustLength(recordItem.getItemLength(), Integer.parseInt(recordItem.getItemType()), jsonHead.getString(recordItem.getItemId()));
		}
		List<Record> records = findRecordsBySubmissionId(id);
		for(int i = 0; i < records.size(); i++){
			Record record = records.get(i);
			String recordJson = record.getContent();
			JSONObject recordObj = JSON.parseObject(recordJson);
			List<RecordSegment> recordSegments = findRecordSegmentsByRecordType(record.getRecordType().getId());
			String recordContent = "";
			for(int j = 0; j < recordSegments.size(); j++){
				List<RecordItem> recordItems = recordSegments.get(j).getRecordItems();
				String segmentJson= recordObj.getString(recordSegments.get(j).getSegMark());
			   	JSONArray segmentArray = null;
			   	if(segmentJson != null){
			   		segmentArray = JSON.parseArray(segmentJson);
			   		for(int k = 0; k < segmentArray.size(); k++){
			   			JSONObject segmentObj = (JSONObject) segmentArray.get(k);
			   			for(RecordItem recordItem : recordItems){
			   				recordContent += adjustLength(recordItem.getItemLength(), Integer.parseInt(recordItem.getItemType()), segmentObj.getString(recordItem.getItemId()));
						}
			   		}
			   	}
			}
			body += recordContent + "\n";
		}
		if(type == 2){
			exportSubmission += head + "\n" + "\n" + body;
		}else{
			exportSubmission += head + "\n" + body;
			if(type == 1){
				String tail = "Z" + String.format("%010d", submission.getRecordNum());
				exportSubmission += tail;
			}
		}
		return exportSubmission.trim();
	}
	
/*	public String exportSubmissions(Long[] ids){
		String exportSubmissions = "";
		for (Long id : ids){
			String head = "";
			String body = "";			
			Submission submission = application.getSubmission(id);
			RecordType recordType = submission.getRecordType();
			List<RecordItem> headerItems = recordType.getHeaderItems();
			JSONObject jsonHead = JSON.parseObject(submission.getContent());
			for(RecordItem recordItem : headerItems){
				head += adjustLength(recordItem.getItemLength(), Integer.parseInt(recordItem.getItemType()), jsonHead.getString(recordItem.getItemId()));
			}
	        //System.out.println("head: " + head.length());
			List<Long> recordIds = findRecordIdsBySubmissionId(id);
			for(int i = 0; i < recordIds.size(); i++){
				List<Segment> segments = findSegmentsByRecordId(recordIds.get(i));
				if(segments != null){
					String record = "";
					for(int j = 0; j < segments.size(); j++){
						RecordSegment recordSegment = findRecordSegmentByRecordType(segments.get(j).getRecord().getRecordType().getId(), segments.get(j).getSegMark());
						List<RecordItem> recordItems = recordSegment.getRecordItems();
						JSONObject jsonRecord = JSON.parseObject(segments.get(j).getContent());
						for(RecordItem recordItem : recordItems){
							record += adjustLength(recordItem.getItemLength(), Integer.parseInt(recordItem.getItemType()), jsonRecord.getString(recordItem.getItemId()));
						}
//						LinkedHashMap<String, String> jsonRecordMap = JSON.parseObject(segments.get(k).getContent(), new TypeReference<LinkedHashMap<String, String>>() {});
//				        for (Map.Entry<String, String> entry : jsonRecordMap.entrySet()) {
//				        	record += entry.getValue();
//				        }
					}
					body += record + "\n";
					//System.out.println("record: " + record.length());
				}
			}
			String tail = "Z" + String.format("%10d", submission.getRecordNum());
			//System.out.println("tail: " + tail.length());
			exportSubmissions += head + "\n" + body + tail + "\n" + "\n" + "\n";
		}
		return exportSubmissions.trim();
	}*/
	
	private String adjustLength(int itemLength, int itemType, String itemValue){
		if(itemType == 0 && !itemValue.equals("")){
			//itemValue = String.format("%0" + itemLength + "d", Integer.parseInt(itemValue));
			while (itemValue.length() < itemLength) {  
				StringBuffer sb = new StringBuffer();  
				sb.append("0").append(itemValue);//左补0  				
				itemValue = sb.toString();
			}
		}else{
			itemValue = String.format("%-" + itemLength + "s", itemValue);
		}
		return itemValue;
	}
	
	public List<SubmissionDTO> findAllSubmission() {
		return SubmissionAssembler.toDTOs(application.findAllSubmission());
	}
	
	public Page<SubmissionDTO> pageQuerySubmission(SubmissionDTO queryVo, int currentPage, int pageSize, String createdBy) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _submission from Submission _submission   where 1=1 ");
	   	if (createdBy != null) {
	   		jpql.append(" and _submission.createdBy = ?");
	   		conditionVals.add(createdBy);
	   	}
	   	if (queryVo.getContent() != null && !"".equals(queryVo.getContent())) {
	   		jpql.append(" and _submission.content like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getContent()));
	   	}		
	   	if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
	   		jpql.append(" and _submission.name like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getName()));
	   	}				
	   	if (queryVo.getRecordNum() != null && !"".equals(queryVo.getRecordNum())) {
	   		jpql.append(" and _submission.recordNum like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRecordNum()));
	   	}		
        Page<Submission> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();		   
        return new Page<SubmissionDTO>(pages.getStart(), pages.getResultCount(),pageSize, SubmissionAssembler.toDTOs(pages.getData()));
	}
	
	public Page<SubmissionDTO> pageJsonByType(SubmissionDTO queryVo, int currentPage, int pageSize, String createdBy, Long lotId){
		Lot lot = lotApplication.getLot(lotId);
		Integer type = lot.getType();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _submission from Submission _submission   where 1=1 ");
	   	if (createdBy != null) {
	   		jpql.append(" and _submission.createdBy = ?");
	   		conditionVals.add(createdBy);
	   	}
	   	if (type != null) {
	   		jpql.append(" and _submission.recordType.type = ?");
	   		conditionVals.add(type);
	   	}
	   	if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
	   		jpql.append(" and _submission.name like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getName()));
	   	}
	    Page<Submission> pages = getQueryChannelService()
	    	.createJpqlQuery(jpql.toString())
	 		.setParameters(conditionVals)
	 		.setPage(currentPage, pageSize)
	 		.pagedList();		   
	    return new Page<SubmissionDTO>(pages.getStart(), pages.getResultCount(),pageSize, SubmissionAssembler.toDTOs(pages.getData()));
	}
	
	@SuppressWarnings("unchecked")
	private List<Long> findRecordIdsBySubmissionId(Long submissionId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _record.id from Record _record  where 1=1 ");	   	
	   	if (submissionId != null ) {
	   		jpql.append(" and _record.submission.id = ? ");
	   		conditionVals.add(submissionId);
	   	}
	   	List<Long> recordIds = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return recordIds;
	}
	
	@SuppressWarnings("unchecked")
	private List<Record> findRecordsBySubmissionId(Long submissionId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _record from Record _record  where 1=1 ");	   	
	   	if (submissionId != null ) {
	   		jpql.append(" and _record.submission.id = ? ");
	   		conditionVals.add(submissionId);
	   	}
	   	List<Record> records = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return records;
	}
	
/*	private RecordSegment findRecordSegmentByRecordType(Long recordTypeId, String segMark){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _recordSegment from RecordSegment _recordSegment  where 1=1 ");
	   	
	   	if (recordTypeId != null ) {
	   		jpql.append(" and _recordSegment.recordType.id = ? ");
	   		conditionVals.add(recordTypeId);
	   	}
	   	if (segMark != null ) {
	   		jpql.append(" and _recordSegment.segMark = ? ");
	   		conditionVals.add(segMark);
	   	}
	   	RecordSegment recordSegment = (RecordSegment) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return recordSegment;
	}*/
	
	private List<RecordSegment> findRecordSegmentsByRecordType(Long recordTypeId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _recordSegment from RecordSegment _recordSegment  where 1=1 ");   	
	   	if (recordTypeId != null ) {
	   		jpql.append(" and _recordSegment.recordType.id = ? ");
	   		conditionVals.add(recordTypeId);
	   	}
	   	List<RecordSegment> recordSegments = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return recordSegments;
	}
}
