package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface ConfigFacade {

	public InvokeResult getConfig(Long id);
	
	public InvokeResult creatConfig(ConfigDTO config);
	
	public InvokeResult updateConfig(ConfigDTO config);
	
	public InvokeResult removeConfig(Long id);
	
	public InvokeResult removeConfigs(Long[] ids);
	
	public List<ConfigDTO> findAllConfig();
	
	public Page<ConfigDTO> pageQueryConfig(ConfigDTO config, int currentPage, int pageSize);
	

}

