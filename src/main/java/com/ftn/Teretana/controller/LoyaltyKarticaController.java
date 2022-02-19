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
import com.ftn.Teretana.model.Korpa;
import com.ftn.Teretana.model.LoyaltyKartica;
import com.ftn.Teretana.service.KorpaService;
import com.ftn.Teretana.service.LoyaltyKarticaService;

@Controller
@RequestMapping(value="/Kartice")
public class LoyaltyKarticaController {
	
	@Autowired
	private LoyaltyKarticaService karticaService;
	
	@Autowired
	private KorpaService korpaService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL; 

	@PostConstruct
	public void init() {	
		baseURL = servletContext.getContextPath() + "/";			
	}
	
	@GetMapping
	public ModelAndView index(HttpSession session, HttpServletResponse response) throws IOException{
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		List<LoyaltyKartica> kartice = karticaService.findAll();
		
		ModelAndView rez = new ModelAndView("kartice");
		rez.addObject("kartice", kartice);
		
		return rez;
		
	}
	
	@GetMapping(value="/Details")
	public ModelAndView details(@RequestParam Long id, HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		LoyaltyKartica kartica = karticaService.findOne(id);
		List<Korpa> korpe = korpaService.findAll();
		ModelAndView rezultat = new ModelAndView("kartica");
		rezultat.addObject("kartica", kartica);
		rezultat.addObject("korpe", korpe);
		
		return rezultat;
	}
	
	@PostMapping(value="/Create")
	public void Create(HttpSession session, HttpServletResponse response) throws IOException {

		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("korisnik")) {
			response.sendRedirect(baseURL);
			return;
		}
		
		Integer popust = 50;
		Integer brojPoena = 10;
		Korisnik korisnik = prijavljeniKorisnik;
		
		LoyaltyKartica kartica = new LoyaltyKartica(popust, brojPoena, korisnik, false);
		karticaService.save(kartica);
		
		response.sendRedirect(baseURL + "Korpa");
	}
	
	@PostMapping(value="/Edit")
	public void edit(@RequestParam Long id, @RequestParam(required = false) Integer popust, @RequestParam(required = false) Integer brojPoena,
			@RequestParam(required = false) Boolean odobrena, HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null) {
			response.sendRedirect(baseURL);
			return;
		}

		LoyaltyKartica kartica = karticaService.findOne(id);
		if (kartica == null) {
			response.sendRedirect(baseURL);
			return;
		}
	
		if(popust != null && brojPoena != null) {
			kartica.setPopust(popust);
			kartica.setBrojPoena(brojPoena);
		}
		
		kartica.setOdobrena(true);
		karticaService.update(kartica);
		
		response.sendRedirect(baseURL + "Kartice");
	}
	
	@PostMapping(value="/Delete")
	public void Delete(@RequestParam Long id, HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("administrator")) {
			response.sendRedirect(baseURL);
			return;
		}

		karticaService.delete(id);

		response.sendRedirect(baseURL + "Kartice");
	}

}
