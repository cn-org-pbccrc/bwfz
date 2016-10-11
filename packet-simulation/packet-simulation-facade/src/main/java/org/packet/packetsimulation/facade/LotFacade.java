package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface LotFacade {

	public InvokeResult getLot(Long id);
	
	public InvokeResult creatLot(LotDTO lot);
	
	public InvokeResult updateLot(LotDTO lot);
	
	public InvokeResult removeLot(Long id);
	
	public InvokeResult removeLots(Long[] ids, String savePath);
	
	public List<LotDTO> findAllLot();
	
	public Page<LotDTO> pageQueryLot(LotDTO lot, int currentPage, int pageSize, String lotCreator);
	

}

