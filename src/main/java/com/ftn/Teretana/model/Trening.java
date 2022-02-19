package com.ftn.Teretana.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Trening {
	
	private Long id;
	private String naziv;
	private String trener;
	private String opis;
	private String slika;
	private List<TipTreninga> tipTreninga = new ArrayList<>();
	private String vrstaTreninga; //pojedinacni ili grupni
	private String nivoTreninga; //amaterski, srednji ili napredni
	private LocalTime trajanje;
	private float ocena;
	private double cena;

	public Trening() {
	}
	
	
	
	

	public Trening(Long id, String naziv, String trener, List<TipTreninga> tipTreninga, double cena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.trener = trener;
		this.tipTreninga = tipTreninga;
		this.cena = cena;
	}



	public Trening(Long id, String naziv, String trener, double cena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.trener = trener;
		this.cena = cena;
	}





	public Trening(Long id, String naziv, LocalTime trajanje, Double cena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.trajanje = trajanje;
		this.cena = cena;
	}





	public Trening(String naziv, String trener, String opis, double cena,
			String vrstaTreninga, String nivoTreninga, LocalTime trajanje) {
		super();
		this.naziv = naziv;
		this.trener = trener;
		this.opis = opis;
		this.cena = cena;
		this.vrstaTreninga = vrstaTreninga;
		this.nivoTreninga = nivoTreninga;
		this.trajanje = trajanje;
	}



	public Trening(String naziv, String trener, String opis, String slika, List<TipTreninga> tipTreninga, double cena,
			String vrstaTreninga, String nivoTreninga, LocalTime trajanje, float ocena) {
		super();
		this.naziv = naziv;
		this.trener = trener;
		this.opis = opis;
		this.slika = slika;
		this.tipTreninga = tipTreninga;
		this.cena = cena;
		this.vrstaTreninga = vrstaTreninga;
		this.nivoTreninga = nivoTreninga;
		this.trajanje = trajanje;
		this.ocena = ocena;
	}
	public Trening(Long id, String naziv, String trener, String opis, String slika, List<TipTreninga> tipTreninga,
			double cena, String vrstaTreninga, String nivoTreninga, LocalTime trajanje, float ocena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.trener = trener;
		this.opis = opis;
		this.slika = slika;
		this.tipTreninga = tipTreninga;
		this.cena = cena;
		this.vrstaTreninga = vrstaTreninga;
		this.nivoTreninga = nivoTreninga;
		this.trajanje = trajanje;
		this.ocena = ocena;
	}
	
	
	
	public Trening(Long id, String naziv, String trener, String opis, String slika, double cena, String vrstaTreninga,
			String nivoTreninga, LocalTime trajanje, float ocena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.trener = trener;
		this.opis = opis;
		this.slika = slika;
		this.cena = cena;
		this.vrstaTreninga = vrstaTreninga;
		this.nivoTreninga = nivoTreninga;
		this.trajanje = trajanje;
		this.ocena = ocena;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getTrener() {
		return trener;
	}
	public void setTrener(String trener) {
		this.trener = trener;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
	}
	
	public List<TipTreninga> getTipTreninga() {
		return tipTreninga;
	}
	public void setTipTreninga(List<TipTreninga> tipTreninga) {
		this.tipTreninga = tipTreninga;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public String getVrstaTreninga() {
		return vrstaTreninga;
	}
	public void setVrstaTreninga(String vrstaTreninga) {
		this.vrstaTreninga = vrstaTreninga;
	}
	public String getNivoTreninga() {
		return nivoTreninga;
	}
	public void setNivoTreninga(String nivoTreninga) {
		this.nivoTreninga = nivoTreninga;
	}
	public LocalTime getTrajanje() {
		return trajanje;
	}
	public void setTrajanje(LocalTime trajanje) {
		this.trajanje = trajanje;
	}
	public float getOcena() {
		return ocena;
	}
	public void setOcena(float ocena) {
		this.ocena = ocena;
	}
	
	
	

}
