package org.packet.packetsimulation.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.io.File;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.impl.assembler.LotAssembler;
import org.packet.packetsimulation.facade.LotFacade;
import org.packet.packetsimulation.application.LotApplication;
import org.packet.packetsimulation.application.LotSubmissionApplication;
import org.packet.packetsimulation.core.domain.*;

@Named
public class LotFacadeImpl implements LotFacade {

	@Inject
	private LotApplication  application;
	
	@Inject
	private LotSubmissionApplication  lotSubmissionApplication;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getLot(Long id) {
		return InvokeResult.success(LotAssembler.toDTO(application.getLot(id)));
	}
	
	public InvokeResult creatLot(LotDTO lotDTO) {
		application.creatLot(LotAssembler.toEntity(lotDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateLot(LotDTO lotDTO) {
		application.updateLot(LotAssembler.toEntity(lotDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeLot(Long id) {
		application.removeLot(application.getLot(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeLots(Long[] ids, String savePath) {
		Set<Lot> lots= new HashSet<Lot>();
		for (Long id : ids) {
			lots.add(application.getLot(id));
			List<LotSubmission> lotSubmissionList = findLotSubmissionsByLotId(id);
			Set<LotSubmission> lotSubmissions = new HashSet<LotSubmission>();
			lotSubmissions.addAll(lotSubmissionList);
			deleteFile(new File(savePath + id));
			lotSubmissionApplication.removeLotSubmissions(lotSubmissions);
		}
		application.removeLots(lots);
		return InvokeResult.success();
	}
	
	public List<LotDTO> findAllLot() {
		return LotAssembler.toDTOs(application.findAllLot());
	}
	
	public Page<LotDTO> pageQueryLot(LotDTO queryVo, int currentPage, int pageSize, String lotCreator) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _lot from Lot _lot   where 1=1 ");
	   	if (lotCreator != null) {
	   		jpql.append(" and _lot.lotCreator = ?");
	   		conditionVals.add(lotCreator);
	   	}
	   	if (queryVo.getLotName() != null && !"".equals(queryVo.getLotName())) {
	   		jpql.append(" and _lot.lotName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLotName()));
	   	}		
	   	if (queryVo.getSendChannel() != null && !"".equals(queryVo.getSendChannel())) {
	   		jpql.append(" and _lot.sendChannel like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSendChannel()));
	   	}		
	   	if (queryVo.getSetTime() != null && !"".equals(queryVo.getSetTime())) {
	   		jpql.append(" and _lot.setTime like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSetTime()));
	   	}		
	   	if (queryVo.getLotCreator() != null && !"".equals(queryVo.getLotCreator())) {
	   		jpql.append(" and _lot.lotCreator like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLotCreator()));
	   	}		
	   	if (queryVo.getLotCreatedTime() != null) {
	   		jpql.append(" and _lot.lotCreatedTime between ? and ? ");
	   		conditionVals.add(queryVo.getLotCreatedTime());
	   		conditionVals.add(queryVo.getLotCreatedTimeEnd());
	   	}	
	   	if (queryVo.getLotStatus() != null && !"".equals(queryVo.getLotStatus())) {
	   		jpql.append(" and _lot.lotStatus like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLotStatus()));
	   	}		
	   	if (queryVo.getSubmissionNum() != null && !"".equals(queryVo.getSubmissionNum())) {
	   		jpql.append(" and _lot.submissionNum like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSubmissionNum()));
	   	}
	   	if (queryVo.getType() != null && !"".equals(queryVo.getType())) {
	   		jpql.append(" and _lot.type like ?");
	   		conditionVals.add(queryVo.getType());
	   	}
        Page<Lot> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<LotDTO>(pages.getStart(), pages.getResultCount(),pageSize, LotAssembler.toDTOs(pages.getData()));
	}
	
	private void deleteFile(File file) {  
		if (file.exists()) {//判断文件是否存在  
			if (file.isFile()) {//判断是否是文件  
				file.delete();//删除文件   
		    } else if (file.isDirectory()) {//否则如果它是一个目录  
		    	File[] files = file.listFiles();//声明目录下所有的文件 files[];  
		        for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件  
		        	this.deleteFile(files[i]);//把每个文件用这个方法进行迭代  
		        }  
		        file.delete();//删除文件夹  
		    }  
		} else {  
			System.out.println("所删除的文件不存在");  
		}  
	}
	
	@SuppressWarnings("unchecked")
	private List<LotSubmission> findLotSubmissionsByLotId(Long lotId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _lotSubmission from LotSubmission _lotSubmission  where 1=1 ");	   	
	   	if (lotId != null ) {
	   		jpql.append(" and _lotSubmission.lot.id = ? ");
	   		conditionVals.add(lotId);
	   	}
	   	List<LotSubmission> lotSubmissionList = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return lotSubmissionList;
	}
}
