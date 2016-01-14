package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.MesgApplication;
import org.packet.packetsimulation.core.domain.Mesg;

@Named
@Transactional
public class MesgApplicationImpl implements MesgApplication {
	
	@PersistenceContext
	protected EntityManager em;
	
	public void batchInsert(List<Mesg> list) {
		for (int i = 0; i < list.size(); i++) {
			em.persist(list.get(i));
			if (i % 100 == 0) {
				em.flush();
				em.clear();
			}
		}
	}
	
	public Mesg getMesg(Long id) {
		return Mesg.get(Mesg.class, id);
	}
	
	public void creatMesg(Mesg mesg) {
		mesg.save();
	}
	
	public void creatMesgs(List<Mesg> mesgs) {
		for (Mesg mesg : mesgs) {
			mesg.save();
		}
		//throw new RecoverableDataAccessException("TEST");
	}
	
	public void updateMesgs(Set<Mesg> mesgs) {
		for (Mesg mesg : mesgs) {
			mesg.save();
		}
		//throw new RecoverableDataAccessException("TEST");
	}
	
	public void updateMesg(Mesg mesg) {
		mesg.save();
	}
	
	public void removeMesg(Mesg mesg) {
		if(mesg != null){
			mesg.remove();
		}
	}
	
	public void removeMesgs(Set<Mesg> mesgs) {
		for (Mesg mesg : mesgs) {
			mesg.remove();
		}
	}
	
	public List<Mesg> findAllMesg() {
		return Mesg.findAll(Mesg.class);
	}
	
}
