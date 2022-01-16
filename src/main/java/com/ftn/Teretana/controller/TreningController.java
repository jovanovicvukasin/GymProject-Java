package com.ftn.Teretana.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

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
}
