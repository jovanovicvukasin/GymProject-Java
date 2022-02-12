package com.ftn.Teretana.service;

import java.util.List;

import com.ftn.Teretana.model.TerminTreninga;

public interface TerminService {
	
	List<TerminTreninga> findTrening(Long id);
	List<TerminTreninga> findAll();
	TerminTreninga findOne(Long id);

}
