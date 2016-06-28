package org.packet.packetsimulation.facade.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.hibernate.exception.ConstraintViolationException;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.impl.assembler.DictItemAssembler;
import org.packet.packetsimulation.facade.impl.assembler.DictTypeAssembler;
import org.packet.packetsimulation.facade.DictItemFacade;
import org.packet.packetsimulation.application.DictItemApplication;
import org.packet.packetsimulation.core.domain.*;

@Named
public class DictItemFacadeImpl implements DictItemFacade {

	@Inject
	private DictItemApplication  application;

	private QueryChannelService queryChannel;
	
	Map loadDictItem;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getDictItem(Long id) {
		return InvokeResult.success(DictItemAssembler.toDTO(application.getDictItem(id)));
	}
	
	public InvokeResult creatDictItem(DictItemDTO dictItemDTO) {
		try {
			application.creatDictItem(DictItemAssembler.toEntity(dictItemDTO));
			return InvokeResult.success();
		} catch (Exception e) {
			return InvokeResult.failure("输入有误，或者无对应的字典类型");
		}
	}
	
	public InvokeResult updateDictItem(DictItemDTO dictItemDTO) {
		application.updateDictItem(DictItemAssembler.toEntity(dictItemDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeDictItem(Long id) {
		application.removeDictItem(application.getDictItem(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeDictItems(Long[] ids) {
		Set<DictItem> dictItems= new HashSet<DictItem>();
		for (Long id : ids) {
			dictItems.add(application.getDictItem(id));
		}
		application.removeDictItems(dictItems);
		return InvokeResult.success();
	}
	
	public List<DictItemDTO> findAllDictItem() {
		return DictItemAssembler.toDTOs(application.findAllDictItem());
	}
	
	public Page<DictItemDTO> pageQueryDictItem(DictItemDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _dictItem from DictItem _dictItem   where _dictItem.delFlag= '0' ");
	   	if (queryVo.getDictId() != null) {
	   		jpql.append(" and _dictItem.dictId=?");
	   		conditionVals.add(queryVo.getDictId());
	   	}	
	   	if (queryVo.getDictItemCode() != null && !"".equals(queryVo.getDictItemCode())) {
	   		jpql.append(" and _dictItem.dictItemCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDictItemCode()));
	   	}		
	   	if (queryVo.getDictItemName() != null && !"".equals(queryVo.getDictItemName())) {
	   		jpql.append(" and _dictItem.dictItemName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDictItemName()));
	   	}		
//	   	if (queryVo.getItemSort() != null) {
//	   		jpql.append(" and _dictItem.itemSort=?");
//	   		conditionVals.add(queryVo.getItemSort());
//	   	}	
	   	// del_flag 为0时表示未删除
	   //	if (queryVo.getDelFlag() != null && !"".equals(queryVo.getDelFlag())) {
//	   		jpql.append(" and _dictItem.delFlag =? ");
//	   		conditionVals.add(MessageFormat.format("%{0}%", 0));
	  // 	}		
	   	if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
	   		jpql.append(" and _dictItem.remark like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
	   	}		
        Page<DictItem> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<DictItemDTO>(pages.getStart(), pages.getResultCount(),pageSize, DictItemAssembler.toDTOs(pages.getData()));
	}

	@Override
	public Map loadDictItem() {
		// TODO Auto-generated method stub
		/**
		 * 返回数据字典
		 */
		return loadDictItem(0);
	}
	
	public Map loadDictItem(int reload) {
		// TODO Auto-generated method stub
		// reload为0，强制重新载入
		if(reload==0 || loadDictItem==null){
			loadDictItem = new HashMap();
			List<DictItemDTO> dtos = DictItemAssembler.toDTOs(application.findAllDictItem());
			for (DictItemDTO dto:dtos){
				loadDictItem.put(dto.getId(), dto);
			}
		}
		return loadDictItem;
	}
}
