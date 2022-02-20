package com.ftn.Teretana.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.Teretana.dao.ZakazivanjeDAO;
import com.ftn.Teretana.model.Zakazivanje;
import com.ftn.Teretana.service.ZakazivanjeService;

@Service
public class DatabaseZakazivanjeService implements ZakazivanjeService {

	@Autowired
	private ZakazivanjeDAO zakazivanjeDAO;
	
	@Override
	public Zakazivanje save(Zakazivanje zakazivanje) {
		// TODO Auto-generated method stub
		zakazivanjeDAO.save(zakazivanje);
		return zakazivanje;
	}

	@Override
	public Zakazivanje findOne(Long id) {
		// TODO Auto-generated method stub
		return zakazivanjeDAO.findOne(id);
	}

	@Override
	public List<Zakazivanje> find(Long korpaId, Double ukupnaCena, LocalDateTime datumZakazivanja, Long korisnikId,
			Integer ukupanBroj) {
		// TODO Auto-generated method stub
		return zakazivanjeDAO.find(korpaId, ukupnaCena, datumZakazivanja, korisnikId, ukupanBroj);
	}

	@Override
	public List<Zakazivanje> findAll() {
		// TODO Auto-generated method stub
		return zakazivanjeDAO.findAll();
	}

}
