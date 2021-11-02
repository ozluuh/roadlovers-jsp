/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roadlovers.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.roadlovers.connection.ConnectionFactory;
import com.roadlovers.dao.impl.ManufacturerDaoImpl;
import com.roadlovers.model.Manufacturer;
import com.roadlovers.util.ServletUtil;

/**
 *
 * @author ozluuh
 */
@WebServlet(
		description = "Manufacturer routes",
		urlPatterns = {"/manufacturer", "/manufacturer/store"}
)
public class ManufacturerController extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static Connection conn;

	private static ManufacturerDaoImpl dao;

	public ManufacturerController() {
		try {
			conn = ConnectionFactory.getInstance().getConnection();
		} catch (ClassNotFoundException | SQLException ex) {
			Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
		}

		dao = new ManufacturerDaoImpl(conn);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = ServletUtil.getURI(req);

		switch(uri){
			case "/manufacturer/store":
				store(req, resp);
				break;
			case "/manufacturer":
				index(req, resp);
				break;
			default:
				ServletUtil.sendRedirectTo(req, resp, "/manufacturer.jsp");
		}
	}

	private void store(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String manufacturerName = req.getParameter("txtManufacturerName");

		if (manufacturerName == null || manufacturerName.isEmpty()) {
			ServletUtil.addSessionAttribute(req, "message", "Nome da fabricante precisa ser preenchido");
			ServletUtil.addSessionAttribute(req, "severity", "danger");
			ServletUtil.sendRedirectTo(req, resp, "/manufacturer");
			return;
		}

		Manufacturer manufacturer = Manufacturer.builder().description(manufacturerName).build();

		try {
			dao.store(manufacturer);
			ServletUtil.addSessionAttribute(req, "message", "Fabricante cadastrada com sucesso");
			ServletUtil.addSessionAttribute(req, "severity", "success");
		} catch (SQLException e) {
			ServletUtil.addSessionAttribute(req, "message", "Erro ao cadastrar fabricante. Tente novamente.");
			ServletUtil.addSessionAttribute(req, "severity", "danger");
		}

		ServletUtil.sendRedirectTo(req, resp, "/manufacturer");
	}

	private void index(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<Manufacturer> manufacturers;
		try {
			manufacturers = dao.findAll();
		} catch (SQLException e) {
			manufacturers = new ArrayList<>();
		}

		ServletUtil.addRequestAttribute(req, "manufacturers", manufacturers);
		req.getRequestDispatcher("/manufacturer.jsp").forward(req, resp);
	}

}
