package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.QuantityConfigApplication;
import org.packet.packetsimulation.core.domain.QuantityConfig;

@Named
@Transactional
public class QuantityConfigApplicationImpl implements QuantityConfigApplication {

	public QuantityConfig getQuantityConfig(Long id) {
		return QuantityConfig.get(QuantityConfig.class, id);
	}
	
	public void creatQuantityConfig(QuantityConfig quantityConfig) {
		quantityConfig.save();
	}
	
	public void updateQuantityConfig(QuantityConfig quantityConfig) {
		quantityConfig .save();
	}
	
	public void removeQuantityConfig(QuantityConfig quantityConfig) {
		if(quantityConfig != null){
			quantityConfig.remove();
		}
	}
	
	public void removeQuantityConfigs(Set<QuantityConfig> quantityConfigs) {
		for (QuantityConfig quantityConfig : quantityConfigs) {
			quantityConfig.remove();
		}
	}
	
	public List<QuantityConfig> findAllQuantityConfig() {
		return QuantityConfig.findAll(QuantityConfig.class);
	}
	
}
