package com.openkoala.packagesimulation.infra.Config;

import org.packet.packetsimulation.core.domain.Config;

public interface IConnectionUtils {
	public void sendByConfig(Config config, String content);
	public void receiveByConfig(Config config, String content);
}
