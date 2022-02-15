package com.ftn.Teretana.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.Teretana.model.Korisnik;
import com.ftn.Teretana.model.Sala;
import com.ftn.Teretana.service.SalaService;

@Controller
@RequestMapping(value="/Sale")
public class SalaController  {
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL; 

	@PostConstruct
	public void init() {	
		baseURL = servletContext.getContextPath() + "/";			
	}
	
	@PostMapping(value="/Create")
	public ModelAndView create(@RequestParam String oznakaSale, @RequestParam Integer kapacitet,
			HttpSession session, HttpServletResponse response) throws IOException  {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if(prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("administrator")) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		try {
			Sala postoji = salaService.findOne(oznakaSale);
			if(postoji != null) {
				throw new Exception("Sala sa tom oznakom vec postoji!");
			}
			if(oznakaSale.equals("") || oznakaSale == null || kapacitet == 0 || kapacitet.equals(null)) {
				throw new Exception("Morate popuniti sva polja!");
			}
			
			Sala sala = new Sala(oznakaSale, kapacitet);
			salaService.save(sala);
			
			response.sendRedirect(baseURL + "/Sale");
			
			return null;
		}catch (Exception e) {
			// TODO: handle exception
			String poruka = e.getMessage();
			if (poruka == "") {
				poruka = "Neuspesno dodavanje sale!";
			}
			
			ModelAndView rezultat = new ModelAndView("dodavanjeSale");
			rezultat.addObject("poruka", poruka);
			
			return rezultat;
		}
		
		
		
	}
	
	@GetMapping
	public ModelAndView index(
			@RequestParam(required=false) String oznakaSale,
			HttpSession session, HttpServletResponse response) throws IOException{
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if(prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("administrator")) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		if(oznakaSale != null && oznakaSale.trim().equals(""))
			oznakaSale = null;
		
		List<Sala> sale = salaService.findOznakaSale(oznakaSale);
		
		//List<Sala> sale = salaService.findAll();
		ModelAndView rezultat = new ModelAndView("sale");
		rezultat.addObject("sale", sale);
		
		return rezultat;
	}
	
	

}
