package com.ftn.Teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.Teretana.dao.TerminTreningaDAO;
import com.ftn.Teretana.model.Sala;
import com.ftn.Teretana.model.TerminTreninga;
import com.ftn.Teretana.model.Trening;

@Repository
public class TerminTreningaDAOImpl implements TerminTreningaDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class TerminTreningaRowMapper implements RowMapper<TerminTreninga>{

		@Override
		public TerminTreninga mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			int index = 1;
			Long terminId = rs.getLong(index++);
			LocalDateTime datum = rs.getObject(index++, LocalDateTime.class);
			Integer kapacitetT = rs.getInt(index++);
			
			Long salaId = rs.getLong(index++);
			String oznakaSale = rs.getString(index++);
			Integer kapacitet = rs.getInt(index++);
			
			Sala sala = new Sala(salaId, oznakaSale, kapacitet);
			
			Long treningId = rs.getLong(index++);
			String naziv = rs.getString(index++);
			LocalTime trajanje = rs.getObject(index++, LocalTime.class);
			Double cena = rs.getDouble(index++);
			/*String trener = rs.getString(index++);
			String opis = rs.getString(index++);
			String slika = rs.getString(index++);
			String vrstaTreninga = rs.getString(index++);
			String nivoTreninga = rs.getString(index++);
			LocalTime trajanje = rs.getObject(index++, LocalTime.class);
			Float prosecnaOcena = rs.getFloat(index++);
			*/
			Trening trening = new Trening(treningId, naziv, trajanje, cena);
			

			
			TerminTreninga termin = new TerminTreninga(terminId, sala, trening, datum, kapacitetT);
			return termin;
		}

		
		
	}

	@Override
	public List<TerminTreninga> findAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT tt.id, tt.datum, tt.kapacitet, s.id, s.oznakaSale, s.kapacitet, t.id, t.naziv, t.trajanje, t.cena " +
				"FROM termini tt LEFT JOIN sale s ON tt.salaId = s.id " +
				"LEFT JOIN treninzi t ON tt.treningId = t.id " +
				"ORDER BY tt.id";
		return jdbcTemplate.query(sql, new TerminTreningaRowMapper());
	}

	@Override
	public List<TerminTreninga> findTrening(Long id, LocalDateTime datum) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT tt.id, tt.datum, tt.kapacitet, s.id, s.oznakaSale, s.kapacitet, t.id, t.naziv, t.trajanje, t.cena " +
				"FROM termini tt LEFT JOIN sale s ON tt.salaId = s.id " +
				"LEFT JOIN treninzi t ON tt.treningId = t.id " +
				"WHERE t.id = ? and tt.datum > ?" +
				"ORDER BY tt.id";
		
		
		
		return jdbcTemplate.query(sql, new TerminTreningaRowMapper(), id, datum);
	}

	@Override
	public TerminTreninga findOne(Long id) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT tt.id, tt.datum, tt.kapacitet, s.id, s.oznakaSale, s.kapacitet, t.id, t.naziv, t.trajanje, t.cena " +
				"FROM termini tt LEFT JOIN sale s ON tt.salaId = s.id " +
				"LEFT JOIN treninzi t ON tt.treningId = t.id " +
				"WHERE tt.id = ? " +
				"ORDER BY tt.id";
		return jdbcTemplate.queryForObject(sql, new TerminTreningaRowMapper(), id);
	}

	@Override
	public void save(TerminTreninga terminTreninga) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO termini (salaId, treningId, datum, kapacitet) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, terminTreninga.getSala().getId(), terminTreninga.getTrening().getId(), terminTreninga.getDatum(), terminTreninga.getSala().getKapacitet());
		
	}

	@Override
	public List<TerminTreninga> findSalaDatum(Long id, LocalDateTime datumOd, LocalDateTime datumDo, Long id1, LocalDateTime datumDo1, LocalDateTime datumOd1, LocalDateTime datumDo2, LocalDateTime datumOd2) {
		// TODO Auto-generated method stub
		String sql = "SELECT tt.id, tt.datum, tt.kapacitet, s.id, s.oznakaSale, s.kapacitet, t.id, t.naziv, t.trajanje, t.cena " +
				"FROM termini tt LEFT JOIN sale s ON tt.salaId = s.id " +
				"LEFT JOIN treninzi t ON tt.treningId = t.id " +
				"WHERE tt.salaId = ? and tt.datum >= ? and tt.datum <= ? " +
				"OR tt.salaId = ? and ? >= ? and ? <= ? " +
				"ORDER BY tt.id";
		
		return jdbcTemplate.query(sql, new TerminTreningaRowMapper(), id, datumOd, datumDo, id1, datumDo1, datumOd1, datumDo2, datumOd2);
	}

	@Override
	public List<TerminTreninga> findTerminSala(Long id) {
		// TODO Auto-generated method stub
		String sql = "SELECT tt.id, tt.datum, tt.kapacitet, s.id, s.oznakaSale, s.kapacitet, t.id, t.naziv, t.trajanje, t.cena " +
				"FROM termini tt LEFT JOIN sale s ON tt.salaId = s.id " +
				"LEFT JOIN treninzi t ON tt.treningId = t.id " +
				"WHERE tt.salaId = ? " +
				"ORDER BY tt.id";
		return jdbcTemplate.query(sql, new TerminTreningaRowMapper(), id);
	}

	@Override
	public int updateKapacitet(TerminTreninga terminTreninga) {
		// TODO Auto-generated method stub
		String sql = "UPDATE termini SET kapacitet = ?  WHERE id = ?";
		return jdbcTemplate.update(sql, terminTreninga.getKapacitet(), terminTreninga.getId());
	
	}

}
