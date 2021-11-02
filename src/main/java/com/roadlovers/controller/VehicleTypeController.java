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
import com.roadlovers.dao.impl.VehicleTypeDaoImpl;
import com.roadlovers.model.VehicleType;
import com.roadlovers.util.ServletUtil;

/**
 *
 * @author ozluuh
 */
@WebServlet(
		description = "Classe routes",
		urlPatterns = {"/classe", "/classe/store"}
)
public class VehicleTypeController extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static Connection conn;

	private static VehicleTypeDaoImpl dao;

	public VehicleTypeController() {
		try {
			conn = ConnectionFactory.getInstance().getConnection();
		} catch (ClassNotFoundException | SQLException ex) {
			Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
		}

		dao = new VehicleTypeDaoImpl(conn);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = ServletUtil.getURI(req);

		switch(uri){
			case "/classe/store":
				store(req, resp);
				break;
			case "/classe":
				index(req, resp);
				break;
			default:
				ServletUtil.sendRedirectTo(req, resp, "/classe.jsp");
		}

	}

	private void store(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String classeName = req.getParameter("txtClasseName");

		if (classeName == null || classeName.isEmpty()) {
			ServletUtil.addSessionAttribute(req, "message", "Nome da classe precisa ser preenchido");
			ServletUtil.addSessionAttribute(req, "severity", "danger");
			ServletUtil.sendRedirectTo(req, resp, "/classe");
			return;
		}

		VehicleType classe = VehicleType.builder().description(classeName).build();

		try {
			dao.store(classe);
			ServletUtil.addSessionAttribute(req, "message", "Classe cadastrada com sucesso");
			ServletUtil.addSessionAttribute(req, "severity", "success");
		} catch (SQLException e) {
			ServletUtil.addSessionAttribute(req, "message", "Erro ao cadastrar classe. Tente novamente.");
			ServletUtil.addSessionAttribute(req, "severity", "danger");
		}

		ServletUtil.sendRedirectTo(req, resp, "/classe");
	}

	private void index(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<VehicleType> classes;
		try {
			classes = dao.findAll();
		} catch (SQLException e) {
			classes = new ArrayList<>();
		}

		ServletUtil.addRequestAttribute(req, "classes", classes);
		req.getRequestDispatcher("/classe.jsp").forward(req, resp);
	}

}
