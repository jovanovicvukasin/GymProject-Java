package com.ftn.Teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
			Double cenaK = rs.getDouble(index++);
			Boolean aktivna = rs.getBoolean(index++);

			Long terminId = rs.getLong(index++);
			LocalDateTime datum = rs.getObject(index++, LocalDateTime.class);
			Integer kapacitet = rs.getInt(index++);
			
			
			Long korisnikId = rs.getLong(index++);
			String korisnickoIme = rs.getString(index++);
			Korisnik korisnik = new Korisnik(korisnikId, korisnickoIme);
			
			Long treningId = rs.getLong(index++);
			String naziv = rs.getString(index++);
			String trener = rs.getString(index++);
			Double cena = rs.getDouble(index++);
			
			Trening trening = treningDAO.findOne(treningId);//new Trening(treningId, naziv, trener, cena);

			
			TerminTreninga ttreninga = new TerminTreninga(terminId, trening, datum, kapacitet);


			Korpa korpa = new Korpa(korpaId, ttreninga, korisnik, cenaK, aktivna);
			return korpa;
		}
		
	}
	
	
	@Transactional
	@Override
	public int save(Korpa korpa) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO korpa (terminId, korisnikId, cena, aktivna) VALUES (?, ?, ?, ?)";

		return jdbcTemplate.update(sql, korpa.getTerminTreninga().getId(), korpa.getKorisnik().getId(), korpa.getTerminTreninga().getTrening().getCena(), korpa.isAktivna());
	}


	@Override
	public List<Korpa> findForOne(Long id) {
		// TODO Auto-generated method stub
		String sql = "SELECT k.id, k.cena, k.aktivna, " + 
				"tt.id, tt.datum, tt.kapacitet, " +
				"kor.id, kor.korisnickoIme, " +
				"t.id, t.naziv, t.trener, t.cena " +
				  "from korpa k " +
				  "LEFT JOIN termini tt ON k.terminId = tt.id " +
				  "LEFT JOIN korisnici kor ON k.korisnikId = kor.id " +
				  "LEFT JOIN treninzi t ON tt.treningId = t.id " +
		
				  "WHERE korisnikId = ? " +
				  "ORDER BY k.id";

		
		try {
			return jdbcTemplate.query(sql, new KorpaRowMapper(), id);

			
		}catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			 return null;
		}
		
	}


	@Override
	public Korpa findOne(Long id) {
		// TODO Auto-generated method stub
		String sql =   "SELECT k.id, k.cena, k.aktivna, " + 
				"tt.id, tt.datum, tt.kapacitet, " +
				"kor.id, kor.korisnickoIme, " +
				"t.id, t.naziv, t.trener, t.cena " +
				  "from korpa k " +
				  "LEFT JOIN termini tt ON k.terminId = tt.id " +
				  "LEFT JOIN korisnici kor ON k.korisnikId = kor.id " +
				  "LEFT JOIN treninzi t ON tt.treningId = t.id " +
				"WHERE k.id = ? " +
				"ORDER BY k.id";
		
		return jdbcTemplate.queryForObject(sql, new KorpaRowMapper(), id);
	}


	@Override
	public List<Korpa> findAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT k.id, k.cena, k.aktivna, " + 
				"tt.id, tt.datum, tt.kapacitet, " +
				"kor.id, kor.korisnickoIme, " +
				"t.id, t.naziv, t.trener, t.cena " +
				  "from korpa k " +
				  "LEFT JOIN termini tt ON k.terminId = tt.id " +
				  "LEFT JOIN korisnici kor ON k.korisnikId = kor.id " +
				  "LEFT JOIN treninzi t ON tt.treningId = t.id " +
			
				"ORDER BY k.id";
		return jdbcTemplate.query(sql, new KorpaRowMapper());
	}


	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM korpa WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}


	@Override
	public List<Korpa> find(Long terminId, Long korisnikId, Double cena, Boolean aktivna) {
		// TODO Auto-generated method stub
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT k.id, k.cena, k.aktivna, " + 
				"tt.id, tt.datum, tt.kapacitet, " +
				"kor.id, kor.korisnickoIme, " +
				"t.id, t.naziv, t.trener, t.cena " +
				  "from korpa k " +
				  "LEFT JOIN termini tt ON k.terminId = tt.id " +
				  "LEFT JOIN korisnici kor ON k.korisnikId = kor.id " +
				  "LEFT JOIN treninzi t ON tt.treningId = t.id";
				
			
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(terminId != null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("k.terminId = ?");
			imaArgumenata = true;
			listaArgumenata.add(terminId);
		}
		
		if(korisnikId != null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("k.korisnikId = ?");
			imaArgumenata = true;
			listaArgumenata.add(korisnikId);
		}
		
		if(cena != null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("k.cena = ?");
			imaArgumenata = true;
			listaArgumenata.add(cena);
		}
		
		if(aktivna != null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("k.aktivna = ?");
			imaArgumenata = true;
			listaArgumenata.add(aktivna);
		}
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY k.id";
		else
			sql=sql + " ORDER BY k.id";
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new KorpaRowMapper());
	}
	
	@Override
	public List<Korpa> findForOne(boolean aktivna, Long id) {
		// TODO Auto-generated method stub
		String sql = "SELECT k.id, k.cena, k.aktivna, " +
				"tt.id, tt.datum, tt.kapacitet,  " +
				"kor.id, kor.korisnickoIme, " +
				"t.id, t.naziv, t.trener, t.cena " +
				  "from korpa k " +
				  "LEFT JOIN termini tt ON k.terminId = tt.id " +
				  "LEFT JOIN korisnici kor ON k.korisnikId = kor.id " +
				  "LEFT JOIN treninzi t ON tt.treningId = t.id " +
		
				  "WHERE k.aktivna = ? and k.terminId = ?";

		
		try {
			return jdbcTemplate.query(sql, new KorpaRowMapper(), aktivna, id);

			
		}catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			 return null;
		}
		
	}


	@Override
	public int update(Korpa korpa) {
		// TODO Auto-generated method stub
		String sql = "UPDATE korpa SET terminId = ?, korisnikId = ?, cena = ?, aktivna = ? WHERE id  = ?";
		return jdbcTemplate.update(sql, korpa.getTerminTreninga().getId(), korpa.getKorisnik().getId(), korpa.getCena(), korpa.isAktivna(), korpa.getId());
	
	}

}
