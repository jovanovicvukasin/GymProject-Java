package com.ftn.Teretana.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Zakazivanje {
	
	private Long id;
	private List<Korpa> korpe = new ArrayList<>();
	private double ukupnaCena;
	private LocalDateTime datumZakazivanja;
	private Korisnik korisnik;
	private int ukupanBroj;
	
	
	
	public Zakazivanje(double ukupnaCena, LocalDateTime datumZakazivanja, Korisnik korisnik, int ukupanBroj) {
		super();
		this.ukupnaCena = ukupnaCena;
		this.datumZakazivanja = datumZakazivanja;
		this.korisnik = korisnik;
		this.ukupanBroj = ukupanBroj;
	}
	public Zakazivanje(Long id, double ukupnaCena, LocalDateTime datumZakazivanja, Korisnik korisnik, int ukupanBroj) {
		super();
		this.id = id;
		this.ukupnaCena = ukupnaCena;
		this.datumZakazivanja = datumZakazivanja;
		this.korisnik = korisnik;
		this.ukupanBroj = ukupanBroj;
	}
	public Zakazivanje(Long id, List<Korpa> korpe, double ukupnaCena, LocalDateTime datumZakazivanja, Korisnik korisnik,
			int ukupanBroj) {
		super();
		this.id = id;
		this.korpe = korpe;
		this.ukupnaCena = ukupnaCena;
		this.datumZakazivanja = datumZakazivanja;
		this.korisnik = korisnik;
		this.ukupanBroj = ukupanBroj;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Korpa> getKorpe() {
		return korpe;
	}
	public void setKorpe(List<Korpa> korpe) {
		this.korpe = korpe;
	}
	public double getUkupnaCena() {
		return ukupnaCena;
	}
	public void setUkupnaCena(double ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}
	public LocalDateTime getDatumZakazivanja() {
		return datumZakazivanja;
	}
	public void setDatumZakazivanja(LocalDateTime datumZakazivanja) {
		this.datumZakazivanja = datumZakazivanja;
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	public int getUkupanBroj() {
		return ukupanBroj;
	}
	public void setUkupanBroj(int ukupanBroj) {
		this.ukupanBroj = ukupanBroj;
	}
	
	

}
