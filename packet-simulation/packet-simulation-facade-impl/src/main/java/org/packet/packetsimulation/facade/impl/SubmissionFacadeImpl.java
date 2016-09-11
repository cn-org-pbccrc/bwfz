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
import org.packet.packetsimulation.facade.impl.assembler.SubmissionAssembler;
import org.packet.packetsimulation.facade.SubmissionFacade;
import org.packet.packetsimulation.application.SubmissionApplication;

import org.packet.packetsimulationGeneration.core.domain.*;

@Named
public class SubmissionFacadeImpl implements SubmissionFacade {

	@Inject
	private SubmissionApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getSubmission(Long id) {
		return InvokeResult.success(SubmissionAssembler.toDTO(application.getSubmission(id)));
	}
	
	public InvokeResult creatSubmission(SubmissionDTO submissionDTO) {
		application.creatSubmission(SubmissionAssembler.toEntity(submissionDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateSubmission(SubmissionDTO submissionDTO) {
		application.updateSubmission(SubmissionAssembler.toEntity(submissionDTO));
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
		}
		application.removeSubmissions(submissions);
		return InvokeResult.success();
	}
	
	public List<SubmissionDTO> findAllSubmission() {
		return SubmissionAssembler.toDTOs(application.findAllSubmission());
	}
	
	public Page<SubmissionDTO> pageQuerySubmission(SubmissionDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _submission from Submission _submission   where 1=1 ");
	   	if (queryVo.getContent() != null && !"".equals(queryVo.getContent())) {
	   		jpql.append(" and _submission.content like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getContent()));
	   	}		
	   	if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
	   		jpql.append(" and _submission.Name like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getName()));
	   	}		
	   	if (queryVo.getCreatedBy() != null && !"".equals(queryVo.getCreatedBy())) {
	   		jpql.append(" and _submission.createdBy like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreatedBy()));
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
	
	
}
