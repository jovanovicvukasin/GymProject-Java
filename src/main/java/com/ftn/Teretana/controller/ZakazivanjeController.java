package com.ftn.Teretana.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
import com.ftn.Teretana.model.Korpa;
import com.ftn.Teretana.model.LoyaltyKartica;
import com.ftn.Teretana.model.TerminTreninga;
import com.ftn.Teretana.model.Zakazivanje;
import com.ftn.Teretana.service.KorisnikService;
import com.ftn.Teretana.service.KorpaService;
import com.ftn.Teretana.service.LoyaltyKarticaService;
import com.ftn.Teretana.service.TerminService;
import com.ftn.Teretana.service.ZakazivanjeService;

@Controller
@RequestMapping(value="/Zakazivanje")
public class ZakazivanjeController {
	
	@Autowired
	private ZakazivanjeService zakazivanjeService;
	
	@Autowired
	private KorpaService korpaService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TerminService terminService;
	
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
	public ModelAndView index(@RequestParam(required=false) Long korpaId, @RequestParam(required=false) Double ukupnaCena,
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate datumZakazivanja,
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeZakazivanja,
			@RequestParam(required=false) Long korisnikId, @RequestParam(required=false) Integer ukupanBroj,
			HttpSession session, HttpServletResponse response) throws IOException{
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		LocalDateTime datumVremeZakazivanja = null;
		if(datumZakazivanja != null || vremeZakazivanja != null)
			datumVremeZakazivanja = LocalDateTime.of(datumZakazivanja, vremeZakazivanja);
		
		List<Zakazivanje> zakazivanja = zakazivanjeService.find(korpaId, ukupnaCena, datumVremeZakazivanja, korisnikId, ukupanBroj);
		List<Korpa> korpe = korpaService.findAll();
		
		ModelAndView rezultat = new ModelAndView("zakazivanja");
		rezultat.addObject("zak", zakazivanja);
		rezultat.addObject("sortiranje", Comparator.comparing((	Zakazivanje::getDatumZakazivanja), (s1, s2) -> {return s2.compareTo(s1);}));
		rezultat.addObject("korpe", korpe);
		
		return rezultat;
		
	}
	
	@GetMapping(value="/Details")
	public ModelAndView details(@RequestParam Long id, HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		Zakazivanje zakazivanje = zakazivanjeService.findOne(id);
	
		
		
		ModelAndView rezultat = new ModelAndView("zakazivanje");
		rezultat.addObject("zak", zakazivanje);
		
		return rezultat;
		
	}
	
	@PostMapping(value="/Create")
	public ModelAndView create(@RequestParam(name="id", required=false) Long[] id,
			@RequestParam Double cena, @RequestParam String korisnickoIme, @RequestParam(name="terminId", required=false) Long[] terminId,
			@RequestParam(name="karticaId", required=false) Long karticaId, @RequestParam(required = false) Integer brojPoena,	
			HttpSession session, HttpServletResponse response) throws IOException{
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("korisnik")) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		Korisnik korisnik = korisnikService.findOne(korisnickoIme);
		if (korisnik == null) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		LocalDateTime datum = LocalDateTime.now();
		
		List<Korpa> korpe = korpaService.find(id);
		Double ukupnaCena = 0.0;
		Integer ukupanBroj = 0;
		//Long terminId = 0L;
		
		for(int i=0; i < korpe.size(); i++) {
			if(korpe.get(i).isAktivna() == true && korpe.get(i).getKorisnik().getId() == prijavljeniKorisnik.getId()) {
				ukupnaCena += korpe.get(i).getCena();
				ukupanBroj += 1;
				
				Integer brojTreninga = 1;
				
				List<TerminTreninga> termini = terminService.find(terminId);
				for(TerminTreninga t : termini) {
					
					if(t.getId() == korpe.get(i).getTerminTreninga().getId()) {
						try {if(t.getKapacitet() == 0) {
							throw new Exception("Kapacitet za izabrane termine je popunjen!");
						}
							
						}catch (Exception e) {
							// TODO: handle exception
							String poruka = e.getMessage();
							if(poruka == "") {
								poruka = "Neuspesno zakazivanje";
							}
							ModelAndView rezultat = new ModelAndView("korpa");
							rezultat.addObject("poruka", poruka);
							return rezultat;
						}
						
						t.setKapacitet(t.getKapacitet() - brojTreninga);
						terminService.updateKapacitet(t);
					}
					
					
					
			}
				
			}
			
		}
		
		if(brojPoena != null) {
			ukupnaCena -= (ukupnaCena * (brojPoena * 5)) / 100;
		}
		
		Zakazivanje zakazivanje = new Zakazivanje(ukupnaCena, datum, korisnik, ukupanBroj);
		
		zakazivanje.setKorpe(korpaService.find(id));
		zakazivanjeService.save(zakazivanje);
		
		for(int i=0; i<korpe.size(); i++) {
			korpe.get(i).setAktivna(false);
			korpaService.update(korpe.get(i));
		}
		
		//List<LoyaltyKartica> kartice = karticaService.findAll();
		LoyaltyKartica karticaa = karticaService.findKorisnik(prijavljeniKorisnik.getId());
		
		if(karticaId == null && ukupnaCena >= 500) {
			if(karticaa.getKorisnik().getId() == prijavljeniKorisnik.getId()) {
				if(karticaa.isOdobrena() == true) {
					karticaa.setBrojPoena((int) (karticaa.getBrojPoena() + ukupnaCena / 500));
					karticaa.setPopust((int) (karticaa.getPopust() + ukupnaCena / 500 * 5));
					karticaService.update(karticaa);
				}
			}
		}
		
		if(karticaId != null) {
			LoyaltyKartica kartica = karticaService.findOne(karticaId);
			if(kartica == null) {
				response.sendRedirect(baseURL);
				return null;
			}
			
			if(brojPoena != null) {
				kartica.setBrojPoena(kartica.getBrojPoena() - brojPoena);
				kartica.setPopust(kartica.getPopust() - (brojPoena * 5));
			}else {
				kartica.setBrojPoena(kartica.getBrojPoena());
			}
			karticaService.update(kartica);
		}
		
		response.sendRedirect(baseURL);
		
		return null;
		
	}

}
