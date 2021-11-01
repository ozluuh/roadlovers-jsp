/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roadlovers.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author ozluuh
 */
public class ConnectionFactory {

	private static ConnectionFactory factory;

	private static String dbHost;

	private static String dbUser;

	private static String dbPass;

	private static String dbPort;

	private ConnectionFactory() {
		dbHost = Optional.ofNullable(System.getenv("DB_HOST")).orElse("localhost");
		dbUser = Optional.ofNullable(System.getenv("DB_USER")).orElse("sa");
		dbPass = Optional.ofNullable(System.getenv("DB_PASS")).orElse("sa");
		dbPort = Optional.ofNullable(System.getenv("DB_PORT")).orElse("1433");
	}

	public static ConnectionFactory getInstance() {
		if (Objects.isNull(factory)) {
			factory = new ConnectionFactory();
		}

		return factory;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String dbUrl = "jdbc:sqlserver://" + dbHost + ":" + dbPort + ";databaseName=RoadLovers;";

		Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);

		return connection;
	}
}
