package com.ftn.Teretana.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.Teretana.dao.KorpaDAO;
import com.ftn.Teretana.dao.ZakazivanjeDAO;
import com.ftn.Teretana.model.Korpa;
import com.ftn.Teretana.model.Zakazivanje;

@Repository
public class ZakazivanjeDAOImpl implements ZakazivanjeDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private KorpaDAO korpaDAO;
	
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
				preparedStatement.setObject(index++, zakazivanje.getDatumZakazivanja().plusMinutes(60));
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

}
