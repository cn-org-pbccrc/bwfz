package org.packet.packetsimulation.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.text.NumberFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.impl.assembler.MesgAssembler;
import org.packet.packetsimulation.facade.impl.assembler.RecordAssembler;
import org.packet.packetsimulation.facade.RecordFacade;
import org.packet.packetsimulation.facade.SegmentFacade;
import org.packet.packetsimulation.application.RecordApplication;
import org.packet.packetsimulation.application.RecordTypeApplication;
import org.packet.packetsimulation.application.SegmentApplication;
import org.packet.packetsimulation.application.SubmissionApplication;
import org.packet.packetsimulation.application.ThreeStandardApplication;
import org.packet.packetsimulation.core.domain.BatchConfig;
import org.packet.packetsimulation.core.domain.BatchRule;
import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.Packet;
import org.packet.packetsimulation.core.domain.QuantityConfig;
import org.packet.packetsimulation.core.domain.QuantityRule;
import org.packet.packetsimulation.core.domain.Segment;
import org.packet.packetsimulation.core.domain.ThreeStandard;
import org.packet.packetsimulationGeneration.core.domain.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Named
public class RecordFacadeImpl implements RecordFacade {

	@Inject
	private RecordApplication  application;
	
	@Inject
	private RecordTypeApplication recordTypeApplication;
	
	@Inject
	private RecordApplication recordApplication;
	
	@Inject
	private SubmissionApplication submissionApplication;
	
	@Inject
	private SegmentApplication segmentApplication;
	
	@Inject
	private ThreeStandardApplication threeStandardApplication;
	
	@Inject
	private SegmentFacade segmentFacade;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getRecord(Long id) {
		return InvokeResult.success(RecordAssembler.toDTO(application.getRecord(id)));
	}
	
	public InvokeResult creatRecord(RecordDTO recordDTO, Long recordTypeId) {
		Record record = RecordAssembler.toEntity(recordDTO);
		record.setRecordType(recordTypeApplication.getRecordType(recordTypeId));
		record.setSubmission(submissionApplication.getSubmission(recordDTO.getSubmissionId()));
		if(recordDTO.getSubmissionId() != null){
			Submission submission = submissionApplication.getSubmission(recordDTO.getSubmissionId());
			submission.setRecordNum(submission.getRecordNum() + 1);
			submissionApplication.updateSubmission(submission);
			record.setSubmission(submission);
		}
		application.creatRecord(record);
		return InvokeResult.success();
	}
	
	public InvokeResult updateRecord(RecordDTO recordDTO, Long recordTypeId) {
		Record record = RecordAssembler.toEntity(recordDTO);
		record.setRecordType(recordTypeApplication.getRecordType(recordTypeId));
		record.setSubmission(submissionApplication.getSubmission(recordDTO.getSubmissionId()));
		application.updateRecord(record);
		return InvokeResult.success();
	}
	
	public InvokeResult removeRecord(Long id) {
		application.removeRecord(application.getRecord(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeRecords(Long[] ids) {
		Set<Record> records = new HashSet<Record>();
		for (Long id : ids) {
			records.add(application.getRecord(id));
			List<Long> segmentIds = findSegmentIdsByRecordId(id);
			if(segmentIds.size() != 0){
				segmentFacade.removeSegments(segmentIds.toArray(new Long[segmentIds.size()]));
			}
		}
		Submission submission = null;
		if(ids.length != 0){
			submission = submissionApplication.getSubmission(application.getRecord(ids[0]).getSubmission().getId());
			submission.setRecordNum(submission.getRecordNum() - new Long(records.size()));
		}
		application.removeRecords(records);
		submissionApplication.updateSubmission(submission);
		return InvokeResult.success();
	}
	
	public List<RecordDTO> findAllRecord() {
		return RecordAssembler.toDTOs(application.findAllRecord());
	}
	
	public List<RecordType> findRecordTypes(){
		StringBuilder jpql = new StringBuilder("select _recordType from RecordType _recordType   where 1=1 ");
	   	List<RecordType> recordTypes = getQueryChannelService().createJpqlQuery(jpql.toString()).list();
	   	return recordTypes;
	}
	
	public InvokeResult getRecordForBatch(Long id){
		RecordDTO dto = RecordAssembler.toDTO(application.getRecord(id));
		return InvokeResult.success(dto);
	}
	
	/**
	 * 根据选择的三标信息批量
	 */
	public InvokeResult batchRecord(RecordDTO recordDTO, String[] values ,String userAccount){
		Record recordById = recordApplication.getRecord(recordDTO.getId());
		List<Segment> oriSegments = findSegmentsByRecordId(recordDTO.getId());
		QuantityConfig qc = getQuantityConfig(recordById.getRecordType().getId(), recordById.getCreatedBy());
		List<QuantityRule> quantityRules = qc.getQuantityRules();
		Submission submission = recordById.getSubmission();
		submission.setRecordNum(submission.getRecordNum() + values.length);
		submissionApplication.updateSubmission(submission);
		for (int i = 0; i < values.length; i++){
			Record record = new Record();
			record.setCreatedBy(recordById.getCreatedBy());
			record.setRecordType(recordTypeApplication.getRecordType(recordById.getRecordType().getId()));
			record.setSubmission(submission);
			application.creatRecord(record);
			List<Segment> segments = new ArrayList<Segment>();
			for (int j = 0; j < oriSegments.size(); j++){
				Segment oriSegment = oriSegments.get(j);
				Segment segment = new Segment();				
				List<QuantityRule> rules = new ArrayList<QuantityRule>();
				for(int k = 0; k < quantityRules.size(); k++){
					if(quantityRules.get(k).getSegMark().equals(oriSegment.getSegMark())){
						rules.add(quantityRules.get(k));
					}
				}
				ThreeStandard threeStandard = threeStandardApplication.getThreeStandard(Long.parseLong(values[i]));
				String content = fillQuantityRule(oriSegment.getContent(), rules, i, threeStandard);
				segment.setContent(content);
				segment.setRecord(recordApplication.getRecord(record.getId()));
				segment.setSegMark(oriSegment.getSegMark());
				segments.add(segment);
			}
			segmentApplication.creatSegments(segments);
		}
		return InvokeResult.success();
	}
	
	/**
	 * 根据行号起始进行批量
	 */
	public InvokeResult batchRecord(RecordDTO recordDTO, int start, int end, String userAccount){
		Record recordById = recordApplication.getRecord(recordDTO.getId());
		List<Segment> oriSegments = findSegmentsByRecordId(recordDTO.getId());
		QuantityConfig qc = getQuantityConfig(recordById.getRecordType().getId(), recordById.getCreatedBy());
		List<QuantityRule> quantityRules = qc.getQuantityRules();
		List<ThreeStandard> list = queryThreeStandardByInput(start, end, userAccount);
		Submission submission = recordById.getSubmission();
		submission.setRecordNum(submission.getRecordNum() + end - start + 1);
		submissionApplication.updateSubmission(submission);
		for (int i = 0; i < list.size(); i++){
			Record record = new Record();
			record.setCreatedBy(recordById.getCreatedBy());
			record.setRecordType(recordTypeApplication.getRecordType(recordById.getRecordType().getId()));
			record.setSubmission(submission);
			application.creatRecord(record);
			List<Segment> segments = new ArrayList<Segment>();
			for (int j = 0; j < oriSegments.size(); j++){
				Segment oriSegment = oriSegments.get(j);
				Segment segment = new Segment();				
				List<QuantityRule> rules = new ArrayList<QuantityRule>();
				for(int k = 0; k < quantityRules.size(); k++){
					if(quantityRules.get(k).getSegMark().equals(oriSegment.getSegMark())){
						rules.add(quantityRules.get(k));
					}
				}
				ThreeStandard threeStandard = list.get(i);
				String content = fillQuantityRule(oriSegment.getContent(), rules, i, threeStandard);
				segment.setContent(content);
				segment.setRecord(recordApplication.getRecord(record.getId()));
				segment.setSegMark(oriSegment.getSegMark());
				segments.add(segment);
			}
			segmentApplication.creatSegments(segments);
		}
		return InvokeResult.success();
	}
	
	private String fillQuantityRule(String content, List<QuantityRule> rules, int rowNum, ThreeStandard threeStandard){
		JSONObject objContent = JSON.parseObject(content);
		for (QuantityRule rule : rules) {
			if(!rule.getInUse()){
				continue;
			}
			int ruleType = rule.getRuleType();
			String prop = rule.getRuleProperties();
			JSONObject obj = JSON.parseObject(prop);
			String itemId = rule.getItemId();
			int itemLength = rule.getItemLength();
			int itemType = rule.getItemType();
			switch (ruleType) {
			case 0://自增
				Long start = obj.getLong("startValue");//起始
				int size = obj.getIntValue("stepSize");//步长
				String total = Long.toString(start + size*rowNum);
				if(total.length() < itemLength){
					total = adjustLength(itemLength, itemType, total);					
				}
				objContent.put(itemId, total);
				break;
			case 1://数据集
				obj.getString("dickName");
				break;
			case 2://自定义
				JSONObject vProp = JSON.parseObject(obj.getString("custom"));
				String templete =  vProp.getString("templete");//变量模板
				JSONArray variables = vProp.getJSONArray("variables");//变量配置
				for (int i = 0; i < variables.size(); i++) {
					JSONObject variable = (JSONObject) variables.get(i);
					int vType = variable.getInteger("vType");//变量类型
					String vName = variable.getString("vName");//变量名称
					switch (vType) {
					case 0://自增
						Long initValue = variable.getLong("initValue");//初值
						Integer increment = variable.getInteger("increment");//增量
						Long vTotal = initValue + increment*rowNum;
						if(variable.getBoolean("isWidth")){//是否设置宽度
							Integer dataWidth = variable.getInteger("dataWidth");//数值宽度
							String sn=String.format("%0"+dataWidth+"d", vTotal);	    
							templete = templete.replace(vName, sn);
						}else{
							templete = templete.replace(vName, vTotal.toString());
						}
						break;
					case 1://随机
						String randomArea = variable.getString("randomArea");
						String[] values = randomArea.split(",");
						int n =(int) (Math.random()*values.length);
						templete = templete.replace(vName, values[n]);
						break;
					default:
						break;
					}
				}
				if(templete.length() < itemLength){
					templete = adjustLength(itemLength, itemType, templete);					
				}
				objContent.put(itemId, templete);
				break;
			case 3://三标信息字段
				String col = obj.getString("threeStandardColoum");
				 //反射get方法       
                Method meth;
				try {
					meth = threeStandard.getClass().getMethod(                      
					        "get" 
					+ col.substring(0, 1).toUpperCase()
					+ col.substring(1));
					//执行get方法获取字段值
					String colValue = meth.invoke(threeStandard).toString();
					if(colValue.length() < itemLength){
						colValue = adjustLength(itemLength, itemType, colValue);					
					}
					objContent.put(itemId, colValue);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}   			
			default:
				break;
			}
		}
		return JSON.toJSONString(objContent);
	}
	
	private String adjustLength(int itemLength, int itemType, String itemValue){
		if(itemType == 0){
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
	
	public Page<RecordDTO> pageQueryRecord(RecordDTO queryVo, int currentPage, int pageSize, Long submissionId) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _record from Record _record   where 1=1 ");
	   	if (submissionId != null) {
	   		jpql.append(" and _record.submission.id = ?");
	   		conditionVals.add(submissionId);
	   	}
	   	if (queryVo.getContent() != null && !"".equals(queryVo.getContent())) {
	   		jpql.append(" and _record.content like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getContent()));
	   	}		
	   	if (queryVo.getRecordName() != null && !"".equals(queryVo.getRecordName())) {
	   		jpql.append(" and _record.recordName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRecordName()));
	   	}			
        Page<Record> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<RecordDTO>(pages.getStart(), pages.getResultCount(),pageSize, RecordAssembler.toDTOs(pages.getData()));
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
	private List<Long> findSegmentIdsByRecordId(Long recordId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _segment.id from Segment _segment  where 1=1 ");
	   	
	   	if (recordId != null ) {
	   		jpql.append(" and _segment.record.id = ? ");
	   		conditionVals.add(recordId);
	   	}
	   	List<Long> segmentIds = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return segmentIds;
	}
	
	public Long queryCountOfThreeStandard(String currentUserId){
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select count(_threeStandard) from ThreeStandard _threeStandard where 1=1");
		if (currentUserId != null ) {
	   		jpql.append(" and _threeStandard.createdBy = ? ");
	   		conditionVals.add(currentUserId);
	   	}
		Long result = (Long) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public QuantityConfig getQuantityConfig(Long recordTypeId, String createBy) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _quantityConfig from QuantityConfig _quantityConfig where 1=1 ");
	   	jpql.append(" and _quantityConfig.recordType.id = ?");
	   	conditionVals.add(recordTypeId);
	   	jpql.append(" and _quantityConfig.createdBy = ? ");
	   	conditionVals.add(createBy);
		return (QuantityConfig) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	}
	
	public List<ThreeStandard> queryThreeStandardByInput(int startOfThreeStandard,int endOfThreeStandard,String currentUserId){
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _threeStandard from ThreeStandard _threeStandard  where 1=1");
		if (currentUserId != null ) {
	   		jpql.append(" and _threeStandard.createdBy = ? ");
	   		conditionVals.add(currentUserId);
	   	}
		List<ThreeStandard> result = getQueryChannelService().createJpqlQuery(jpql.toString()).setFirstResult(startOfThreeStandard-1).setPageSize(endOfThreeStandard-startOfThreeStandard+1).setParameters(conditionVals).list();
		return result;
	}
}
