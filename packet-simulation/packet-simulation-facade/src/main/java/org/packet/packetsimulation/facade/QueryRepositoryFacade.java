package org.packet.packetsimulation.facade;

import org.openkoala.koala.commons.InvokeResult;

public interface QueryRepositoryFacade {
	
	public InvokeResult getInterfaceList();
	
	public InvokeResult getMethodList(String interfaceId);
	
}
