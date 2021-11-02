package com.roadlovers.controller;

import com.roadlovers.connection.ConnectionFactory;
import com.roadlovers.dao.impl.ManufacturerDaoImpl;
import com.roadlovers.dao.impl.VehicleDaoImpl;
import com.roadlovers.dao.impl.VehicleTypeDaoImpl;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.roadlovers.model.Manufacturer;
import com.roadlovers.model.Vehicle;
import com.roadlovers.model.VehicleType;
import com.roadlovers.util.ServletUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(
    description = "Vehicles routes",
    urlPatterns = { "/vehicle", "/vehicle/new", "/vehicle/edit", "/vehicle/delete", "/vehicle/store", "/vehicle/update" }
)
public class VehicleController extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static Connection conn;

	private static VehicleDaoImpl dao;

	private static VehicleTypeDaoImpl daoClasses;

	private static ManufacturerDaoImpl daoManufacturer;

	public VehicleController() {
		try {
			conn = ConnectionFactory.getInstance().getConnection();
		} catch (ClassNotFoundException | SQLException ex) {
			Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
		}

		dao = new VehicleDaoImpl(conn);
		daoClasses = new VehicleTypeDaoImpl(conn);
		daoManufacturer = new ManufacturerDaoImpl(conn);
	}


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = ServletUtil.getURI(req);

		switch (uri) {
		case "/vehicle":
			index(req, resp);
			break;
		case "/vehicle/new":
			entry(req, resp);
			break;
		case "/vehicle/edit":
			edit(req, resp);
			break;
		case "/vehicle/delete":
			destroy(req, resp);
			break;
		case "/vehicle/store":
			store(req, resp);
			break;
		case "/vehicle/update":
			update(req, resp);
			break;
		default:
			ServletUtil.sendRedirectTo(req, resp, "/index.jsp");
			break;
		}
	}

	private void entry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		loadExtras(req);
		req.getRequestDispatcher("/WEB-INF/vehicles/new.jsp").forward(req, resp);
	}

	private void loadExtras(HttpServletRequest req) {
		List<VehicleType> classes;
		List<Manufacturer> manufacturers;

		try {
			classes = daoClasses.findAll();
		} catch (SQLException e) {
			classes = new ArrayList<>();
			e.printStackTrace();
		}

		try {
			manufacturers = daoManufacturer.findAll();
		} catch (SQLException e) {
			manufacturers = new ArrayList<>();
			e.printStackTrace();
		}

		ServletUtil.addRequestAttribute(req, "manufacturers", manufacturers);
		ServletUtil.addRequestAttribute(req, "classes", classes);
	}



	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idToSearch = req.getParameter("id");

		if(idToSearch == null || idToSearch.isEmpty()) {
			ServletUtil.addSessionAttribute(req, "message", "Código inválido ou não informado");
			ServletUtil.addSessionAttribute(req, "severity", "danger");
			ServletUtil.sendRedirectTo(req, resp, "/vehicles");
			return;
		}
		Long id = Long.valueOf(idToSearch);
		Vehicle vehicle = null;

		try {
			vehicle = dao.findById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(vehicle == null) {
			ServletUtil.addSessionAttribute(req, "message", "Veículo não encontrado");
			ServletUtil.sendRedirectTo(req, resp, "/vehicles");
			return;
		}

		req.setAttribute("vehicle", vehicle);
		loadExtras(req);
		req.getRequestDispatcher("/WEB-INF/vehicles/edit.jsp").forward(req, resp);
	}


	private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Vehicle vehicle = parseVehicle(req);

		try {
			dao.update(vehicle);

			ServletUtil.addSessionAttribute(req, "message", "Veículo atualizado com sucesso!");
			ServletUtil.addSessionAttribute(req, "severity", "success");
		} catch (SQLException e) {
			ServletUtil.addSessionAttribute(req, "message", "Erro ao atualizar o veículo. Tente novamente.");
			ServletUtil.addSessionAttribute(req, "severity", "danger");

			e.printStackTrace();
		}

		ServletUtil.sendRedirectTo(req, resp, "/vehicles");
	}

	private void destroy(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Long id = Long.valueOf(req.getParameter("vehicleId"));

		try {
			dao.destroy(id);

			ServletUtil.addSessionAttribute(req, "message", "Veículo removido com sucesso");
			ServletUtil.addSessionAttribute(req, "severity", "success");
		} catch (SQLException e) {
			ServletUtil.addSessionAttribute(req, "message", "Erro ao remover o veículo. Tente novamente.");
			ServletUtil.addSessionAttribute(req, "severity", "danger");
			e.printStackTrace();
		}

		ServletUtil.sendRedirectTo(req, resp, "/vehicles");
	}



	private void store(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Vehicle vehicle = parseVehicle(req);

		// if(vehicle)

		try {
			dao.store(vehicle);

			ServletUtil.addSessionAttribute(req, "message", "Veículo cadastrado com sucesso!");
			ServletUtil.addSessionAttribute(req, "severity", "success");
		} catch (SQLException ex) {
			Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
			ServletUtil.addSessionAttribute(req, "message", "Erro ao cadastrar. Tente novamente");
			ServletUtil.addSessionAttribute(req, "severity", "danger");
		}

		ServletUtil.sendRedirectTo(req, resp, "/vehicle");
	}

	private Vehicle parseVehicle(HttpServletRequest req) {
		Long id;
		Manufacturer manufacturer;
		VehicleType classe;
		String model = req.getParameter("txtVehicleModel");
		int year = Integer.valueOf(req.getParameter("nbrVehicleYear"));
		long manufacturerId = Long.valueOf(req.getParameter("slcVehicleManufacturer"));
		long classeId = Long.valueOf(req.getParameter("slcVehicleType"));
		double value = Double.valueOf(req.getParameter("vlrVehicleValue"));

		try {
			id = Long.valueOf(req.getParameter("idVehicle").trim());
		} catch (NumberFormatException e) {
			id = null;
		}

		try {
			classe = daoClasses.findById(classeId);
		} catch (SQLException e) {
			classe = null;
			e.printStackTrace();
		}

		try {
			manufacturer = daoManufacturer.findById(manufacturerId);
		} catch (SQLException e) {
			manufacturer = null;
			e.printStackTrace();
		}

		return Vehicle
				.builder()
				.id(id)
				.year(year)
				.value(value)
				.model(model)
				.classe(classe)
				.manufacturer(manufacturer)
				.build();
	}

	private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String year = req.getParameter("nbrYear");

		List<Vehicle> vehicles = new ArrayList<>();

		try {
			vehicles = dao.findAll();
		} catch (SQLException ex) {
			Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
		}

		if(year == null || year.isEmpty()) {
			ServletUtil.addRequestAttribute(req, "vehicles", vehicles);
		} else {
			List<Vehicle> filteredVehicles = vehicles
					.stream()
					.filter(vehicle -> vehicle.getYear() == Integer.valueOf(year))
					.collect(Collectors.toList());

			ServletUtil.addRequestAttribute(req, "vehicles", vehicles);
		}

		req.getRequestDispatcher("/vehicles.jsp").forward(req, resp);
	}
}
