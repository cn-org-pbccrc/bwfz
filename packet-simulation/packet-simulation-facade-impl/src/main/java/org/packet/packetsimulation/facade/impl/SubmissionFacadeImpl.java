package org.packet.packetsimulation.facade.impl;

import java.util.HashSet;
import java.util.Iterator;
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
import org.packet.packetsimulation.facade.impl.assembler.SubmissionAssembler;
import org.packet.packetsimulation.facade.RecordFacade;
import org.packet.packetsimulation.facade.SubmissionFacade;
import org.packet.packetsimulation.application.RecordTypeApplication;
import org.packet.packetsimulation.application.SubmissionApplication;
import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulation.core.domain.Segment;
import org.packet.packetsimulationGeneration.core.domain.*;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Named
public class SubmissionFacadeImpl implements SubmissionFacade {

	@Inject
	private SubmissionApplication  application;
	
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
			if(recordIds != null){
				recordFacade.removeRecords(recordIds.toArray(new Long[recordIds.size()]));
			}
		}
		application.removeSubmissions(submissions);
		return InvokeResult.success();
	}
	
	
	public String exportSubmissions(Long[] ids){
		String exportSubmissions = "";
		for (Long id : ids){
			Submission submission = application.getSubmission(id);
			String head = "";
			String record = "";
			JSONObject objHead = JSON.parseObject(submission.getContent());		
			RecordType recordType = submission.getRecordType();
			List<RecordItem> recordItems1 = recordType.getHeaderItems();
			for(int i = 0; i < recordItems1.size(); i++){
				RecordItem recordItem = recordItems1.get(i);
				head += objHead.getString(recordItem.getItemId());
			}
			List<Long> recordIds = findRecordIdsBySubmissionId(id);
			for(int j = 0; j < recordIds.size(); j++){
				List<Segment> segments = findSegmentsByRecordId(recordIds.get(j));
				String body = "";
				for(int k = 0; k < segments.size(); k++){
					JSONObject objBody = JSON.parseObject(segments.get(k).getContent());
					RecordSegment recordSegment = findRecordSegmentBySegMark(segments.get(k).getSegMark());
					List<RecordItem> recordItems2 = recordSegment.getRecordItems();
					for(int i = 0; i < recordItems2.size(); i++){
						RecordItem recordItem = recordItems2.get(i);
						body += objBody.getString(recordItem.getItemId());
					}
				}
				record += body + "\n";
			}
			String tail = "Z" + submission.getRecordNum();
			exportSubmissions += head + "\r\n" + record + "\r\n" + tail + "\r\n" + "\r\n";
		}
		return exportSubmissions;
	}
	
	public List<SubmissionDTO> findAllSubmission() {
		return SubmissionAssembler.toDTOs(application.findAllSubmission());
	}
	
	public Page<SubmissionDTO> pageQuerySubmission(SubmissionDTO queryVo, int currentPage, int pageSize, String createdBy) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _submission from Submission _submission   where 1=1 ");
	   	if (queryVo.getCreatedBy() != null && !"".equals(queryVo.getCreatedBy())) {
	   		jpql.append(" and _submission.createdBy = ?");
	   		conditionVals.add(createdBy);
	   	}
	   	if (queryVo.getContent() != null && !"".equals(queryVo.getContent())) {
	   		jpql.append(" and _submission.content like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getContent()));
	   	}		
	   	if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
	   		jpql.append(" and _submission.Name like ?");
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
	private List<Segment> findSegmentsByRecordId(Long recordId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _segment from Segment _segment  where 1=1 ");
	   	
	   	if (recordId != null ) {
	   		jpql.append(" and _segment.record.id = ? ");
	   		conditionVals.add(recordId);
	   	}
	   	List<Segment> segmentList = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return segmentList;
	}
	
	@SuppressWarnings("unchecked")
	private RecordSegment findRecordSegmentBySegMark(String segMark){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _recordSegment from RecordSegment _recordSegment  where 1=1 ");
	   	
	   	if (segMark != null ) {
	   		jpql.append(" and _recordSegment.segMark = ? ");
	   		conditionVals.add(segMark);
	   	}
	   	RecordSegment recordSegment = (RecordSegment) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return recordSegment;
	}
}
