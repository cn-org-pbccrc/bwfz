package org.packet.packetsimulation.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.io.IOException;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.gqc.core.domain.GeneralQuery;
import org.openkoala.gqc.facade.impl.assembler.DynamicQueryConditionAssembler;
import org.openkoala.gqc.facade.impl.assembler.FieldDetailAssembler;
import org.openkoala.gqc.facade.impl.assembler.PreQueryConditionAssembler;
import org.openkoala.gqc.infra.util.XmlNode;
import org.openkoala.gqc.infra.util.XmlUtil;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.impl.assembler.BatchConfigAssembler;
import org.packet.packetsimulation.facade.impl.assembler.BatchRuleAssembler;
import org.packet.packetsimulation.facade.impl.assembler.MesgTypeAssembler;
import org.packet.packetsimulation.facade.BatchConfigFacade;
import org.packet.packetsimulation.application.BatchConfigApplication;
import org.packet.packetsimulation.application.MesgTypeApplication;
import org.packet.packetsimulation.core.domain.*;
import org.xml.sax.SAXException;

@Named
public class BatchConfigFacadeImpl implements BatchConfigFacade {

	@Inject
	private BatchConfigApplication  application;
	
	@Inject
	private MesgTypeApplication  mesgTypeApplication;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getBatchConfig(Long id) {
		return InvokeResult.success(BatchConfigAssembler.toDTO(application.getBatchConfig(id)));
	}
	
	public InvokeResult creatBatchConfig(BatchConfigDTO batchConfigDTO) {
		batchConfigDTO.setMesgTypeDTO(MesgTypeAssembler.toDTO(mesgTypeApplication.getMesgType(batchConfigDTO.getMesgTypeDTO().getId())));
		application.creatBatchConfig(BatchConfigAssembler.toEntity(batchConfigDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateBatchConfig(BatchConfigDTO batchConfigDTO) {
		try {
			BatchConfig batchConfig = application.getBatchConfig(batchConfigDTO.getId());
			batchConfig.setBatchRules(BatchRuleAssembler.toEntityList(batchConfigDTO.getBatchRules()));
			application.updateBatchConfig(batchConfig);
			return InvokeResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return InvokeResult.failure("修改批量规则失败");
		}
	}
	
	public InvokeResult removeBatchConfig(Long id) {
		application.removeBatchConfig(application.getBatchConfig(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeBatchConfigs(Long[] ids) {
		Set<BatchConfig> batchConfigs= new HashSet<BatchConfig>();
		for (Long id : ids) {
			batchConfigs.add(application.getBatchConfig(id));
		}
		application.removeBatchConfigs(batchConfigs);
		return InvokeResult.success();
	}
	
	public List<BatchConfigDTO> findAllBatchConfig() {
		return BatchConfigAssembler.toDTOs(application.findAllBatchConfig());
	}
	
	public Page<BatchConfigDTO> pageQueryBatchConfig(BatchConfigDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _batchConfig from BatchConfig _batchConfig   where 1=1 ");
	   	if (queryVo.getCreatedBy() != null && !"".equals(queryVo.getCreatedBy())) {
	   		jpql.append(" and _batchConfig.createdBy like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreatedBy()));
	   	}		
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _batchConfig.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
        Page<BatchConfig> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<BatchConfigDTO>(pages.getStart(), pages.getResultCount(),pageSize, BatchConfigAssembler.toDTOs(pages.getData()));
	}

	@Override
	public InvokeResult getNodes(Long id) throws SAXException, IOException, ParserConfigurationException {
		MesgType mesgType = mesgTypeApplication.getMesgType(id);
		XmlNode node = XmlUtil.getXmlNodeByXmlContent1(mesgType.getXml(), mesgType.getCountTag(), mesgType.getMesgType());
		return InvokeResult.success(node);
	}

	@Override
	public List<BatchConfigDTO> queryBatchConfig(BatchConfigDTO queryVo) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _batchConfig from BatchConfig _batchConfig   where 1=1 ");
	   	if (queryVo.getCreatedBy() != null && !"".equals(queryVo.getCreatedBy())) {
	   		jpql.append(" and _batchConfig.createdBy = ?");
	   		conditionVals.add(queryVo.getCreatedBy());
	   	}		
	   	if (queryVo.getMesgTypeDTO().getId() != null) {
	   		jpql.append(" and _batchConfig.mesgType.id = ? ");
	   		conditionVals.add(queryVo.getMesgTypeDTO().getId());
	   	}	
		return BatchConfigAssembler.toDTOs(getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list());
	}
	
	
}
