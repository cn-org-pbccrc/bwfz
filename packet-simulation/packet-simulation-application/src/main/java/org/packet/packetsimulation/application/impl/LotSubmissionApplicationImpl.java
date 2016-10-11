package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.LotSubmissionApplication;
import org.packet.packetsimulation.core.domain.LotSubmission;

@Named
@Transactional
public class LotSubmissionApplicationImpl implements LotSubmissionApplication {

	public LotSubmission getLotSubmission(Long id) {
		return LotSubmission.get(LotSubmission.class, id);
	}
	
	public void creatLotSubmission(LotSubmission lotSubmission) {
		lotSubmission.save();
	}
	
	public void creatLotSubmissions(Set<LotSubmission> lotSubmissions) {
		for (LotSubmission lotSubmission : lotSubmissions) {
			lotSubmission.save();
		}
	}
	
	public void updateLotSubmission(LotSubmission lotSubmission) {
		lotSubmission .save();
	}
	
	public void removeLotSubmission(LotSubmission lotSubmission) {
		if(lotSubmission != null){
			lotSubmission.remove();
		}
	}
	
	public void removeLotSubmissions(Set<LotSubmission> lotSubmissions) {
		for (LotSubmission lotSubmission : lotSubmissions) {
			lotSubmission.remove();
		}
	}
	
	public List<LotSubmission> findAllLotSubmission() {
		return LotSubmission.findAll(LotSubmission.class);
	}
	
}
