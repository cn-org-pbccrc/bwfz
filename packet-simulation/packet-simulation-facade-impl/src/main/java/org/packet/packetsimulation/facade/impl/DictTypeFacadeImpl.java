package org.packet.packetsimulation.facade.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.application.DictTypeApplication;
import org.packet.packetsimulation.core.domain.DictType;
import org.packet.packetsimulation.facade.DictTypeFacade;
import org.packet.packetsimulation.facade.dto.DictItemDTO;
import org.packet.packetsimulation.facade.dto.DictTypeDTO;
import org.packet.packetsimulation.facade.impl.assembler.DictTypeAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class DictTypeFacadeImpl implements DictTypeFacade {

	static Logger logger = LoggerFactory.getLogger(DictTypeFacadeImpl.class.getName());
	@Inject
	private DictTypeApplication  application;

	private QueryChannelService queryChannel;

	// 数据项类型map，通过类型id进行查询
	DictTypeDTO dictTypeTree;
	
    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getDictType(Long id) {
		return InvokeResult.success(DictTypeAssembler.toDTO(application.getDictType(id)));
	}
	
	public InvokeResult creatDictType(DictTypeDTO dictTypeDTO) {
		application.creatDictType(DictTypeAssembler.toEntity(dictTypeDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateDictType(DictTypeDTO dictTypeDTO) {
		application.updateDictType(DictTypeAssembler.toEntity(dictTypeDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeDictType(Long id) {
		application.removeDictType(application.getDictType(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeDictTypes(Long[] ids) {
		Set<DictType> dictTypes= new HashSet<DictType>();
		for (Long id : ids) {
			dictTypes.add(application.getDictType(id));
		}
		application.removeDictTypes(dictTypes);
		return InvokeResult.success();
	}
	
	public List<DictTypeDTO> findAllDictType() {
		return DictTypeAssembler.toDTOs(application.findAllDictType());
	}
	
	// 删除该数据类型及其所有子类型
	public InvokeResult deleteAllByParent(long id){
		DictTypeDTO queryVo = new DictTypeDTO();
		queryVo.setId(id);
		deleteAllDictTypeByParent(queryVo);
		return InvokeResult.success();
	}
	
	public void deleteAllDictTypeByParent(DictTypeDTO queryVo) {
		Set<DictType> deleteSet = new HashSet<DictType>();
		// 先将需要删除的最顶层id放在deleteSet中，再依次找这个节点的子节点，放入deleteSet中
		DictType entity = application.getDictType(queryVo.getId());
		// DictTypeAssembler.toEntity(queryVo)
		if(entity!=null){
			deleteSet.add(entity);
		}
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _dictType from DictType _dictType   where 1=1 ");
		if (queryVo.getId() != null) {
			jpql.append(" and _dictType.upDictId=?");
			conditionVals.add(queryVo.getId());
		}
		List<DictType> types = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.list();

		for (DictType type : types) {
//			DictTypeDTO childDict = new DictTypeDTO();
//			childDict.setId(type.getId());
//			deleteAllDictTypeByParent(childDict);
			deleteSet.add(type);
		}
		if (deleteSet.size() > 0) {
			application.removeDictTypes(deleteSet);
		}
	}
	
	public Page<DictTypeDTO> pageQueryDictType(DictTypeDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _dictType from DictType _dictType   where 1=1 ");
	   	if (queryVo.getUpDictId() != null) {
	   		jpql.append(" and _dictType.upDictId=?");
	   		conditionVals.add(queryVo.getUpDictId());
	   	}	
	   	if (queryVo.getDictName() != null && !"".equals(queryVo.getDictName())) {
	   		jpql.append(" and _dictType.dictName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDictName()));
	   	}		
	   	if (queryVo.getDictClassify() != null) {
	   		jpql.append(" and _dictType.dictClassify=?");
	   		conditionVals.add(queryVo.getDictClassify());
	   	}	
	   	if (queryVo.getCreateUserId() != null) {
	   		jpql.append(" and _dictType.createUserId=?");
	   		conditionVals.add(queryVo.getCreateUserId());
	   	}	
//	   	if (queryVo.getCreateTime() != null) {
//	   		jpql.append(" and _dictType.createTime between ? and ? ");
//	   		conditionVals.add(queryVo.getCreateTime());
//	   		conditionVals.add(queryVo.getCreateTimeEnd());
//	   	}	
	   	if (queryVo.getMendUserId() != null) {
	   		jpql.append(" and _dictType.mendUserId=?");
	   		conditionVals.add(queryVo.getMendUserId());
	   	}	
//	   	if (queryVo.getMendTime() != null) {
//	   		jpql.append(" and _dictType.mendTime between ? and ? ");
//	   		conditionVals.add(queryVo.getMendTime());
//	   		conditionVals.add(queryVo.getMendTimeEnd());
//	   	}	
	   	if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
	   		jpql.append(" and _dictType.remark like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
	   	}		
        Page<DictType> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<DictTypeDTO>(pages.getStart(), pages.getResultCount(),pageSize, DictTypeAssembler.toDTOs(pages.getData()));
	}

	@Override
	public Page<DictItemDTO> findDictItemSetByDictType(Long id,
			int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DictTypeDTO loadDictType() {
		// TODO Auto-generated method stub
		/**
		 * 返回数据字典
		 */
		return loadDictType(0);
	}
	
	public DictTypeDTO loadDictType(int reload) {
		// TODO Auto-generated method stub
		// reload为0，强制重新载入
		logger.info("导入数据字典  --start  ---");
//		if(reload==0 || dictTypeMap==null){
//			dictTypeMap = new HashMap();
//			List<DictTypeDTO> dtos = DictTypeAssembler.toDTOs(application.findAllDictType());
//			for (DictTypeDTO dto:dtos){
//				dictTypeMap.put(dto.getId(), dto);
//			}
//		}
		
		if(reload==0 || dictTypeTree==null){
			List<DictTypeDTO> allDictTypes = DictTypeAssembler
					.toDTOs(application.findAllDictType());

			dictTypeTree = new DictTypeDTO();
			dictTypeTree.setId(-1l);
			dictTypeTree.setDictName("root");
			dictTypeTree.setChildTypesSet(new HashSet<DictTypeDTO>());
			for (DictTypeDTO dictType : allDictTypes) {
				// DictType
				addNode(dictTypeTree, dictType, true);

			}
		}
		
		logger.info("导入数据字典  -- end ---");
		
		for(DictTypeDTO currentNode : dictTypeTree.getChildTypesSet()){
			System.out.println("id is : " + currentNode.getId() + "upper id is : "+currentNode.getUpDictId());
		}
		
		return dictTypeTree;
	}
	
	// 将DTO插入树中对应的位置
	// 返回是否找到对应的父节点，用到递归
	private boolean addNode(DictTypeDTO tree, DictTypeDTO node, boolean isTree){
		boolean hasParent = false;
		if(node.getChildTypesSet()==null){
			node.setChildTypesSet(new HashSet<DictTypeDTO>());
		}
		for (DictTypeDTO currentNode : tree.getChildTypesSet()){
			
			if (currentNode.getChildTypesSet()==null){
				currentNode.setChildTypesSet(new HashSet<DictTypeDTO>());
			}
//			//遍历所有子节点，找其父或子,注意可能有多个子
//			// 欲插入的节点一旦在一颗树上找到父或子，这颗树上就不可能再有它的父或子
			if( node.getUpDictId().equals(currentNode.getId())){
				currentNode.getChildTypesSet().add(node);
				hasParent = true;
				//node.setParentType(currentNode);
				//return true ;
			}else if(isTree && currentNode.getUpDictId().equals(node.getId()) ){
				// 只有一层节点才有可能是子节点，所有判断isTree
				//node.setParentType(currentNode.getParentType());
				//currentNode.setParentType(node);
				node.getChildTypesSet().add(currentNode);
				tree.getChildTypesSet().remove(currentNode);
				//return true;
			}
			// 如果该节点是树顶节点的父或子，添加节点后直接返回
			// 如果不是，则对该节点的子节点遍历查找
			hasParent = hasParent||addNode(currentNode, node, false);
		}
		// 如果树中的所有节点都不是子或父，直接把这个节点放在根节点下
		if (isTree && !hasParent){
			//node.setParentType(tree);
			tree.getChildTypesSet().add(node);
		}
		return hasParent;
	}
}
