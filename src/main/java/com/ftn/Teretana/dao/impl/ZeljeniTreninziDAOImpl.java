package com.ftn.Teretana.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.ftn.Teretana.dao.TreningDAO;
import com.ftn.Teretana.dao.ZeljeniTreninziDAO;
import com.ftn.Teretana.model.Korisnik;
import com.ftn.Teretana.model.Trening;
import com.ftn.Teretana.model.ZeljeniTreninzi;
import com.mysql.cj.xdevapi.Statement;

@Repository
public class ZeljeniTreninziDAOImpl implements ZeljeniTreninziDAO {

	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	@Autowired
	private TreningDAO treningDAO;
	
	private class ZeljeniTreninziTreningRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, ZeljeniTreninzi> zelje = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet rs) throws SQLException {
			int index = 1;
			Long listaZeljaId = rs.getLong(index++);
			
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
			
			ZeljeniTreninzi zelja = zelje.get(listaZeljaId);
			if (zelja == null) {
				zelja = new ZeljeniTreninzi(listaZeljaId, korisnik);
				zelje.put(zelja.getId(), zelja);
			}
			
			Long treningid = rs.getLong(index++);
			String naziv = rs.getString(index++);
			String trener = rs.getString(index++);
			String opis = rs.getString(index++);
			String slika = rs.getString(index++);
			Double cena = rs.getDouble(index++);
			String vrstaTreninga = rs.getString(index++);
			String nivoTreninga = rs.getString(index++);
			LocalTime trajanje = rs.getObject(index++, LocalTime.class);
			Float prosecnaOcena = rs.getFloat(index++);

			Trening trening = new Trening(treningid, naziv, trener, opis, slika, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena);
			zelja.getTrening().add(trening);
			
		}
		public List<ZeljeniTreninzi> getZelje() {
			return new ArrayList<>(zelje.values());
		}

	}
	
	private class ZeljeniTreninziRowMapper implements RowMapper<ZeljeniTreninzi> {

		@Override
		public ZeljeniTreninzi mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			int index = 1;
			Long id = rs.getLong(index++);
			
			Long korisnikId = rs.getLong(index++);
			String korisnickoIme = rs.getString(index++);
			String email = rs.getString(index++);
			String ime = rs.getString(index++);
			String prezime = rs.getString(index++);
			LocalDate datumRodjenja = rs.getObject(index++, LocalDate.class);
			String adresa = rs.getString(index++);
			String brojTelefona = rs.getString(index++);
			LocalDateTime datumIVremeRegistracije = rs.getTimestamp(index++).toLocalDateTime();
			String uloga = rs.getString(index++);
			Boolean blokiran = rs.getBoolean(index++);
			
			Korisnik korisnik = new Korisnik(korisnikId, korisnickoIme, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumIVremeRegistracije, uloga, blokiran);
			
			ZeljeniTreninzi zeljeni = new ZeljeniTreninzi(id, korisnik);
			
			return zeljeni;
		}
		
	}
	
	@Transactional
	@Override
	public int save(ZeljeniTreninzi zeljeni) {
		// TODO Auto-generated method stub
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				String sql = "INSERT INTO zeljeniTreninzi (korisnikId) VALUES (?)";
				
				PreparedStatement preparedStatement = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setObject(index++, zeljeni.getKorisnik().getId());
				return preparedStatement;
			}
		};
		
		
		GeneratedKeyHolder key = new GeneratedKeyHolder();
		boolean uspeh = JdbcTemplate.update(preparedStatementCreator, key) == 1;
		if(uspeh) {
			String sql = "INSERT INTO zeljeniTreninziTrening (zeljeniTreningId, treningId) VALUES (?, ?)";
			for(Trening trening : zeljeni.getTrening()) {
				uspeh = uspeh && JdbcTemplate.update(sql, key.getKey(), trening.getId()) == 1;
			}
		}
		return uspeh?1:0;
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM zeljeniTreninzi WHERE id = ?";
		return JdbcTemplate.update(sql, id);
	}

	@Override
	public ZeljeniTreninzi findOne(Long id) {
		// TODO Auto-generated method stub
		String sql = "SELECT zt.id, " +
				"kor.id, kor.korisnickoIme, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, " +
			    "kor.brojTelefona, kor.datumIVremeRegistracije, kor.uloga, kor.blokiran, " +
							"t.id, t.naziv, t.trener, t.opis, t.slika, t.cena, t.vrstaTreninga, t.nivoTreninga, " +
							"t.trajanje, t.prosecnaOcena FROM zeljeniTreninzi zt " +
							"LEFT JOIN zeljeniTreninziTrening ztt ON ztt.zeljeniTreningId = zt.id " +
							"LEFT JOIN treninzi t ON ztt.treningId = t.id " +
							"LEFT JOIN korisnici kor ON zt.korisnikId = kor.id " +
							"WHERE zt.id = ? " +
							"ORDER BY zt.id";
		
		ZeljeniTreninziTreningRowCallBackHandler rowCallbackHandler = new ZeljeniTreninziTreningRowCallBackHandler();
		JdbcTemplate.query(sql, rowCallbackHandler, id);
		
		return rowCallbackHandler.getZelje().get(0);
	}
	
	private List<Trening> findZeljeniTreninziTrening(Long zeljeniTreninziId, Long treningId) {
		
		List<Trening> treningZeljeniTrening = new ArrayList<Trening>();
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT ztt.zeljeniTreningId, ztt.treningId FROM zeljeniTreninziTrening ztt ";
		
		StringBuffer where = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(zeljeniTreninziId != null) {
			if(imaArgumenata)
				where.append(" AND ");
			where.append("ztt.zeljeniTreningId = ?");
			imaArgumenata = true;
			listaArgumenata.add(zeljeniTreninziId);
		}
		
		if(treningId != null) {
			if(imaArgumenata)
				where.append(" AND ");
			where.append("ztt.treningId = ?");
			imaArgumenata = true;
			listaArgumenata.add(treningId);
		}

		if(imaArgumenata)
			sql=sql + where.toString()+" ORDER BY ztt.zeljeniTreningId";
		else
			sql=sql + " ORDER BY ztt.treningId";
		
		List<Long[]> zeljeniTreninziTrening = JdbcTemplate.query(sql, listaArgumenata.toArray(), new ZeljeniTreninziTreningRowMapper()); 
				
		for (Long[] t : zeljeniTreninziTrening) {
			treningZeljeniTrening.add(treningDAO.findOne(t[1]));
		}
		return treningZeljeniTrening;
	}

	@Override
	public List<ZeljeniTreninzi> find(Long treningId, Long korisnikId) {
		// TODO Auto-generated method stub
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT zt.id, " +
				"kor.id, kor.korisnickoIme, kor.email, kor.ime, kor.prezime, " +
				"kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumIVremeRegistracije, " +
		        "kor.uloga, kor.blokiran FROM zeljeniTreninzi zt " +
				"LEFT JOIN korisnici kor ON zt.korisnikId = kor.id ";
		
		StringBuffer where = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(korisnikId != null) {
			if(imaArgumenata)
				where.append(" AND ");
			where.append("zt.korisnikId LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(korisnikId);
		}
		
		if(imaArgumenata)
			sql=sql + where.toString() + " ORDER BY zt.id";
		else
			sql=sql + " ORDER BY zt.id";
		
		List<ZeljeniTreninzi> zeljeniTreninzi = JdbcTemplate.query(sql, listaArgumenata.toArray(), new ZeljeniTreninziRowMapper());
		
		for (ZeljeniTreninzi zelje : zeljeniTreninzi) {
			zelje.setTrening(findZeljeniTreninziTrening(zelje.getId(), null));
		}
		
		if(treningId != null) {
			for(Iterator<ZeljeniTreninzi> iterator = zeljeniTreninzi.iterator(); iterator.hasNext();) {
				ZeljeniTreninzi zt = iterator.next();
				boolean zaBrisanje = true;
				for(Trening t : zt.getTrening()) {
					if(t.getId() == treningId) {
						zaBrisanje = false;
						break;
					}
				}
				if(zaBrisanje) {
					iterator.remove();
				}
			}
		}
			
		return zeljeniTreninzi;
	
	}
	
	
	
	private class ZeljeniTreninziTreningRowMapper implements RowMapper<Long []> {

		@Override
		public Long [] mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long zeljeniTreninziId = rs.getLong(index++);
			Long treningId = rs.getLong(index++);

			Long [] zeljeniTreninziTrening = {zeljeniTreninziId, treningId};
			return zeljeniTreninziTrening;
		}
	}

}
