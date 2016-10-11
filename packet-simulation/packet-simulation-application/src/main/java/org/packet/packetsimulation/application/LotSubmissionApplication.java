package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.LotSubmission;

public interface LotSubmissionApplication {

	public LotSubmission getLotSubmission(Long id);
	
	public void creatLotSubmission(LotSubmission lotSubmission);
	
	public void creatLotSubmissions(Set<LotSubmission> lotSubmissions);
	
	public void updateLotSubmission(LotSubmission lotSubmission);
	
	public void removeLotSubmission(LotSubmission lotSubmission);
	
	public void removeLotSubmissions(Set<LotSubmission> lotSubmissions);
	
	public List<LotSubmission> findAllLotSubmission();
	
}

