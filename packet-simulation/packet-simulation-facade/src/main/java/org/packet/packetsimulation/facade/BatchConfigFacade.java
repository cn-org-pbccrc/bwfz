package org.packet.packetsimulation.facade;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.xml.sax.SAXException;

public interface BatchConfigFacade {

	public InvokeResult getBatchConfig(Long id);
	
	public InvokeResult getNodes(Long id) throws SAXException, IOException, ParserConfigurationException;
	
	public InvokeResult creatBatchConfig(BatchConfigDTO batchConfig);
	
	public InvokeResult updateBatchConfig(BatchConfigDTO batchConfig);
	
	public InvokeResult removeBatchConfig(Long id);
	
	public InvokeResult removeBatchConfigs(Long[] ids);
	
	public List<BatchConfigDTO> findAllBatchConfig();
	
	public Page<BatchConfigDTO> pageQueryBatchConfig(BatchConfigDTO batchConfig, int currentPage, int pageSize);
	
	public List<BatchConfigDTO> queryBatchConfig(BatchConfigDTO batchConfig);
	
}

