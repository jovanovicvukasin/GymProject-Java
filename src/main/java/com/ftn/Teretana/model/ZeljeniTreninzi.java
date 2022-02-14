package com.ftn.Teretana.model;

import java.util.ArrayList;
import java.util.List;

public class ZeljeniTreninzi {
	
	private Long id;
	private List<Trening> trening = new ArrayList<>();
	private Korisnik korisnik;
	
	public ZeljeniTreninzi() {
	}

	public ZeljeniTreninzi(Korisnik korisnik) {
		super();
		this.korisnik = korisnik;
	}

	public ZeljeniTreninzi(Long id, Korisnik korisnik) {
		super();
		this.id = id;
		this.korisnik = korisnik;
	}

	public ZeljeniTreninzi(Long id, List<Trening> trening, Korisnik korisnik) {
		super();
		this.id = id;
		this.trening = trening;
		this.korisnik = korisnik;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Trening> getTrening() {
		return trening;
	}
	public void setTrening(List<Trening> trening) {
		this.trening = trening;
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	
	

}
