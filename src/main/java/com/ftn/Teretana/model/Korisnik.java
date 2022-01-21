package com.ftn.Teretana.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Korisnik {
	
	private Long id;
	private String korisnickoIme;
	private String lozinka; 
	private String email;
	private String ime;
	private String prezime;
	private LocalDate datumRodjenja; 
	private String adresa;
	private String brojTelefona;
	private LocalDateTime datumIVremeRegistracije;
	private String uloga;
	private boolean blokiran = false;
	
	
	
	public Korisnik() {
	}
	
	
	
	
	
	public Korisnik(String korisnickoIme, String lozinka, String email, String ime, String prezime,
			LocalDate datumRodjenja, String adresa, String brojTelefona, LocalDateTime datumIVremeRegistracije,
			String uloga) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumIVremeRegistracije = datumIVremeRegistracije;
		this.uloga = uloga;
	}





	public Korisnik(String korisnickoIme, String lozinka, String email, String ime, String prezime,
			LocalDate datumRodjenja, String adresa, String brojTelefona, LocalDateTime datumIVremeRegistracije,
			String uloga, boolean blokiran) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumIVremeRegistracije = datumIVremeRegistracije;
		this.uloga = uloga;
		this.blokiran = blokiran;
	}
	public Korisnik(Long id, String korisnickoIme, String lozinka, String email, String ime, String prezime,
			LocalDate datumRodjenja, String adresa, String brojTelefona, LocalDateTime datumIVremeRegistracije,
			String uloga, boolean blokiran) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumIVremeRegistracije = datumIVremeRegistracije;
		this.uloga = uloga;
		this.blokiran = blokiran;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public LocalDate getDatumRodjenja() {
		return datumRodjenja;
	}
	public void setDatumRodjenja(LocalDate datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getBrojTelefona() {
		return brojTelefona;
	}
	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}
	public LocalDateTime getDatumIVremeRegistracije() {
		return datumIVremeRegistracije;
	}
	public void setDatumIVremeRegistracije(LocalDateTime datumIVremeRegistracije) {
		this.datumIVremeRegistracije = datumIVremeRegistracije;
	}
	public String getUloga() {
		return uloga;
	}
	public void setUloga(String uloga) {
		this.uloga = uloga;
	}
	public boolean isBlokiran() {
		return blokiran;
	}
	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}
	
	


}
