package com.ftn.Teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

	@Override
	public Korisnik findOne(String korisnickoIme) {
		// TODO Auto-generated method stub
		try {
			String sql = "SELECT id, korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumIVremeRegistracije, uloga, blokiran FROM korisnici WHERE korisnickoIme = ?";
			return jdbcTemplate.queryForObject(sql, new KorisnikRowMapper(), korisnickoIme);
		}catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public Korisnik findOne(String korisnickoIme, String lozinka) {
		// TODO Auto-generated method stub
		try {
			
			String sql = "SELECT id, korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumIVremeRegistracije, uloga, blokiran FROM korisnici WHERE korisnickoIme = ? AND lozinka = ?";
			return jdbcTemplate.queryForObject(sql, new KorisnikRowMapper(), korisnickoIme, lozinka);
		}catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public void edit(Korisnik korisnik) {
		// TODO Auto-generated method stub
		String sql = "UPDATE korisnici SET uloga = ?, blokiran = ? WHERE korisnickoIme = ?";
		jdbcTemplate.update(sql, korisnik.getUloga(), korisnik.isBlokiran(), korisnik.getKorisnickoIme());
	
	}
	
	@Override
	public void editProfile(Korisnik korisnik) {
		// TODO Auto-generated method stub
		String sql = "UPDATE korisnici SET lozinka = ?, email = ?, ime = ?, prezime = ?, datumRodjenja = ?, adresa = ?, brojTelefona = ? WHERE korisnickoIme = ?";
		jdbcTemplate.update(sql, korisnik.getLozinka(), korisnik.getEmail(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getDatumRodjenja(), korisnik.getAdresa(), korisnik.getBrojTelefona(), korisnik.getKorisnickoIme());
	}

	@Override
	public List<Korisnik> find(String korisnickoIme, String uloga) {
		// TODO Auto-generated method stub
		
		ArrayList<Object> lista = new ArrayList<Object>();
		
		String sql = "SELECT * FROM korisnici";
		
		StringBuffer where = new StringBuffer(" WHERE ");
		boolean imaArg = false;
		
		if(korisnickoIme != null) {
			korisnickoIme = "%" + korisnickoIme + "%";
			if(imaArg)
				where.append(" AND ");
			where.append("korisnickoIme LIKE ?");
			imaArg = true;
			lista.add(korisnickoIme);
		}
		
		if(uloga != null) {
			if(imaArg)
				where.append(" AND ");
			where.append("uloga LIKE ?");
			imaArg = true;
			lista.add(uloga);
		}
		
		if(imaArg) {
			sql=sql + where.toString() + " ORDER BY id";
		}else {
			sql=sql + " ORDER BY id";
			
		}
		
		List<Korisnik> korisnici = jdbcTemplate.query(sql, lista.toArray(), new KorisnikRowMapper());
		
		return korisnici;
	}

	

}
