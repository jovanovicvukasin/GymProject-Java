package com.ftn.Teretana.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ftn.Teretana.model.Korisnik;

public interface KorisnikDAO {

	public void save(Korisnik korisnik);
	public List<Korisnik> find(String korisnickoIme, String email, String ime, String prezime, LocalDate datumRodjenja, String adresa, String brojTelefona, LocalDateTime datumIVremeRegistracije, String uloga, Boolean blokiran);
	public Korisnik findOne(String korisnickoIme);
	Korisnik findOne(String korisnickoIme, String lozinka);
	public void edit(Korisnik korisnik);
	public List<Korisnik> find(String korisnickoIme, String uloga);
	public void editProfile(Korisnik korisnik);
	
	
}
