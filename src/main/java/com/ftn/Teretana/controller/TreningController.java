package com.ftn.Teretana.controller;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.Teretana.model.Korisnik;
import com.ftn.Teretana.model.TipTreninga;
import com.ftn.Teretana.model.Trening;
import com.ftn.Teretana.service.TipTreningaService;
import com.ftn.Teretana.service.TreningService;

@Controller
@RequestMapping(value="/")
public class TreningController implements ServletContextAware{
	
	@Autowired
	private TreningService treningService;
	
	@Autowired
	private TipTreningaService tipTreningaService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL;
	
	/** inicijalizacija podataka za kontroler */
	@PostConstruct
	public void init() {	
		baseURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 
	
	@GetMapping
	public ModelAndView index(
			@RequestParam(required=false) String naziv,
			@RequestParam(required=false) String trener,
			@RequestParam(required=false) Double cenaOd, 
			@RequestParam(required=false) Double cenaDo,
			@RequestParam(required=false) Long tipTreningaId,
			@RequestParam(required=false) String vrstaTreninga,
			@RequestParam(required=false) String nivoTreninga) {
		
		if(naziv != null && naziv.trim().equals(""))
			naziv = null;
		
		if(trener != null && trener.trim().equals(""))
			trener = null;
		
		if(vrstaTreninga != null && vrstaTreninga.trim().equals(""))
			vrstaTreninga = null;
		
		if(nivoTreninga != null && nivoTreninga.trim().equals(""))
			nivoTreninga = null;
		
		List<Trening> treninzi = treningService.find(naziv, tipTreningaId, trener, cenaOd, cenaDo, vrstaTreninga, nivoTreninga);
		
		
		//List<Trening> treninzi = treningService.findAll();
		List<TipTreninga> tipoviTreninga = tipTreningaService.findAll();
		ModelAndView rezultat = new ModelAndView("index");
		rezultat.addObject("treninzi", treninzi);
		rezultat.addObject("tipoviTreninga", tipoviTreninga);
		return rezultat;
	}
	
	@GetMapping(value="/treninzi/details")
	public ModelAndView details(@RequestParam Long id) throws IOException {

		Trening trening = treningService.findOne(id);
		List<TipTreninga> tipoviTreninga = tipTreningaService.findAll();
		List<String> vrstaTreninga = new ArrayList<String>();
		vrstaTreninga.add("pojedinacni");
		vrstaTreninga.add("grupni");
		List<String> nivoTreninga = new ArrayList<>();
		nivoTreninga.add("amaterski");
		nivoTreninga.add("srednji");
		nivoTreninga.add("napredni");
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("trening");// naziv template-a
		rezultat.addObject("trening", trening); // naziv template-a
		rezultat.addObject("tipoviTreninga", tipoviTreninga);
		rezultat.addObject("vrsteTreninga", vrstaTreninga);
		rezultat.addObject("nivoiTreninga", nivoTreninga);



		
		return rezultat;// prosleđivanje zahteva zajedno sa podacima template-u

	}
	
	@PostMapping(value="/trening/edit")
	public void edit(@RequestParam Long id, @RequestParam String naziv, @RequestParam String trener,
			@RequestParam(name="tipTreningaId", required=false) Long[] tipTreningaId,
			@RequestParam String opis, @RequestParam Double cena, 
			@RequestParam String vrstaTreninga, @RequestParam String nivoTreninga, 
			@RequestParam @DateTimeFormat(iso = ISO.TIME) LocalTime trajanje,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if(prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("administrator")) {
			response.sendRedirect(baseURL);
			return;
		}
		
		
		
		//validacija
		Trening trening = treningService.findOne(id);
		if(trening == null) {
			response.sendRedirect(baseURL);
			return;
		}
		
		if (naziv == null || naziv.equals("") || trener == null || trener.equals("") || opis == null || opis.equals("") || 
				cena == 0 || cena.equals(null) || trajanje == null || tipTreningaId == null || nivoTreninga == null || vrstaTreninga == null) {
			response.sendRedirect(baseURL + "treninzi/details?id=" + id);
			return;
		}
		
		//izmena
		trening.setNaziv(naziv);
		trening.setTrener(trener);
		trening.setOpis(opis);
		trening.setCena(cena);
		trening.setVrstaTreninga(vrstaTreninga);
		trening.setNivoTreninga(nivoTreninga);
		trening.setTrajanje(trajanje);
		trening.setTipTreninga(tipTreningaService.find(tipTreningaId));
		treningService.edit(trening);
		
		response.sendRedirect(baseURL);
		
	}
	
	@GetMapping(value="/trening/create")
	public ModelAndView create(HttpSession session, HttpServletResponse response) throws IOException{
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if(prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("administrator")) {
			response.sendRedirect(baseURL);
			return null;
		}
		List<TipTreninga> tipoviTreninga = tipTreningaService.findAll();
		List<String> vrstaTreninga = new ArrayList<String>();
		vrstaTreninga.add("pojedinacni");
		vrstaTreninga.add("grupni");
		List<String> nivoTreninga = new ArrayList<>();
		nivoTreninga.add("amaterski");
		nivoTreninga.add("srednji");
		nivoTreninga.add("napredni");
		
		ModelAndView rezultat = new ModelAndView("dodavanjeTreninga");
		rezultat.addObject("tipoviTreninga", tipoviTreninga);
		rezultat.addObject("vrsteTreninga", vrstaTreninga);
		rezultat.addObject("nivoiTreninga", nivoTreninga);
		
		return rezultat;
		
	}
	
	@PostMapping(value="trening/create")
	public void create(@RequestParam String naziv, @RequestParam String trener,
			@RequestParam(name="tipTreningaId", required=false) Long[] tipTreningaId,
			@RequestParam String opis, @RequestParam Double cena, 
			@RequestParam String vrstaTreninga, @RequestParam String nivoTreninga, 
			@RequestParam @DateTimeFormat(iso = ISO.TIME) LocalTime trajanje,
			HttpSession session, HttpServletResponse response) throws IOException{
		
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if(prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals("administrator")) {
			response.sendRedirect(baseURL);
		}
		if (naziv == null || naziv.equals("") || trener == null || trener.equals("") || opis == null || opis.equals("") || 
				cena == 0 || cena.equals(null) || trajanje == null || tipTreningaId == null || nivoTreninga == null || vrstaTreninga == null) {
			response.sendRedirect(baseURL + "trening/create");
			return;
		}
		
		Trening trening = new Trening(naziv, trener, opis, cena, vrstaTreninga, nivoTreninga, trajanje);
		trening.setTipTreninga(tipTreningaService.find(tipTreningaId));
		treningService.save(trening);
		
		response.sendRedirect(baseURL);
		
	}

	
	
}
