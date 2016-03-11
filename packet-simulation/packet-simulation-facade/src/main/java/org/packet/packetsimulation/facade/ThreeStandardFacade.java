package org.packet.packetsimulation.facade;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface ThreeStandardFacade {

	public InvokeResult getThreeStandard(Long id);
	
	public InvokeResult creatThreeStandard(ThreeStandardDTO threeStandard);
	
	public InvokeResult generateThreeStandard(String createdBy, int threeStandardNumber, int threadNumber);
	
	public InvokeResult importThreeStandard(ThreeStandardDTO threeStandardDTO, String path, String ctxPath) throws FileNotFoundException, IOException, ParseException;
	
	public InvokeResult updateThreeStandard(ThreeStandardDTO threeStandard);
	
	public InvokeResult removeThreeStandard(Long id);
	
	public InvokeResult removeThreeStandards(Long[] ids);
	
	public List<ThreeStandardDTO> findAllThreeStandard();
	
	public Page<ThreeStandardDTO> pageQueryThreeStandard(ThreeStandardDTO threeStandard, int currentPage, int pageSize, String currentUserId);

	public String downloadCSV(String createdBy);
	
}

