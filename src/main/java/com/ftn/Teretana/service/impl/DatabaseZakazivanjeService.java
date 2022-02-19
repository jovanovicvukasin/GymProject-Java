package com.ftn.Teretana.service.impl;

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

}
