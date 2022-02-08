package com.ftn.Teretana.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
	
	//test
	
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

}
