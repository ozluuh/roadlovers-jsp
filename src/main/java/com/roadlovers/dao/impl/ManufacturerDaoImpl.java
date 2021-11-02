/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roadlovers.dao.impl;

import com.roadlovers.dao.BaseDao;
import com.roadlovers.model.Manufacturer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ozluuh
 */
public class ManufacturerDaoImpl implements BaseDao<Manufacturer, Long> {

	private static final String TABLE_NAME = "Tbl_Manufacturer";

	private final Connection conn;
	private ResultSet res;
	private PreparedStatement stmt;

	public ManufacturerDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Manufacturer findById(Long id) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE Id = ?;";
		Manufacturer response = null;

		stmt = conn.prepareStatement(sql);
		stmt.setLong(1, id);

		res = stmt.executeQuery();

		if (res.next()) {
			response = parse(res);
		}

		close();

		return response;
	}

	@Override
	public List<Manufacturer> findAll() throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + ";";
		List<Manufacturer> response = new ArrayList<>();

		stmt = conn.prepareStatement(sql);

		res = stmt.executeQuery();

		while (res.next()) {
			Manufacturer manufacturer = parse(res);
			response.add(manufacturer);
		}

		close();

		return response;
	}

	private Manufacturer parse(ResultSet res) throws SQLException {
		long id = res.getLong("Id");
		String description = res.getString("Name");

		return Manufacturer
				.builder()
				.id(id)
				.description(description)
				.build();
	}

	private void close() throws SQLException {
		if (Objects.nonNull(res)) {
			res.close();
		}
		stmt.close();
	}

    public void store(Manufacturer manufacturer) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + "(Name)"
					+ "VALUES(?);";

		stmt = conn.prepareStatement(sql, new String[]{"Id"});
		stmt.setString(1, manufacturer.getDescription());

		stmt.executeUpdate();

		res = stmt.getGeneratedKeys();

		if(res.next()){
			manufacturer.setId(res.getLong(1));
		}

		close();
    }
}
