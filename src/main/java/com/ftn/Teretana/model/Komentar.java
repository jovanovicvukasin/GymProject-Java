package com.ftn.Teretana.model;

import java.time.LocalDate;

public class Komentar {
	
	private Long id;
	private String tekst;
	private int ocena;
	private LocalDate datum;
	private Korisnik korisnik;
	private Trening trening;
	private String statusKomentara;
	
	public Komentar() {
	}

	public Komentar(String tekst, int ocena, LocalDate datum, Korisnik korisnik, Trening trening,
			String statusKomentara) {
		super();
		this.tekst = tekst;
		this.ocena = ocena;
		this.datum = datum;
		this.korisnik = korisnik;
		this.trening = trening;
		this.statusKomentara = statusKomentara;
	}

	public Komentar(Long id, String tekst, int ocena, LocalDate datum, Korisnik korisnik, Trening trening,
			String statusKomentara) {
		super();
		this.id = id;
		this.tekst = tekst;
		this.ocena = ocena;
		this.datum = datum;
		this.korisnik = korisnik;
		this.trening = trening;
		this.statusKomentara = statusKomentara;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	public int getOcena() {
		return ocena;
	}
	public void setOcena(int ocena) {
		this.ocena = ocena;
	}
	public LocalDate getDatum() {
		return datum;
	}
	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	public Trening getTrening() {
		return trening;
	}
	public void setTrening(Trening trening) {
		this.trening = trening;
	}
	public String getStatusKomentara() {
		return statusKomentara;
	}
	public void setStatusKomentara(String statusKomentara) {
		this.statusKomentara = statusKomentara;
	}
	
	

}
