package com.ftn.Teretana.model;

public class Korpa {
	
	private Long id;
	//private Trening trening;
	private TerminTreninga terminTreninga;
	private Korisnik korisnik;
	private double cena;
	private boolean aktivna = true;
	

	public Korpa() {
	}
	
	

	public Korpa(Long id, double cena) {
		super();
		this.id = id;
		this.cena = cena;
	}



	public Korpa(TerminTreninga terminTreninga, Korisnik korisnik, double cena, boolean aktivna) {
		super();
		this.terminTreninga = terminTreninga;
		this.korisnik = korisnik;
		this.cena = cena;
		this.aktivna = aktivna;
	}

	public Korpa(TerminTreninga terminTreninga, Korisnik korisnik, double cena) {
		super();
		
		this.terminTreninga = terminTreninga;
		this.korisnik = korisnik;
		this.cena = cena;
	}

	public Korpa(Long id, TerminTreninga terminTreninga, Korisnik korisnik, double cena, boolean aktivna) {
		super();
		this.id = id;
		this.terminTreninga = terminTreninga;
		this.korisnik = korisnik;
		this.cena = cena;
		this.aktivna = aktivna;
	}
	
	
	
	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
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
