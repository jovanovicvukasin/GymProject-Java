package com.ftn.Teretana.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.ftn.Teretana.model.Korisnik;
import com.ftn.Teretana.service.KorisnikService;

@Controller
@RequestMapping(value="/Korisnici")
public class KorisnikController {
	
	
	public static final String KORISNIK_KEY = "prijavljeniKorisnik";

	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL; 

	@PostConstruct
	public void init() {	
		baseURL = servletContext.getContextPath() + "/";			
	}
	
	@PostMapping(value="/Register")
	public ModelAndView registracija(@RequestParam String korisnickoIme, @RequestParam String lozinka, 
			@RequestParam String ponovljenaLozinka, @RequestParam String email, @RequestParam String ime,
			@RequestParam String prezime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumRodjenja,
			@RequestParam String adresa, @RequestParam String brojTelefona, HttpServletResponse response) throws IOException {
		
		try {
			Korisnik postoji = korisnikService.findOne(korisnickoIme);
			if(postoji != null) {
				throw new Exception("Korisnik sa tim korisnickim imenom vec postoji!");
			}
			if (korisnickoIme.equals("") || lozinka.equals("") || ponovljenaLozinka.equals("") || email.equals("") || ime.equals("") ||
					prezime.equals("") || datumRodjenja == null || adresa.equals("") || brojTelefona.equals("")) {
					throw new Exception("Morate popuniti sva polja!");
				}
			else if (!lozinka.equals(ponovljenaLozinka)) {
				throw new Exception("Lozinke se ne podudaraju!");
			}
			
			LocalDateTime datum = LocalDateTime.now();
			
			String uloga = "korisnik";
			
			Korisnik korisnik = new Korisnik(korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datum, uloga);
			korisnikService.save(korisnik);
			
			response.sendRedirect(baseURL + "login.html");
			return null;
		}catch (Exception e) {
			// TODO: handle exception
			String poruka = e.getMessage();
			if (poruka == "") {
				poruka = "Neuspesna registracija!";
			}
			
			ModelAndView rezultat = new ModelAndView("registracija");
			rezultat.addObject("poruka", poruka);
			
			return rezultat;

		}
		
	}
	
	@PostMapping(value="/Login")
	public ModelAndView login(@RequestParam String korisnickoIme, @RequestParam String sifra, 
			HttpSession session, HttpServletResponse response) throws IOException {
				
		try {
			Korisnik korisnik = korisnikService.findOne(korisnickoIme, sifra);
			if(korisnik == null) {
				throw new Exception("Neispravni podaci!");
			}else if(korisnik.isBlokiran() == true) {
				throw new Exception("Korisnik se ne moze logovati!");

			}
			
			session.setAttribute(KorisnikController.KORISNIK_KEY, korisnik);
			
			response.sendRedirect(baseURL);

			return null;
		}catch (Exception e) {
			// TODO: handle exception
			String poruka = e.getMessage();
			if(poruka == "") {
				poruka = "Neuspesna prijava!";
			}
			
			ModelAndView rez = new ModelAndView("login");
			rez.addObject("poruka", poruka);
			
			return rez;

		}
		
		
	}
	
	@GetMapping(value="/Logout")
	public void logout(HttpSession session, HttpServletResponse response) throws IOException{
		
		session.invalidate();
		
		response.sendRedirect(baseURL);
	}
	
	@GetMapping(value="/Details")
	public ModelAndView details(@RequestParam String korisnickoIme, HttpSession session, HttpServletResponse response) throws IOException {

		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		
		if (prijavljeniKorisnik == null) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		Korisnik korisnik = korisnikService.findOne(korisnickoIme);

		ModelAndView rezultat = new ModelAndView("korisnik");
		List<String> uloge = new ArrayList<>();
		uloge.add("korisnik");
		uloge.add("administrator");
		rezultat.addObject("korisnik", korisnik);
		rezultat.addObject("uloge", uloge);
		return rezultat;
	}
	
	@PostMapping(value="/Edit")
	public void edit(@RequestParam String korisnickoIme, @RequestParam String uloga, @RequestParam(required=false) String blokiran,
			HttpSession session, HttpServletResponse response) throws IOException {
	
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("administrator")) {
			response.sendRedirect(baseURL);
			return;
		}
		
		Korisnik k = korisnikService.findOne(korisnickoIme);
		if (k == null) {
			response.sendRedirect(baseURL);
			return;
		}
		if(!prijavljeniKorisnik.equals(k)) {
			k.setUloga(uloga);
		}
		if(k.getUloga().equals("korisnik")) {
			k.setBlokiran(blokiran != null);
		}
	
		korisnikService.edit(k);

		response.sendRedirect(baseURL + "Korisnici");
		
	}
	
	@PostMapping(value="/EditProfile")
	public void editProfile(@RequestParam String korisnickoIme,@RequestParam String lozinka, 
			@RequestParam String novaLozinka1, @RequestParam String novaLozinka2, @RequestParam String email, @RequestParam String ime,
			@RequestParam String prezime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumRodjenja,
			@RequestParam String adresa, @RequestParam String brojTelefona, HttpSession session, HttpServletResponse response) throws IOException {
	
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("korisnik")) {
			response.sendRedirect(baseURL);
			return;
		}
		
		Korisnik k = korisnikService.findOne(korisnickoIme);
		if (k == null) {
			response.sendRedirect(baseURL);
			return;
		}
		
		if (email.equals("") || ime.equals("") ||
				prezime.equals("") || datumRodjenja == null || adresa.equals("") || brojTelefona.equals("")) {
					response.sendRedirect(baseURL + "Korisnici/Details?korisnickoIme=" + korisnickoIme);
					return;
			}
		if (!novaLozinka1.equals(novaLozinka2)) {
			response.sendRedirect(baseURL + "Korisnici/Details?korisnickoIme=" + korisnickoIme);
			return;
		}
		
		if (novaLozinka1.equals(novaLozinka2)) {
			k.setLozinka(novaLozinka2);
		}
		
		if(novaLozinka1.equals("") || novaLozinka2.equals("")) {
			k.setLozinka(lozinka);
		}
		
		k.setEmail(email);
		k.setIme(ime);
		k.setPrezime(prezime);
		k.setDatumRodjenja(datumRodjenja);
		k.setAdresa(adresa);
		k.setBrojTelefona(brojTelefona);
		
		
	
		korisnikService.editProfile(k);

		response.sendRedirect(baseURL);
		
	}
	
	@GetMapping
	public ModelAndView index(@RequestParam(required=false) String korisnickoIme,
			@RequestParam(required=false) String uloga,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if(prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("administrator")) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		if(korisnickoIme != null && korisnickoIme.trim().equals(""))
			korisnickoIme = null;
		
		
		if(uloga != null && uloga.trim().equals(""))
			uloga = null;
		
		List<Korisnik> korisnici = korisnikService.find(korisnickoIme, uloga);
		
		ModelAndView rezultat = new ModelAndView("korisnici");
		rezultat.addObject("korisnici", korisnici);
		
		return rezultat;
	}

}
