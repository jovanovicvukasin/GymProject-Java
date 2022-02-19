package com.ftn.Teretana.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.ftn.Teretana.model.TerminTreninga;
import com.ftn.Teretana.model.TipTreninga;
import com.ftn.Teretana.model.Trening;
import com.ftn.Teretana.service.KorisnikService;
import com.ftn.Teretana.service.KorpaService;
import com.ftn.Teretana.service.LoyaltyKarticaService;
import com.ftn.Teretana.service.TerminService;
import com.ftn.Teretana.service.TipTreningaService;
import com.ftn.Teretana.service.TreningService;

@Controller
@RequestMapping(value="/Korpa")
public class KorpaController {
	
	@Autowired
	private KorpaService korpaService;
	
	@Autowired
	private TreningService treningService;
	
	@Autowired
	private TerminService terminService;
	
	
	
	@Autowired
	private TipTreningaService tipService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private LoyaltyKarticaService karticaService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL; 

	@PostConstruct
	public void init() {	
		baseURL = servletContext.getContextPath() + "/";			
	}
	
	@GetMapping
	public ModelAndView index(@RequestParam(required = false) Long terminId, @RequestParam(required=false) Long korisnikId,
			@RequestParam(required=false) Double cena,
			@RequestParam(required=false) Boolean aktivna,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("korisnik")) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		List<Korpa> korpe = korpaService.findForOne(prijavljeniKorisnik.getId());
		List<Korpa> kk = korpaService.find(terminId, korisnikId, cena, aktivna);
		List<Trening> treninzi = treningService.findAll();
		List<TipTreninga> tipoviTreninga = tipService.findAll();
		LoyaltyKartica kartica = karticaService.findKorisnik(prijavljeniKorisnik.getId());
		List<LoyaltyKartica> kartice = karticaService.findAll();
		List<Korisnik> korisnici = new ArrayList<>();
		List<Korisnik> korisniciKartica = new ArrayList<>();

		
		for(int i =0; i<kartice.size(); i++) {
			korisnici.add(kartice.get(i).getKorisnik());
		}
		
		for(int i=0; i<kartice.size(); i++) {
			if(kartice.get(i).isOdobrena() == true)
				korisniciKartica.add(kartice.get(i).getKorisnik());
		}
		
		ModelAndView rezultat = new ModelAndView("korpa");
		rezultat.addObject("korpe", korpe);
		rezultat.addObject("treninzi", treninzi);
		rezultat.addObject("tipoviTreninga", tipoviTreninga);
		rezultat.addObject("kartica", kartica);
		rezultat.addObject("kartice", kartice);
		rezultat.addObject("kk", kk);
		rezultat.addObject("korisniciKartica", korisniciKartica);
		rezultat.addObject("korisnici", korisnici);

	

		return rezultat;
		
	}
	
	
	@PostMapping(value="/Create")
	public void Create(@RequestParam Long terminId, @RequestParam String korisnikId, @RequestParam Double cena, 
			 HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("korisnik")) {
			response.sendRedirect(baseURL);
			return;
		}
		
		TerminTreninga termin = terminService.findOne(terminId);
		if(termin == null) {
			response.sendRedirect(baseURL);
			return;
		}
		if(termin.getKapacitet() == 0) {
			response.sendRedirect(baseURL);
			return;
		}
		
		Korisnik korisnik = korisnikService.findOne(korisnikId);
		if(korisnik == null) {
			response.sendRedirect(baseURL);
			return;
		}
		
		Korpa korpa = new Korpa(termin, korisnik, termin.getTrening().getCena(), true);
		korpaService.save(korpa);
		
		response.sendRedirect(baseURL);
	}
	
	@PostMapping(value="/Delete")
	public void Delete(@RequestParam Long id, HttpSession session, HttpServletResponse response) throws IOException{
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("korisnik")) {
			response.sendRedirect(baseURL);
			return;
		}
		
		korpaService.delete(id);
		
		response.sendRedirect(baseURL + "Korpa");
	}

}
