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
			
			Long salaId = rs.getLong(index++);
			String oznakaSale = rs.getString(index++);
			Integer kapacitet = rs.getInt(index++);
			
			Sala sala = new Sala(salaId, oznakaSale, kapacitet);
			
			Long treningId = rs.getLong(index++);
			String naziv = rs.getString(index++);
			/*String trener = rs.getString(index++);
			String opis = rs.getString(index++);
			String slika = rs.getString(index++);
			Double cena = rs.getDouble(index++);
			String vrstaTreninga = rs.getString(index++);
			String nivoTreninga = rs.getString(index++);
			LocalTime trajanje = rs.getObject(index++, LocalTime.class);
			Float prosecnaOcena = rs.getFloat(index++);
			*/
			Trening trening = new Trening(treningId, naziv);
			

			
			TerminTreninga termin = new TerminTreninga(terminId, sala, trening, datum);
			return termin;
		}

		
		
	}

	@Override
	public List<TerminTreninga> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TerminTreninga> findTrening(Long id) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT tt.id, tt.datum, s.id, s.oznakaSale, s.kapacitet, t.id, t.naziv " +
				"FROM termini tt LEFT JOIN sale s ON tt.salaId = s.id " +
				"LEFT JOIN treninzi t ON tt.treningId = t.id " +
				"WHERE t.id = ? " +
				"ORDER BY tt.id";
		
		
		
		return jdbcTemplate.query(sql, new TerminTreningaRowMapper(), id);
	}

}
