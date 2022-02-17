package com.ftn.Teretana.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ftn.Teretana.model.TerminTreninga;

public interface TerminService {
	
	List<TerminTreninga> findTrening(Long id, LocalDateTime datum);
	List<TerminTreninga> findAll();
	TerminTreninga findOne(Long id);
	TerminTreninga save(TerminTreninga terminTreninga);

}
