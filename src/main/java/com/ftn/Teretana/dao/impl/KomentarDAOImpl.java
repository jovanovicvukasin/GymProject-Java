package com.ftn.Teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.Teretana.dao.KomentarDAO;
import com.ftn.Teretana.model.Komentar;
import com.ftn.Teretana.model.Korisnik;
import com.ftn.Teretana.model.Trening;

@Repository
public class KomentarDAOImpl implements KomentarDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class KomentarRowMapper implements RowMapper<Komentar> {

		@Override
		public Komentar mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			int index = 1;
			Long komentarId = rs.getLong(index++);
			String tekst = rs.getString(index++);
			Integer ocena = rs.getInt(index++);
			LocalDate datum = rs.getObject(index++, LocalDate.class);
			String statusKomentara = rs.getString(index++);
			
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

			Long treningId = rs.getLong(index++);
			String naziv = rs.getString(index++);
			Float prosecnaOcena = rs.getFloat(index++);

			Trening trening = new Trening(treningId, naziv, prosecnaOcena);
			
			Komentar kom = new Komentar(komentarId, tekst, ocena, datum, korisnik, trening, statusKomentara);
			return kom;
		}
		
	}

	@Override
	public List<Komentar> findAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT kom.id, kom.tekst, kom.ocena, kom.datum, kom.statusKomentara, kor.id, kor.korisnickoIme, kor.email, " +
				"kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumIVremeRegistracije, kor.uloga, " +
				"kor.blokiran, t.id, t.naziv, t.prosecnaOcena FROM komentari kom " +
				"LEFT JOIN treninzi t ON kom.treningId = t.id " +
				"LEFT JOIN korisnici kor ON kom.korisnikId = kor.id " +
				"ORDER BY kom.id";
		return jdbcTemplate.query(sql, new KomentarRowMapper());
	}

	@Override
	public int save(Komentar komentar) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO komentari (tekst, ocena, datum, korisnikId, treningId, statusKomentara) VALUES (?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, komentar.getTekst(), komentar.getOcena(), komentar.getDatum(), komentar.getKorisnik().getId(), komentar.getTrening().getId(), komentar.getStatusKomentara());
		}

	@Override
	public int update(Komentar komentar) {
		// TODO Auto-generated method stub
		String sql = "UPDATE komentari SET statusKomentara = ? WHERE id  = ?";
		return jdbcTemplate.update(sql, komentar.getStatusKomentara(), komentar.getId());
	
	}

	@Override
	public Komentar findOne(Long id) {
		// TODO Auto-generated method stub
		String sql = "SELECT kom.id, kom.tekst, kom.ocena, kom.datum, kom.statusKomentara, kor.id, kor.korisnickoIme, kor.email, " +
				"kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumIVremeRegistracije, kor.uloga, " +
				"kor.blokiran, t.id, t.naziv, t.prosecnaOcena FROM komentari kom " +
				"LEFT JOIN treninzi t ON kom.treningId = t.id " +
				"LEFT JOIN korisnici kor ON kom.korisnikId = kor.id " +
				"WHERE kom.id = ? " +
				"ORDER BY kom.id";
	
		return jdbcTemplate.queryForObject(sql, new KomentarRowMapper(), id);
	}

	@Override
	public List<Komentar> find(String tekst, Integer ocena, LocalDate datum, Long korisnikId, Long treningId,
			String statusKomentara) {
		// TODO Auto-generated method stub
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT kom.id, kom.tekst, kom.ocena, kom.datum, kom.statusKomentara, kor.id, kor.korisnickoIme, kor.email, " +
				"kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumIVremeRegistracije, kor.uloga, " +
				"kor.blokiran, t.id, t.naziv, t.prosecnaOcena FROM komentari kom " +
				"LEFT JOIN treninzi t ON kom.treningId = t.id " +
				"LEFT JOIN korisnici kor ON kom.korisnikId = kor.id ";
		
		StringBuffer where = new StringBuffer(" WHERE ");
		boolean imaArg = false;
		
		if(tekst != null) {
			if(imaArg)
				where.append(" AND ");
			where.append("kom.tekst = ?");
			imaArg = true;
			listaArgumenata.add(tekst);
		}
		
		if(ocena != null) {
			if(imaArg)
				where.append(" AND ");
			where.append("kom.ocena = ?");
			imaArg = true;
			listaArgumenata.add(korisnikId);
		}
		
		if(datum != null) {
			if(imaArg)
				where.append(" AND ");
			where.append("kom.datum = ?");
			imaArg = true;
			listaArgumenata.add(datum);
		}
		
		if(korisnikId != null) {
			if(imaArg)
				where.append(" AND ");
			where.append("kom.korisnikId = ?");
			imaArg = true;
			listaArgumenata.add(korisnikId);
		}
		
		if(treningId != null) {
			if(imaArg)
				where.append(" AND ");
			where.append("kom.treningId = ?");
			imaArg = true;
			listaArgumenata.add(treningId);
		}
		
		if(statusKomentara != null) {
			if(imaArg)
				where.append(" AND ");
			where.append("kom.statusKomentara = ?");
			imaArg = true;
			listaArgumenata.add(statusKomentara);
		}
		
		if(imaArg)
			sql=sql + where.toString()+" ORDER BY kom.id";
		else
			sql=sql + " ORDER BY kom.id";
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new KomentarRowMapper());
	
	}

}
