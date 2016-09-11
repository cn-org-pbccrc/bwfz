package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.SubmissionApplication;
import org.packet.packetsimulationGeneration.core.domain.Submission;

@Named
@Transactional
public class SubmissionApplicationImpl implements SubmissionApplication {

	public Submission getSubmission(Long id) {
		return Submission.get(Submission.class, id);
	}
	
	public void creatSubmission(Submission submission) {
		submission.save();
	}
	
	public void updateSubmission(Submission submission) {
		submission .save();
	}
	
	public void removeSubmission(Submission submission) {
		if(submission != null){
			submission.remove();
		}
	}
	
	public void removeSubmissions(Set<Submission> submissions) {
		for (Submission submission : submissions) {
			submission.remove();
		}
	}
	
	public List<Submission> findAllSubmission() {
		return Submission.findAll(Submission.class);
	}
	
}
