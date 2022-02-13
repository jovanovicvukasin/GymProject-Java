package com.ftn.Teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
	
	private class KorpaRowMapper implements RowMapper<Korpa> {

		@Override
		public Korpa mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			int index = 1;
			
			
			return null;
		}
		
	}
	
	
	@Transactional
	@Override
	public int save(Korpa korpa) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO korpa (treningId, terminId, korisnikId, aktivna) VALUES (?, ?, ?, ?)";

		return jdbcTemplate.update(sql, korpa.getTrening().getId(), korpa.getTerminTreninga().getId(), korpa.getKorisnik().getId(), korpa.isAktivna());
	}


	@Override
	public List<Korpa> findAll() {
		// TODO Auto-generated method stub
		String sql = "";
		return null;
	}


	@Override
	public Korpa findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
