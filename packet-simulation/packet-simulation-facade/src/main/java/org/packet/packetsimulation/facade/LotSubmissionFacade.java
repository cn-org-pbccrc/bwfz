package org.packet.packetsimulation.facade;

import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.springframework.web.servlet.ModelAndView;

public interface LotSubmissionFacade {

	public InvokeResult getLotSubmission(Long id);
	
	public InvokeResult creatLotSubmissions(LotSubmissionDTO lotSubmission, String ctxPath, String[] flags, String[] coms, String[] encs);
	
	public InvokeResult updateLotSubmission(LotSubmissionDTO lotSubmission);
	
	public InvokeResult removeLotSubmission(Long id);
	
	public InvokeResult removeLotSubmissions(Long[] ids, String savePath);
	
	public String showSubmissionContent(Long id, String ctxPath);
	
	public List<LotSubmissionDTO> findAllLotSubmission();
	
	public Page<LotSubmissionDTO> pageQueryLotSubmission(LotSubmissionDTO lotSubmission, int currentPage, int pageSize, Long lotId);
	
	public ModelAndView uploadLotSubmission(LotSubmissionDTO lotSubmissionDTO, String ctxPath);
	
	public InvokeResult upLotSubmission(String sourceId, String destId);
	
	public InvokeResult downLotSubmission(String sourceId, String destId);
}

