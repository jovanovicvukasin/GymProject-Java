package com.ftn.Teretana.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ftn.Teretana.model.Korisnik;

public interface KorisnikService {
	
	Korisnik save(Korisnik korisnik);
	List<Korisnik> find(String korisnickoIme, String email, String ime, String prezime, LocalDate datumRodjenja, String adresa, String brojTelefona, LocalDateTime datumIVremeRegistracije, String uloga, Boolean blokiran);

}
