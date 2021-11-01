/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roadlovers.dao.impl;

import com.roadlovers.dao.BaseDao;
import com.roadlovers.model.VehicleType;
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
public class VehicleTypeDaoImpl implements BaseDao<VehicleType, Long> {

	private static final String TABLE_NAME = "Tbl_Class";

	private final Connection conn;
	private ResultSet res;
	private PreparedStatement stmt;

	public VehicleTypeDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public VehicleType findById(Long id) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE Id = ?;";
		VehicleType response = null;

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
	public List<VehicleType> findAll() throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + ";";
		List<VehicleType> response = new ArrayList<>();

		stmt = conn.prepareStatement(sql);
		res = stmt.executeQuery();

		while (res.next()) {
			VehicleType type = parse(res);
			response.add(type);
		}

		close();
		return response;
	}

	private void close() throws SQLException {
		if (Objects.nonNull(res)) {
			res.close();
		}
		stmt.close();
	}

	private VehicleType parse(ResultSet res) throws SQLException {
		long id = res.getLong("Id");
		String description = res.getString("Name");

		return VehicleType
				.builder()
				.id(id)
				.description(description)
				.build();
	}

}
