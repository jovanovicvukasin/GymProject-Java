package com.ftn.Teretana.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.Teretana.dao.KorpaDAO;
import com.ftn.Teretana.dao.ZakazivanjeDAO;
import com.ftn.Teretana.model.Korisnik;
import com.ftn.Teretana.model.Korpa;
import com.ftn.Teretana.model.Zakazivanje;

@Repository
public class ZakazivanjeDAOImpl implements ZakazivanjeDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private KorpaDAO korpaDAO;
	
	private class ZakazivanjeRowMapper implements RowMapper<Zakazivanje>{

		@Override
		public Zakazivanje mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			int index = 1;
			Long zakazanId = rs.getLong(index++);
			Double ukupnaCena = rs.getDouble(index++);
			LocalDateTime datumZakazivanja = rs.getObject(index++, LocalDateTime.class);
			Integer ukupanBroj = rs.getInt(index++);
			
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
			Zakazivanje zakazivanje = new Zakazivanje(zakazanId, ukupnaCena, datumZakazivanja, korisnik, ukupanBroj);
			return zakazivanje;
		}
		
	}
	
	private class ZakazivanjeKorpaRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Zakazivanje> zakazivanja = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet rs) throws SQLException {
			// TODO Auto-generated method stub
			
			int index = 1;
			Long zakazanId = rs.getLong(index++);
			Double ukupnaCena = rs.getDouble(index++);
			LocalDateTime datumZakazivanja = rs.getObject(index++, LocalDateTime.class);
			Integer ukupanBroj = rs.getInt(index++);
			
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
			
			Zakazivanje zakazivanje = zakazivanja.get(zakazanId);
			if(zakazivanje == null) {
				zakazivanje = new Zakazivanje(zakazanId, ukupnaCena, datumZakazivanja, korisnik, ukupanBroj);
				zakazivanja.put(zakazivanje.getId(), zakazivanje);
			}
			
			Long korpaId = rs.getLong(index++);
			Double cena = rs.getDouble(index++);
			Korpa korpa = new Korpa(korpaId, cena);
			zakazivanje.getKorpe().add(korpa);
		}
		
		public List<Zakazivanje> getZakazivanje() {
			return new ArrayList<>(zakazivanja.values());
		}
		
	}
	
	@Transactional
	@Override
	public int save(Zakazivanje zakazivanje) {
		// TODO Auto-generated method stub
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				String sql = "INSERT INTO zakazani (ukupnaCena, datumZakazivanja, korisnikId, ukupanBroj) VALUES (?, ?, ?, ?)";

				PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setDouble(index++, zakazivanje.getUkupnaCena());
				preparedStatement.setObject(index++, zakazivanje.getDatumZakazivanja());
				preparedStatement.setObject(index++, zakazivanje.getKorisnik().getId());
				preparedStatement.setInt(index++, zakazivanje.getUkupanBroj());

				return preparedStatement;
			}
		};
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		if (uspeh) {
			String sql = "INSERT INTO zakazaniKorpa (zakazaniId, korpaId) VALUES (?, ?)";
			for (Korpa itKorpa : zakazivanje.getKorpe()) {	
				uspeh = uspeh && jdbcTemplate.update(sql, keyHolder.getKey(), itKorpa.getId()) == 1;
			}
		}
		
		return uspeh?1:0;
	}

	@Override
	public Zakazivanje findOne(Long id) {
		String sql = "SELECT z.id, z.ukupnaCena, z.datumZakazivanja, z.ukupanBroj, " +
				"kor.id, kor.korisnickoIme, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumIVremeRegistracije, kor.uloga, kor.blokiran, " +
				"k.id, k.terminId, k.korisnikId, k.cena, k.aktivna FROM zakazani z " +
				"LEFT JOIN zakazaniKorpa zk ON zk.zakazaniId = z.id " +
				"LEFT JOIN korpa k ON zk.korpaId = k.id " +
				"LEFT JOIN korisnici kor ON z.korisnikId = kor.id " +
				"WHERE z.id = ? " +
				"ORDER BY z.id";
		
		ZakazivanjeKorpaRowCallBackHandler rowCallBackHandler = new ZakazivanjeKorpaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallBackHandler, id);
		return rowCallBackHandler.getZakazivanje().get(0);
	}

	@Override
	public List<Zakazivanje> find(Long korpaId, Double ukupnaCena, LocalDateTime datumZakazivanja, Long korisnikId,
			Integer ukupanBroj) {
		// TODO Auto-generated method stub
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT z.id, z.ukupnaCena, z.datumZakazivanja, z.ukupanBroj, " +
				"kor.id, kor.korisnickoIme, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumIVremeRegistracije, kor.uloga, kor.blokiran " +
				"FROM zakazani z " +
				"LEFT JOIN korisnici kor ON z.korisnikId = kor.id ";
		
		StringBuffer where = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(ukupnaCena != null) {
			if(imaArgumenata)
				where.append(" AND ");
			where.append("z.ukupnaCena LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(ukupnaCena);
		}
		
		if(datumZakazivanja != null) {
			if(imaArgumenata)
				where.append(" AND ");
			where.append("z.datumZakazivanja LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(datumZakazivanja);
		}
		
		if(korisnikId != null) {
			if(imaArgumenata)
				where.append(" AND ");
			where.append("z.korisnikId LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(korisnikId);
		}
		
		if(ukupanBroj != null) {
			if(imaArgumenata)
				where.append(" AND ");
			where.append("z.ukupanBroj LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(ukupanBroj);
		}
		
		if(imaArgumenata)
			sql=sql + where.toString() + " ORDER BY z.id";
		else
			sql=sql + " ORDER BY z.id";
		
		List<Zakazivanje> zakazivanja = jdbcTemplate.query(sql, listaArgumenata.toArray(), new ZakazivanjeRowMapper());
		for(Zakazivanje z : zakazivanja) {
			z.setKorpe(findZakazivanjeKorpa(z.getId(), null));
		}
		
		if(korpaId != null) {
			for(Iterator<Zakazivanje> iterator = zakazivanja.iterator(); iterator.hasNext();) {
				Zakazivanje za = iterator.next();
				boolean zaBrisanje = true;
				for(Korpa k : za.getKorpe()) {
					if(k.getId() == korpaId) {
						zaBrisanje = false;
						break;
					}
				}
				if(zaBrisanje)
					iterator.remove();
			}
		}
		
		return zakazivanja;
	}
	
	private List<Korpa> findZakazivanjeKorpa(Long zakazaniId, Long korpaId) {
		
		List<Korpa> korpaZakazivanje = new ArrayList<Korpa>();
		
		ArrayList<Object> listaArg = new ArrayList<Object>();
		
		String sql = "SELECT zk.zakazaniId, zk.korpaId FROM zakazaniKorpa zk ";
	
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(zakazaniId != null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("zk.zakazaniId = ?");
			imaArgumenata = true;
			listaArg.add(zakazaniId);
		}
		
		if(korpaId != null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("zk.korpaId = ?");
			imaArgumenata = true;
			listaArg.add(korpaId);
		}
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY zk.zakazaniId";
		else
			sql=sql + " ORDER BY zk.korpaId";
		
		List<Long[]> zakazivanjeKorpa = jdbcTemplate.query(sql, listaArg.toArray(), new ZakazivanjeKorpaRowMapper());
		
		for(Long[] zk : zakazivanjeKorpa) {
			korpaZakazivanje.add(korpaDAO.findOne(zk[1]));
		}
		return korpaZakazivanje;
		
	
	}
	
	private class ZakazivanjeKorpaRowMapper implements RowMapper<Long []> {

		@Override
		public Long[] mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			int index = 1;
			Long zakazaniId = rs.getLong(index++);
			Long korpaId = rs.getLong(index++);
			
			Long [] zakazivanjeKorpa = {zakazaniId, korpaId};
			return zakazivanjeKorpa;
		}
		
	}

	@Override
	public List<Zakazivanje> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
