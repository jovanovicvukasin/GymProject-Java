package com.ftn.Teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.ftn.Teretana.dao.TipTreningaDAO;
import com.ftn.Teretana.dao.TreningDAO;
import com.ftn.Teretana.model.TipTreninga;
import com.ftn.Teretana.model.Trening;

@Repository
public class TreningDAOImpl implements TreningDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemlate;
	
	@Autowired
	private TipTreningaDAO tipTreningaDAO;
	
	
	private class TreningRowCallBackHandler implements RowCallbackHandler{
		
		private Map<Long, Trening> treninzi = new LinkedHashMap<>();

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			// TODO Auto-generated method stub
			int index = 1;
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
			
			Trening trening = treninzi.get(treningid);
			if(trening == null) {
				trening = new Trening(treningid, naziv, trener, opis, slika, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena);
				treninzi.put(trening.getId(), trening);
			}
			
			Long tipTreningaId = rs.getLong(index++);
			String ime = rs.getString(index++);
			String opisTT = rs.getString(index++);
			TipTreninga tt = new TipTreninga(tipTreningaId, ime, opisTT);
			if(trening.getTipTreninga().contains(tt)) {
				trening.getTipTreninga().remove(tt);
				trening.getTipTreninga().add(tt);
			}else {
				trening.getTipTreninga().add(tt);
			}

		}
		
		public List<Trening> getTreninzi(){
			return new ArrayList<>(treninzi.values());
		}
		
	}


	@Override
	public List<Trening> findAll() {
		String sql = "SELECT * FROM treninzi t " +
				"LEFT JOIN treningTipTreninga ttt ON ttt.treningId = t.id " +
				"LEFT JOIN tipoviTreninga tt ON ttt.tipTreningaId = tt.id " +
			"ORDER BY t.id";
		
		TreningRowCallBackHandler rowCallbackHandler = new TreningRowCallBackHandler();
		jdbcTemlate.query(sql, rowCallbackHandler);
		return rowCallbackHandler.getTreninzi();
	}
	
	
	
	
	

}
