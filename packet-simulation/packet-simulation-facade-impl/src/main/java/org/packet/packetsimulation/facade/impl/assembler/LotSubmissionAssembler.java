package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class LotSubmissionAssembler {
	
	public static LotSubmissionDTO  toDTO(LotSubmission  lotSubmission){
		if (lotSubmission == null) {
			return null;
		}
    	LotSubmissionDTO result  = new LotSubmissionDTO();
	    	result.setId (lotSubmission.getId());
     	    	result.setVersion (lotSubmission.getVersion());
     	    	result.setName (lotSubmission.getName());
     	    	result.setFrontPosition (lotSubmission.getFrontPosition());
     	    	result.setFileNumber (lotSubmission.getFileNumber());
     	    	result.setCompression (lotSubmission.getCompression());
     	    	result.setEncryption (lotSubmission.getEncryption());
     	    	result.setSerialNumber (lotSubmission.getSerialNumber());
     	    	result.setLotId(lotSubmission.getLot().getId());
     	    	result.setSubmissionFrom(lotSubmission.getSubmissionFrom());
     	    return result;
	 }
	
	public static List<LotSubmissionDTO>  toDTOs(Collection<LotSubmission>  lotSubmissions){
		if (lotSubmissions == null) {
			return null;
		}
		List<LotSubmissionDTO> results = new ArrayList<LotSubmissionDTO>();
		for (LotSubmission each : lotSubmissions) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static LotSubmission  toEntity(LotSubmissionDTO  lotSubmissionDTO){
	 	if (lotSubmissionDTO == null) {
			return null;
		}
	 	LotSubmission result  = new LotSubmission();
        result.setId (lotSubmissionDTO.getId());
         result.setVersion (lotSubmissionDTO.getVersion());
         result.setName (lotSubmissionDTO.getName());
         result.setFrontPosition (lotSubmissionDTO.getFrontPosition());
         result.setFileNumber (lotSubmissionDTO.getFileNumber());
         result.setCompression (lotSubmissionDTO.getCompression());
         result.setEncryption (lotSubmissionDTO.getEncryption());
         result.setSerialNumber (lotSubmissionDTO.getSerialNumber());
         result.setSubmissionFrom(lotSubmissionDTO.getSubmissionFrom());
 	  	return result;
	 }
	
	public static List<LotSubmission> toEntities(Collection<LotSubmissionDTO> lotSubmissionDTOs) {
		if (lotSubmissionDTOs == null) {
			return null;
		}
		
		List<LotSubmission> results = new ArrayList<LotSubmission>();
		for (LotSubmissionDTO each : lotSubmissionDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
