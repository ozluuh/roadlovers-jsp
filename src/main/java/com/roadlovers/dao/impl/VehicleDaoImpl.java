/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roadlovers.dao.impl;

import com.roadlovers.dao.CrudDao;
import com.roadlovers.model.Vehicle;
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
public class VehicleDaoImpl implements CrudDao<Vehicle, Long> {

	private static final String TABLE_NAME = "Tbl_Vehicle";

	private final Connection conn;
	private ResultSet res;
	private PreparedStatement stmt;

	public VehicleDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void store(Vehicle entity) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + "(_Year,Model,Price,Class_Id,Manufacturer_Id)"
				+ " VALUES(?,?,?,?,?);";

		stmt = conn.prepareStatement(sql, new String[]{"Id"});
		stmt.setInt(1, entity.getYear());
		stmt.setString(2, entity.getModel());
		stmt.setDouble(3, entity.getValue());
		stmt.setLong(4, entity.getClasse().getId());
		stmt.setLong(5, entity.getManufacturer().getId());

		stmt.executeUpdate();

		res = stmt.getGeneratedKeys();

		if (res.next()) {
			// Get key in position
			entity.setId(res.getLong(1));
		}

		close();
	}

	@Override
	public int update(Vehicle entity) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET"
				+ " _Year = ?, Model = ?, Price = ?, Class_Id = ?, Manufacturer_Id = ?"
				+ " WHERE Id = ?;";

		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, entity.getYear());
		stmt.setString(2, entity.getModel());
		stmt.setDouble(3, entity.getValue());
		stmt.setLong(3, entity.getClasse().getId());
		stmt.setLong(4, entity.getManufacturer().getId());

		stmt.setLong(5, entity.getId());

		int rows = stmt.executeUpdate();

		close();

		return rows;
	}

	@Override
	public int destroy(Long id) throws SQLException {
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE Id = ?;";

		stmt = conn.prepareStatement(sql);
		stmt.setLong(1, id);

		int rows = stmt.executeUpdate();

		close();

		return rows;
	}

	@Override
	public Vehicle findById(Long id) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE Id = ?;";
		Vehicle response = null;

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
	public List<Vehicle> findAll() throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + ";";
		List<Vehicle> response = new ArrayList<>();

		stmt = conn.prepareStatement(sql);
		res = stmt.executeQuery();

		while (res.next()) {
			Vehicle vehicle = null;
			vehicle = parse(res);

			response.add(vehicle);
		}

		close();

		return response;
	}

	private Vehicle parse(ResultSet res) throws SQLException {
		long id = res.getLong("Id");
		int year = res.getInt("_Year");
		String model = res.getString("Model");
		double value = res.getDouble("Price");
		String createdAt = res.getString("CreatedAt");

		System.out.println("Date: " + createdAt);

		return Vehicle
				.builder()
				.id(id)
				.model(model)
				.value(value)
				.build();
	}

	private void close() throws SQLException {
		if (Objects.nonNull(res)) {
			res.close();
		}
		stmt.close();
	}
}
