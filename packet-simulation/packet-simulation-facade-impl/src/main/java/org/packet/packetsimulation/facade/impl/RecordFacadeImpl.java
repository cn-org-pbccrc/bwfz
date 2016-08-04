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

import org.packet.packetsimulationGeneration.core.domain.*;

@Named
public class RecordFacadeImpl implements RecordFacade {

	@Inject
	private RecordApplication  application;

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
	
	public InvokeResult creatRecord(RecordDTO recordDTO) {
		application.creatRecord(RecordAssembler.toEntity(recordDTO));
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
	
	public Page<RecordDTO> pageQueryRecord(RecordDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _record from Record _record   where 1=1 ");
	   	if (queryVo.getContent() != null && !"".equals(queryVo.getContent())) {
	   		jpql.append(" and _record.content like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getContent()));
	   	}		
	   	if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
	   		jpql.append(" and _record.remark like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
	   	}		
	   	if (queryVo.getCreateBy() != null && !"".equals(queryVo.getCreateBy())) {
	   		jpql.append(" and _record.createBy like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateBy()));
	   	}		
        Page<Record> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<RecordDTO>(pages.getStart(), pages.getResultCount(),pageSize, RecordAssembler.toDTOs(pages.getData()));
	}
	
	
}
