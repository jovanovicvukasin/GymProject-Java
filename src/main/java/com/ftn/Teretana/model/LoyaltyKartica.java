package com.ftn.Teretana.model;

public class LoyaltyKartica {

	private Long id;
	private int popust;
	private int brojPoena;
	private Korisnik korisnik;
	private boolean odobrena = false;
	
	
	
	public LoyaltyKartica() {
	}
	
	

	public LoyaltyKartica(int popust, int brojPoena, Korisnik korisnik, boolean odobrena) {
		super();
		this.popust = popust;
		this.brojPoena = brojPoena;
		this.korisnik = korisnik;
		this.odobrena = odobrena;
	}



	public LoyaltyKartica(Long id, int popust, int brojPoena, Korisnik korisnik, boolean odobrena) {
		super();
		this.id = id;
		this.popust = popust;
		this.brojPoena = brojPoena;
		this.korisnik = korisnik;
		this.odobrena = odobrena;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getPopust() {
		return popust;
	}
	public void setPopust(int popust) {
		this.popust = popust;
	}
	public int getBrojPoena() {
		return brojPoena;
	}
	public void setBrojPoena(int brojPoena) {
		this.brojPoena = brojPoena;
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	public boolean isOdobrena() {
		return odobrena;
	}
	public void setOdobrena(boolean odobrena) {
		this.odobrena = odobrena;
	}
	
	
}
