package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulationGeneration.core.domain.Submission;

public interface SubmissionApplication {

	public Submission getSubmission(Long id);
	
	public void creatSubmission(Submission submission);
	
	public void updateSubmission(Submission submission);
	
	public void removeSubmission(Submission submission);
	
	public void removeSubmissions(Set<Submission> submissions);
	
	public List<Submission> findAllSubmission();
	
}

