package com.ftn.Teretana.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.Teretana.dao.KomentarDAO;
import com.ftn.Teretana.model.Komentar;
import com.ftn.Teretana.service.KomentarService;

@Service
public class DatabaseKomentarService implements KomentarService {

	@Autowired
	private KomentarDAO komentarDAO;
	
	@Override
	public List<Komentar> findAll() {
		// TODO Auto-generated method stub
		return komentarDAO.findAll();
	}

	@Override
	public Komentar save(Komentar komentar) {
		// TODO Auto-generated method stub
		komentarDAO.save(komentar);
		return komentar;
	}

	@Override
	public Komentar update(Komentar komentar) {
		// TODO Auto-generated method stub
		komentarDAO.update(komentar);
		return komentar;
	}

	@Override
	public Komentar findOne(Long id) {
		// TODO Auto-generated method stub
		return komentarDAO.findOne(id);
	}

	@Override
	public List<Komentar> find(String tekst, Integer ocena, LocalDate datum, Long korisnikId, Long treningId,
			String statusKomentara) {
		// TODO Auto-generated method stub
		return komentarDAO.find(tekst, ocena, datum, korisnikId, treningId, statusKomentara);
	}

}
