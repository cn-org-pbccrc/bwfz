package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.QuantityConfig;

public interface QuantityConfigApplication {

	public QuantityConfig getQuantityConfig(Long id);
	
	public void creatQuantityConfig(QuantityConfig quantityConfig);
	
	public void updateQuantityConfig(QuantityConfig quantityConfig);
	
	public void removeQuantityConfig(QuantityConfig quantityConfig);
	
	public void removeQuantityConfigs(Set<QuantityConfig> quantityConfigs);
	
	public List<QuantityConfig> findAllQuantityConfig();
	
}

