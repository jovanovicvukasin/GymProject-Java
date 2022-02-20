package com.ftn.Teretana.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.Teretana.model.Komentar;
import com.ftn.Teretana.model.Korisnik;
import com.ftn.Teretana.model.Trening;
import com.ftn.Teretana.service.KomentarService;
import com.ftn.Teretana.service.KorisnikService;
import com.ftn.Teretana.service.TreningService;

@Controller
@RequestMapping(value="/Komentari")
public class KomentarController {
	
	@Autowired
	private KomentarService komentarService;
	
	@Autowired
	private TreningService treningService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL; 

	@PostConstruct
	public void init() {	
		baseURL = servletContext.getContextPath() + "/";			
	}
	
	@GetMapping
	public ModelAndView index(@RequestParam(required = false) String tekstKomentara, @RequestParam(required=false) Integer ocena,
			@RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datum,
			@RequestParam(required=false) Long korisnikId, @RequestParam(required=false) Long treningId,
			@RequestParam(required=false) String status, HttpSession session, HttpServletResponse response) throws IOException {
		
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		List<Komentar> komentari = komentarService.find(tekstKomentara, ocena, datum, korisnikId, treningId, status);
		
		ModelAndView rezultat = new ModelAndView("komentari");
		rezultat.addObject("komentari", komentari);
		
		return rezultat;
	}
	
	@GetMapping(value="/Details")
	public ModelAndView details(@RequestParam Long id, HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		Komentar komentar = komentarService.findOne(id);
		List<String> statusiKomentara = new ArrayList<>();
		statusiKomentara.add("naCekanju");
		statusiKomentara.add("odobren");
		statusiKomentara.add("nijeOdobren");
		
		ModelAndView rez = new ModelAndView("komentar");
		rez.addObject("komentar", komentar);
		rez.addObject("statusiKomentara", statusiKomentara);
		
		
		
		return rez;
	}
	
	
	@PostMapping(value="/Edit")
	public void edit(@RequestParam Long id, @RequestParam String status, HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null) {
			response.sendRedirect(baseURL);
			return;
		}
		
		Komentar komentar = komentarService.findOne(id);
		if(komentar == null) {
			response.sendRedirect(baseURL);
			
			return;
		}
		
		komentar.setStatusKomentara(status);
		komentarService.update(komentar);
		
		Trening trening = treningService.findOne(komentar.getTrening().getId());
		Integer ocena = komentar.getOcena();
		
		Float prosecnaOcena = (trening.getOcena() + ocena) / 2;
		
		if(komentar.getStatusKomentara().equals("odobren")) {
			trening.setOcena(prosecnaOcena);
			treningService.update(trening);
		}
		
		response.sendRedirect(baseURL + "Komentari");
		return;
		
	}
	
	@PostMapping(value="/Create")
	public void Create(@RequestParam String tekstKomentara, @RequestParam Integer ocena,
			@RequestParam String korisnickoIme, @RequestParam(required=false) Long treningId,
			HttpSession session, HttpServletResponse response) throws IOException {

		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("korisnik")) {
			response.sendRedirect(baseURL);
			return;
		}
		
		Trening trening = treningService.findOne(treningId);
		if (trening == null) {
			response.sendRedirect(baseURL);
			return;
		}
		
		Korisnik korisnik = korisnikService.findOne(korisnickoIme);
		if (korisnik == null) {
			response.sendRedirect(baseURL);
			return;
		}
		
		LocalDate datum = LocalDate.now();
		
		String status = "naCekanju";
		
		Komentar komentar = new Komentar(tekstKomentara, ocena, datum, korisnik, trening, status);
		komentarService.save(komentar);
		
		response.sendRedirect(baseURL + "treninzi/details?id=" + trening.getId());
	}
	

}
