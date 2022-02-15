package com.ftn.Teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.Teretana.dao.SalaDAO;
import com.ftn.Teretana.model.Sala;

@Repository
public class SalaDAOImpl implements SalaDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class SalaRowMapper implements RowMapper<Sala>{

		@Override
		public Sala mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			int index = 1;
			Long id = rs.getLong(index++);
			String oznakaSale = rs.getString(index++);
			Integer kapacitet = rs.getInt(index++);
			
			Sala sala = new Sala(id, oznakaSale, kapacitet);
			
			return sala;
		}
		
	}

	@Override
	public List<Sala> findAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM sale";
		return jdbcTemplate.query(sql, new SalaRowMapper());
	}

	@Override
	public List<Sala> findOznakaSale(String oznakaSale) {
		// TODO Auto-generated method stub
		ArrayList<Object> lista = new ArrayList<Object>();

		String sql = "SELECT * FROM sale";
		
		StringBuffer where = new StringBuffer(" WHERE ");
		boolean imaArg = false;
		
		if(oznakaSale != null) {
			oznakaSale = "%" + oznakaSale + "%";
			if(imaArg)
				where.append(" AND ");
			where.append("oznakaSale LIKE ?");
			imaArg = true;
			lista.add(oznakaSale);
		}
		
		if(imaArg) {
			sql=sql + where.toString() + " ORDER BY id";
		}else {
			sql=sql + " ORDER BY id";
			
		}
		
		List<Sala> sale = jdbcTemplate.query(sql, lista.toArray(), new SalaRowMapper());
		
		return sale;
	}

	@Override
	public Sala findOne(Long id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM sale WHERE id = ? " +
		"ORDER BY id";
		return jdbcTemplate.queryForObject(sql, new SalaRowMapper(), id);
	}

	@Override
	public void save(Sala sala) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO sale (oznakaSale, kapacitet) VALUES (?, ?)";
	
		jdbcTemplate.update(sql, sala.getOznakaSale(), sala.getKapacitet());
	}

	@Override
	public Sala findOne(String oznakaSale) {
		try {
			String sql = "SELECT id, oznakaSale, kapacitet FROM sale WHERE oznakaSale = ?";
			return jdbcTemplate.queryForObject(sql, new SalaRowMapper(), oznakaSale);
		}catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public void edit(Sala sala) {
		// TODO Auto-generated method stub
		String sql = "UPDATE sale SET oznakaSale = ?, kapacitet = ? WHERE id = ?";
		
		jdbcTemplate.update(sql, sala.getOznakaSale(), sala.getKapacitet(), sala.getId());
		
	}

}
