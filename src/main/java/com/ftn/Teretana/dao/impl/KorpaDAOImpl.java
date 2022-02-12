package com.ftn.Teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.Teretana.dao.KorpaDAO;
import com.ftn.Teretana.model.Korpa;

@Repository
public class KorpaDAOImpl implements KorpaDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Transactional
	@Override
	public int save(Korpa korpa) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO korpa (treningId, terminId, korisnikId, aktivna) VALUES (?, ?, ?, ?)";

		return jdbcTemplate.update(sql, korpa.getTrening().getId(), korpa.getTerminTreninga().getId(), korpa.getKorisnik().getId(), korpa.isAktivna());
	}

}
