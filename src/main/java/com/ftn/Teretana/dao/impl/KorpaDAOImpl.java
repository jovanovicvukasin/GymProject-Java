package com.ftn.Teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.Teretana.dao.KorpaDAO;
import com.ftn.Teretana.dao.TreningDAO;
import com.ftn.Teretana.model.Korisnik;
import com.ftn.Teretana.model.Korpa;
import com.ftn.Teretana.model.TerminTreninga;
import com.ftn.Teretana.model.TipTreninga;
import com.ftn.Teretana.model.Trening;

@Repository
public class KorpaDAOImpl implements KorpaDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TreningDAO treningDAO;
	
	private class KorpaRowMapper implements RowMapper<Korpa> {

		@Override
		public Korpa mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			int index = 1;
			Long korpaId = rs.getLong(index++);
			Boolean aktivna = rs.getBoolean(index++);

			Long terminId = rs.getLong(index++);
			LocalDateTime datum = rs.getObject(index++, LocalDateTime.class);
			
			
			Long korisnikId = rs.getLong(index++);
			String korisnickoIme = rs.getString(index++);
			Korisnik korisnik = new Korisnik(korisnikId, korisnickoIme);
			
			Long treningId = rs.getLong(index++);
			String naziv = rs.getString(index++);
			String trener = rs.getString(index++);
			Double cena = rs.getDouble(index++);
			
			Long tipId = rs.getLong(index++);
			String ime = rs.getString(index++);
			String opis = rs.getString(index++);
			
			
			
			Trening trening = treningDAO.findOne(treningId);//new Trening(treningId, naziv, trener, cena);

			
			TerminTreninga ttreninga = new TerminTreninga(terminId, trening, datum);


			Korpa korpa = new Korpa(korpaId, ttreninga, korisnik, aktivna);
			return korpa;
		}
		
	}
	
	
	@Transactional
	@Override
	public int save(Korpa korpa) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO korpa (terminId, korisnikId, aktivna) VALUES (?, ?, ?)";

		return jdbcTemplate.update(sql, korpa.getTerminTreninga().getId(), korpa.getKorisnik().getId(), korpa.isAktivna());
	}


	@Override
	public List<Korpa> findForOne(Long id) {
		// TODO Auto-generated method stub
		String sql = "SELECT k.id, k.aktivna, " + 
				"tt.id, tt.datum, " +
				"kor.id, kor.korisnickoIme, " +
				"t.id, t.naziv, t.trener, t.cena, " +
				"tip.id, tip.ime, tip.opis " +
				  "from korpa k " +
				  "LEFT JOIN termini tt ON k.terminId = tt.id " +
				  "LEFT JOIN korisnici kor ON k.korisnikId = kor.id " +
				  "LEFT JOIN treninzi t ON tt.treningId = t.id " +
				  "LEFT JOIN treningTipTreninga ttt ON ttt.treningId = t.id " +
				  "LEFT JOIN tipoviTreninga tip ON ttt.tipTreningaId = tip.id " +
				  "WHERE korisnikId = ? " +
				  "ORDER BY k.id";
		
		return jdbcTemplate.query(sql, new KorpaRowMapper(), id);
	}


	@Override
	public Korpa findOne(Long id) {
		// TODO Auto-generated method stub
		String sql =   "SELECT k.id, k.aktivna, " + 
				"tt.id, tt.datum, " +
				"kor.id, kor.korisnickoIme, " +
				"t.id, t.naziv, t.trener, t.cena, " +
				"tip.id, tip.ime, tip.opis " +
				  "from korpa k " +
				  "LEFT JOIN termini tt ON k.terminId = tt.id " +
				  "LEFT JOIN korisnici kor ON k.korisnikId = kor.id " +
				  "LEFT JOIN treninzi t ON tt.treningId = t.id " +
				  "LEFT JOIN treningTipTreninga ttt ON ttt.treningId = t.id " +
				  "LEFT JOIN tipoviTreninga tip ON ttt.tipTreningaId = tip.id " +
				"WHERE k.id = ? " +
				"ORDER BY k.id";
		
		return jdbcTemplate.queryForObject(sql, new KorpaRowMapper(), id);
	}


	@Override
	public List<Korpa> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM korpa WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
