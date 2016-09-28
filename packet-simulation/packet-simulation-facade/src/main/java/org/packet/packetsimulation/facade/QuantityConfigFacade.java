package org.packet.packetsimulation.facade;

import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface QuantityConfigFacade {

	public InvokeResult getQuantityConfig(Long id);
	
	public InvokeResult creatQuantityConfig(QuantityConfigDTO quantityConfig);
	
	public InvokeResult updateQuantityConfig(QuantityConfigDTO quantityConfig);
	
	public InvokeResult removeQuantityConfig(Long id);
	
	public InvokeResult removeQuantityConfigs(Long[] ids);
	
	public List<QuantityConfigDTO> findAllQuantityConfig();
	
	public Page<QuantityConfigDTO> pageQueryQuantityConfig(QuantityConfigDTO quantityConfig, int currentPage, int pageSize);
	
	public List<QuantityConfigDTO> queryQuantityConfig(QuantityConfigDTO quantityConfigDTO);
	
	public InvokeResult getRecordSegments(Long id);
}

