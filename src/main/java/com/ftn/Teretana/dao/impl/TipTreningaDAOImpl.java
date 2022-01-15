package com.ftn.Teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.Teretana.dao.TipTreningaDAO;
import com.ftn.Teretana.model.TipTreninga;

@Repository
public class TipTreningaDAOImpl implements TipTreningaDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class AutoRowMapper implements RowMapper<TipTreninga> {

		@Override
		public TipTreninga mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			int index =1;
			Long id = rs.getLong(index++);
			String ime = rs.getString(index++);
			String opis = rs.getString(index++);
			
			TipTreninga tip = new TipTreninga(id, ime, opis);
			return tip;
		}
		
	}
	
	@Override
	public List<TipTreninga> findAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM tipoviTreninga";
		return jdbcTemplate.query(sql, new AutoRowMapper());
	}

	@Override
	public TipTreninga findOne(Long id) {
		// TODO Auto-generated method stub
		String sql = "SELECT id, ime, opis FROM tipoviTreninga WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new AutoRowMapper(), id);
	}

	@Override
	public List<TipTreninga> find(String ime, String opis) {
		// TODO Auto-generated method stub
		return null;
	}

}
