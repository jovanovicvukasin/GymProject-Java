package com.ftn.Teretana.service;

import java.util.List;

import com.ftn.Teretana.model.LoyaltyKartica;

public interface LoyaltyKarticaService {

	LoyaltyKartica save(LoyaltyKartica kartica);
	LoyaltyKartica update(LoyaltyKartica kartica);
	LoyaltyKartica delete(Long id);
	LoyaltyKartica findOne(Long id);
	List<LoyaltyKartica> findAll();
	LoyaltyKartica findKorisnik(Long id);

}
