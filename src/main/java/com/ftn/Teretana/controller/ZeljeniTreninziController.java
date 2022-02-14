package com.ftn.Teretana.controller;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

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
import com.ftn.Teretana.model.Trening;
import com.ftn.Teretana.model.ZeljeniTreninzi;
import com.ftn.Teretana.service.KorisnikService;
import com.ftn.Teretana.service.TreningService;
import com.ftn.Teretana.service.ZeljeniTreninziService;

@Controller
@RequestMapping(value="/ZeljeniTreninzi")
public class ZeljeniTreninziController {
	
	@Autowired
	private ZeljeniTreninziService zeljeniTreninziService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TreningService treningService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL; 

	@PostConstruct
	public void init() {	
		baseURL = servletContext.getContextPath() + "/";			
	}
	
	@GetMapping
	public ModelAndView index(@RequestParam(required=false) Long treningId, @RequestParam(required=false) Long korisnikId,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("korisnik")) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		List<ZeljeniTreninzi> zeljeniTreninzi = zeljeniTreninziService.find(treningId, korisnikId);
		List<Trening> treninzi = new ArrayList<>();
		
		for(int i=0; i < zeljeniTreninzi.size(); i++) {
			treninzi.removeAll(zeljeniTreninzi.get(i).getTrening());
			treninzi.addAll(zeljeniTreninzi.get(i).getTrening());
		}
		
		ModelAndView rez = new ModelAndView("zeljeniTreninzi");
		rez.addObject("zeljeniTreninzi", zeljeniTreninzi);
		rez.addObject("treninzi", treninzi);
		
		return rez;
		
	}
	
	
	
	@PostMapping(value ="/Create")
	public void create(@RequestParam(name="id", required=false) Long[] id, @RequestParam String korisnickoIme,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("korisnik")) {
			response.sendRedirect(baseURL);
			return;
		}
		
		Korisnik korisnik = korisnikService.findOne(korisnickoIme);
		if(korisnik == null) {
			response.sendRedirect(baseURL);
			return;
		}
		
		ZeljeniTreninzi zeljenTrening = new ZeljeniTreninzi(korisnik);
		zeljenTrening.setTrening(treningService.find(id));
		zeljeniTreninziService.save(zeljenTrening);
		
		response.sendRedirect(baseURL);
	}
	
	@PostMapping(value="/Delete")
	public void Delete(@RequestParam(name = "id") Long id,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("korisnik")) {
			response.sendRedirect(baseURL);
			return;
		}
		
		zeljeniTreninziService.delete(id);
		
		response.sendRedirect(baseURL + "ZeljeniTreninzi");
		
	}

}
