package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulationGeneration.core.domain.*;

public class SubmissionAssembler {
	
	public static SubmissionDTO  toDTO(Submission  submission){
		if (submission == null) {
			return null;
		}
    	SubmissionDTO result  = new SubmissionDTO();
	    	result.setId (submission.getId());
     	    	result.setVersion (submission.getVersion());
     	    	result.setContent (submission.getContent());
     	    	result.setName (submission.getName());
     	    	result.setCreatedBy (submission.getCreatedBy());
     	    	result.setRecordNum (submission.getRecordNum());
     	    	result.setRecordType(submission.getRecordType());
     	    	result.setRecordTypeStr(submission.getRecordType().getRecordType());
     	    	result.setPadding(submission.getPadding());
     	    return result;
	 }
	
	public static List<SubmissionDTO>  toDTOs(Collection<Submission>  submissions){
		if (submissions == null) {
			return null;
		}
		List<SubmissionDTO> results = new ArrayList<SubmissionDTO>();
		for (Submission each : submissions) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static Submission  toEntity(SubmissionDTO  submissionDTO){
	 	if (submissionDTO == null) {
			return null;
		}
	 	Submission result  = new Submission();
        result.setId (submissionDTO.getId());
         result.setVersion (submissionDTO.getVersion());
         result.setContent (submissionDTO.getContent());
         result.setName (submissionDTO.getName());
         result.setCreatedBy (submissionDTO.getCreatedBy());
         result.setRecordNum (submissionDTO.getRecordNum());
         result.setPadding(submissionDTO.getPadding());
 	  	return result;
	 }
	
	public static List<Submission> toEntities(Collection<SubmissionDTO> submissionDTOs) {
		if (submissionDTOs == null) {
			return null;
		}
		
		List<Submission> results = new ArrayList<Submission>();
		for (SubmissionDTO each : submissionDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
