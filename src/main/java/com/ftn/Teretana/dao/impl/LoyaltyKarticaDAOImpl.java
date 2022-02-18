package com.ftn.Teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.Teretana.dao.LoyaltyKarticaDAO;
import com.ftn.Teretana.model.Korisnik;
import com.ftn.Teretana.model.LoyaltyKartica;

@Repository
public class LoyaltyKarticaDAOImpl implements LoyaltyKarticaDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	private class KarticaRowMapper implements RowMapper<LoyaltyKartica> {

		@Override
		public LoyaltyKartica mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long karticaId = rs.getLong(index++);
			Integer popust = rs.getInt(index++);
			Integer brojPoena = rs.getInt(index++);
			Boolean odobrena = rs.getBoolean(index++);

			Long korisnikId = rs.getLong(index++);
			String korisnickoIme = rs.getString(index++);
			String email = rs.getString(index++);
			String ime = rs.getString(index++);
			String prezime = rs.getString(index++);
			LocalDate datumRodjenja = rs.getObject(index++, LocalDate.class);
			String adresa = rs.getString(index++);
			String brojTelefona = rs.getString(index++);
			LocalDateTime datumIVremeRegistracije = rs.getObject(index++, LocalDateTime.class);
			String uloga = rs.getString(index++);
			Boolean blokiran = rs.getBoolean(index++);

			Korisnik korisnik = new Korisnik(korisnikId, korisnickoIme, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumIVremeRegistracije, uloga, blokiran);

			LoyaltyKartica kartica = new LoyaltyKartica(karticaId, popust, brojPoena, korisnik, odobrena);
			return kartica;
		}
	}
	
	@Override
	public int save(LoyaltyKartica kartica) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO kartice (popust, brojPoena, korisnikId, odobrena) VALUES (?, ?, ?, ?)";

		return jdbcTemplate.update(sql, kartica.getPopust(), kartica.getBrojPoena(), kartica.getKorisnik().getId(), kartica.isOdobrena());

	}

	@Override
	public int update(LoyaltyKartica kartica) {
		// TODO Auto-generated method stub
		String sql = "UPDATE kartice SET popust = ?, brojPoena = ?, korisnikId = ?, odobrena = ? WHERE id  = ?";
		return jdbcTemplate.update(sql, kartica.getPopust(), kartica.getBrojPoena(), kartica.getKorisnik().getId(), kartica.isOdobrena(), kartica.getId());
	
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM kartice WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public LoyaltyKartica findOne(Long id) {
		// TODO Auto-generated method stub
		String sql = "SELECT k.id, k.popust, k.brojPoena, k.odobrena, kor.id, kor.korisnickoIme, kor.email, kor.ime, " +
				"kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumIVremeRegistracije, kor.uloga, kor.blokiran FROM kartice k " +
				"LEFT JOIN korisnici kor ON k.korisnikId = kor.id " +
				"WHERE k.id = ? " + 
				"ORDER BY k.id";
		
		return jdbcTemplate.queryForObject(sql, new KarticaRowMapper(), id);

	}


	@Override
	public List<LoyaltyKartica> findAll() {
		// TODO Auto-generated method stub
		String sql= "SELECT k.id, k.popust, k.brojPoena, k.odobrena, kor.id, kor.korisnickoIme, kor.email, kor.ime, " +
				"kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumIVremeRegistracije, kor.uloga, kor.blokiran FROM kartice k " +
				"LEFT JOIN korisnici kor ON k.korisnikId = kor.id " +
				"ORDER BY k.id";
		return jdbcTemplate.query(sql, new KarticaRowMapper());
	}

	@Override
	public LoyaltyKartica findKorisnik(Long id) {
		// TODO Auto-generated method stub
		 String sql = "SELECT k.id, k.popust, k.brojPoena, k.odobrena, kor.id, kor.korisnickoIme, kor.email, kor.ime, " +
			"kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumIVremeRegistracije, kor.uloga, kor.blokiran FROM kartice k " +
			"LEFT JOIN korisnici kor ON k.korisnikId = kor.id " +
			"WHERE korisnikId = ? " + 
			"ORDER BY k.id";
		 
		 try {
			 
				return jdbcTemplate.queryForObject(sql, new KarticaRowMapper(), id);

			 
		 }catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			 return null;
		}
		 
	}

}
