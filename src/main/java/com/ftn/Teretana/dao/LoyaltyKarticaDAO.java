package com.ftn.Teretana.dao;

import java.util.List;

import com.ftn.Teretana.model.LoyaltyKartica;

public interface LoyaltyKarticaDAO {
	
	public int save(LoyaltyKartica kartica);
	public int update(LoyaltyKartica kartica);
	public int delete(Long id);
	public LoyaltyKartica findOne(Long id);
	public List<LoyaltyKartica> findAll();
	public LoyaltyKartica findKorisnik(Long id);


}
