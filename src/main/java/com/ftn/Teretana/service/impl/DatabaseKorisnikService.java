package com.ftn.Teretana.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.Teretana.dao.KorisnikDAO;
import com.ftn.Teretana.model.Korisnik;
import com.ftn.Teretana.service.KorisnikService;

@Service
public class DatabaseKorisnikService implements KorisnikService {

	@Autowired
	private KorisnikDAO korisnikDAO;
	
	@Override
	public Korisnik save(Korisnik korisnik) {
		// TODO Auto-generated method stub
		korisnikDAO.save(korisnik);
		return korisnik;
	}

	@Override
	public List<Korisnik> find(String korisnickoIme, String email, String ime, String prezime, LocalDate datumRodjenja,
			String adresa, String brojTelefona, LocalDateTime datumIVremeRegistracije, String uloga, Boolean blokiran) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Korisnik findOne(String korisnickoIme) {
		// TODO Auto-generated method stub
		return korisnikDAO.findOne(korisnickoIme);
	}

	@Override
	public Korisnik findOne(String korisnickoIme, String lozinka) {
		// TODO Auto-generated method stub
		return korisnikDAO.findOne(korisnickoIme, lozinka);
	}

	@Override
	public Korisnik edit(Korisnik korisnik) {
		// TODO Auto-generated method stub
		korisnikDAO.edit(korisnik);
		return korisnik;
	}

	@Override
	public List<Korisnik> find(String korisnickoIme, String uloga) {
		// TODO Auto-generated method stub
		return korisnikDAO.find(korisnickoIme, uloga);
	}

	@Override
	public Korisnik editProfile(Korisnik korisnik) {
		// TODO Auto-generated method stub
		korisnikDAO.editProfile(korisnik);
		return korisnik;
	}

	
}
