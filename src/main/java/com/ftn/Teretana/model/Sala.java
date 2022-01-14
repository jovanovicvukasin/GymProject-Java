package com.ftn.Teretana.model;

public class Sala {
	
	private Long id;
	private String oznakaSale;
	private int kapacitet;
	
	public Sala() {
	}
	
	public Sala(String oznakaSale, int kapacitet) {
		super();
		this.oznakaSale = oznakaSale;
		this.kapacitet = kapacitet;
	}
	
	public Sala(Long id, String oznakaSale, int kapacitet) {
		super();
		this.id = id;
		this.oznakaSale = oznakaSale;
		this.kapacitet = kapacitet;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOznakaSale() {
		return oznakaSale;
	}
	public void setOznakaSale(String oznakaSale) {
		this.oznakaSale = oznakaSale;
	}
	public int getKapacitet() {
		return kapacitet;
	}
	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}
	
	

}
