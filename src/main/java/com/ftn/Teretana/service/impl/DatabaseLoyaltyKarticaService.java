package com.ftn.Teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.Teretana.dao.LoyaltyKarticaDAO;
import com.ftn.Teretana.model.LoyaltyKartica;
import com.ftn.Teretana.service.LoyaltyKarticaService;

@Service
public class DatabaseLoyaltyKarticaService implements LoyaltyKarticaService {

	@Autowired
	private LoyaltyKarticaDAO karticaDAO;
	
	@Override
	public LoyaltyKartica save(LoyaltyKartica kartica) {
		// TODO Auto-generated method stub
		karticaDAO.save(kartica);
		return kartica;
	}

	@Override
	public LoyaltyKartica update(LoyaltyKartica kartica) {
		// TODO Auto-generated method stub
		karticaDAO.update(kartica);
		return kartica;
	}

	@Override
	public LoyaltyKartica delete(Long id) {
		// TODO Auto-generated method stub
		LoyaltyKartica kartica = findOne(id);
		if (kartica != null) {
			karticaDAO.delete(id);
		}
		return kartica;
	}

	@Override
	public LoyaltyKartica findOne(Long id) {
		// TODO Auto-generated method stub
		return karticaDAO.findOne(id);
	}


	@Override
	public List<LoyaltyKartica> findAll() {
		// TODO Auto-generated method stub
		return karticaDAO.findAll();
	}

	@Override
	public LoyaltyKartica findKorisnik(Long id) {
		// TODO Auto-generated method stub
		return karticaDAO.findKorisnik(id);
	}

}
