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
import org.packet.packetsimulation.facade.impl.assembler.RecordAssembler;
import org.packet.packetsimulation.facade.RecordFacade;
import org.packet.packetsimulation.application.RecordApplication;
import org.packet.packetsimulation.application.RecordTypeApplication;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulationGeneration.core.domain.*;

@Named
public class RecordFacadeImpl implements RecordFacade {

	@Inject
	private RecordApplication  application;
	
	@Inject
	private RecordTypeApplication recordTypeApplication;

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
		application.creatRecord(record);
		return InvokeResult.success();
	}
	
	public InvokeResult updateRecord(RecordDTO recordDTO) {
		application.updateRecord(RecordAssembler.toEntity(recordDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeRecord(Long id) {
		application.removeRecord(application.getRecord(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeRecords(Long[] ids) {
		Set<Record> records= new HashSet<Record>();
		for (Long id : ids) {
			records.add(application.getRecord(id));
		}
		application.removeRecords(records);
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
	
	public Page<RecordDTO> pageQueryRecord(RecordDTO queryVo, int currentPage, int pageSize, String currentUserId) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _record from Record _record   where 1=1 ");
	   	if (currentUserId != null) {
	   		jpql.append(" and _record.createdBy = ?");
	   		conditionVals.add(currentUserId);
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
	
	
}
