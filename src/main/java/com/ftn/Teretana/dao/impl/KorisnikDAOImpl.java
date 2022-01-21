package com.ftn.Teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.Teretana.dao.KorisnikDAO;
import com.ftn.Teretana.model.Korisnik;

@Repository
public class KorisnikDAOImpl implements KorisnikDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class KorisnikRowMapper implements RowMapper<Korisnik> {

		@Override
		public Korisnik mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			String korisnickoIme = rs.getString(index++);
			String lozinka = rs.getString(index++);
			String email = rs.getString(index++);
			String ime = rs.getString(index++);
			String prezime = rs.getString(index++);
			LocalDate datumRodjenja = rs.getObject(index++, LocalDate.class);
			String adresa = rs.getString(index++);
			String brojTelefona = rs.getString(index++);
			LocalDateTime datumIVremeRegistracije = rs.getObject(index++, LocalDateTime.class);
			String uloga = rs.getString(index++);
			Boolean blokiran = rs.getBoolean(index++);

			Korisnik korisnik = new Korisnik(id, korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumIVremeRegistracije, uloga, blokiran);
			return korisnik;
		}
	}

	@Override
	public void save(Korisnik korisnik) {
		// TODO Auto-generated method stub
		
		String sql = "INSERT INTO korisnici (korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumIVremeRegistracije, uloga, blokiran) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, korisnik.getKorisnickoIme(), korisnik.getLozinka(), korisnik.getEmail(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getDatumRodjenja().plusDays(1), korisnik.getAdresa(), korisnik.getBrojTelefona(), korisnik.getDatumIVremeRegistracije(), korisnik.getUloga(), korisnik.isBlokiran());
	
		
	}

	@Override
	public List<Korisnik> find(String korisnickoIme, String email, String ime, String prezime, LocalDate datumRodjenja,
			String adresa, String brojTelefona, LocalDateTime datumIVremeRegistracije, String uloga, Boolean blokiran) {
		// TODO Auto-generated method stub
		return null;
	}

}
