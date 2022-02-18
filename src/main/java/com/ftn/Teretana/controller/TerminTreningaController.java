package com.ftn.Teretana.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import com.ftn.Teretana.model.Sala;
import com.ftn.Teretana.model.TerminTreninga;
import com.ftn.Teretana.model.Trening;
import com.ftn.Teretana.service.SalaService;
import com.ftn.Teretana.service.TerminService;
import com.ftn.Teretana.service.TreningService;

@Controller
@RequestMapping(value="/Termin")
public class TerminTreningaController {
	
	@Autowired
	private TerminService terminService;
	
	@Autowired
	private SalaService salaService;

	@Autowired
	private TreningService treningService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL;
	
	
	@PostConstruct
	public void init() {	
		baseURL = servletContext.getContextPath() + "/";			
	}
	
	@GetMapping(value="/Create")
	public ModelAndView create(@RequestParam Long id, HttpSession session, HttpServletResponse response) throws IOException{
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if(prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("administrator")) {
			response.sendRedirect(baseURL);
			return null;
		}
		
		Trening trening = treningService.findOne(id);
		List<Sala> sale = salaService.findAll();
	    
		//LocalDate now = LocalDate.now().plusDays(1);
		ModelAndView rezultat = new ModelAndView("dodavanjeTermina");
		rezultat.addObject("trening", trening);
		rezultat.addObject("sale", sale);
		//rezultat.addObject("now", now);
		
		return rezultat;
	}
	
	@PostMapping(value="/Create")
	public void create(@RequestParam Long salaId, @RequestParam(name="treningId", required=false) Long treningId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datum,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if(prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("administrator")) {
			response.sendRedirect(baseURL);
		}
		
		if (datum == null || salaId == null || treningId == null) {
    		response.sendRedirect(baseURL + "treninzi/details?id=" + treningId);
			return;
		}
		
		
		Trening t = treningService.findOne(treningId);
		Sala s = salaService.findOne(salaId);
		TerminTreninga termin = new TerminTreninga(s, t, datum);

		
		LocalDateTime datumOd = datum;
		System.out.println(datumOd);
		System.out.println(termin.getTrening().getTrajanje());
		LocalDateTime datumDo = datum.plusHours(termin.getTrening().getTrajanje().getHour());
		System.out.println(datumOd);

		
		
		List<TerminTreninga> termini = terminService.findTerminSala(salaId);
		List<LocalDateTime> terminTreningaa = new ArrayList<>();
		
		for(TerminTreninga tt : termini) {
			//System.out.println(tt.getTrening().getTrajanje());
			LocalDateTime datum2 = tt.getDatum();
			//System.out.println(datum2);
			LocalDateTime datumDo2 = datum2.plusHours(tt.getTrening().getTrajanje().getHour());
			terminTreningaa.add(datum2);
			terminTreningaa.add(datumDo2);
		}
		
		List<TerminTreninga> terminiSalaDatum = terminService.findSalaDatum(salaId, datumOd, datumDo, salaId, terminTreningaa.get(1), datumOd, terminTreningaa.get(1), datumDo);

		System.out.println("U ovoliko trening pocinje: " + terminTreningaa.get(0) + "   do ovoliko traje: " + terminTreningaa.get(1));

		
		boolean ans = terminiSalaDatum.isEmpty();

        if (ans == true) {
            System.out.println("NE postoje treninzi u datoj sali u tom terminu!");
    		terminService.save(termin);
    		response.sendRedirect(baseURL + "treninzi/details?id=" + treningId);
        }
        else {
            System.out.println("Postoje treninzi u datoj sali u tom terminu!");
            response.sendRedirect(baseURL + "treninzi/details?id=" + treningId);
			return;
        }
		
				
		
	}
	
	

	
}
