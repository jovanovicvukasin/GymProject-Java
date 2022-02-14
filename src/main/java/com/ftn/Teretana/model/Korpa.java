package com.ftn.Teretana.model;

public class Korpa {
	
	private Long id;
	//private Trening trening;
	private TerminTreninga terminTreninga;
	private Korisnik korisnik;
	private boolean aktivna = true;
	
	
	
	
	public Korpa() {
	}

	public Korpa(TerminTreninga terminTreninga, Korisnik korisnik, boolean aktivna) {
		super();
		this.terminTreninga = terminTreninga;
		this.korisnik = korisnik;
		this.aktivna = aktivna;
	}

	public Korpa(TerminTreninga terminTreninga, Korisnik korisnik) {
		super();
		
		this.terminTreninga = terminTreninga;
		this.korisnik = korisnik;
	}

	public Korpa(Long id, TerminTreninga terminTreninga, Korisnik korisnik, boolean aktivna) {
		super();
		this.id = id;
		this.terminTreninga = terminTreninga;
		this.korisnik = korisnik;
		this.aktivna = aktivna;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public TerminTreninga getTerminTreninga() {
		return terminTreninga;
	}
	public void setTerminTreninga(TerminTreninga terminTreninga) {
		this.terminTreninga = terminTreninga;
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	public boolean isAktivna() {
		return aktivna;
	}
	public void setAktivna(boolean aktivna) {
		this.aktivna = aktivna;
	}
	
	

}
