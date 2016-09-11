package org.packet.packetsimulation.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;

public interface SubmissionFacade {

	public InvokeResult getSubmission(Long id);
	
	public InvokeResult creatSubmission(SubmissionDTO submission);
	
	public InvokeResult updateSubmission(SubmissionDTO submission);
	
	public InvokeResult removeSubmission(Long id);
	
	public InvokeResult removeSubmissions(Long[] ids);
	
	public List<SubmissionDTO> findAllSubmission();
	
	public Page<SubmissionDTO> pageQuerySubmission(SubmissionDTO submission, int currentPage, int pageSize, String createdBy);
	

}

