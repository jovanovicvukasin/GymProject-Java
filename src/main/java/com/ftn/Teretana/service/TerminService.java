package com.ftn.Teretana.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ftn.Teretana.model.TerminTreninga;

public interface TerminService {
	
	List<TerminTreninga> findTrening(Long id, LocalDateTime datum);
	List<TerminTreninga> findTerminSala(Long id);
	List<TerminTreninga> findAll();
	TerminTreninga findOne(Long id);
	TerminTreninga save(TerminTreninga terminTreninga);
	List<TerminTreninga> findSalaDatum(Long id, LocalDateTime datumOd, LocalDateTime datumDo, Long id1, LocalDateTime datumDo1, LocalDateTime datumOd1, LocalDateTime datumDo2, LocalDateTime datumOd2);

}
