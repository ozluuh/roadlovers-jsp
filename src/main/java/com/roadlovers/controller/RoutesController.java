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
    urlPatterns = { "/vehicles", "/vehicles/new", "/vehicles/edit", "/vehicles/delete", "/vehicles/store", "/vehicles/update" }
)
public class RoutesController extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static Connection conn;

	private static VehicleDaoImpl dao;

	private static VehicleTypeDaoImpl daoClasses;

	private static ManufacturerDaoImpl daoManufacturer;

	public RoutesController() {
		try {
			conn = ConnectionFactory.getInstance().getConnection();
		} catch (ClassNotFoundException | SQLException ex) {
			Logger.getLogger(RoutesController.class.getName()).log(Level.SEVERE, null, ex);
		}

		dao = new VehicleDaoImpl(conn);
		daoClasses = new VehicleTypeDaoImpl(conn);
		daoManufacturer = new ManufacturerDaoImpl(conn);
	}


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = ServletUtil.getURI(req);

		switch (uri) {
		case "/vehicles":
			index(req, resp);
			break;
//		case "/vehicles/new":
//			setFormAttributes(req);
//			req.getRequestDispatcher("/WEB-INF/vehicles/new.jsp").forward(req, resp);
//			break;
//		case "/vehicles/edit":
//			edit(req, resp);
//			break;
//		case "/vehicles/delete":
//			destroy(req, resp);
//			break;
//		case "/vehicles/store":
//			store(req, resp);
//			break;
//		case "/vehicles/update":
//			update(req, resp);
//			break;
		default:
			ServletUtil.sendRedirectTo(req, resp, "/index.jsp");
			break;
		}
	}
//
//	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String idToSearch = req.getParameter("id");
//
//		if(idToSearch == null || idToSearch.isEmpty()) {
//			addSessionAttribute(req, "message", "Código inválido ou não informado");
//			addSessionAttribute(req, "severity", "danger");
//			sendRedirectTo(req, resp, "/vehicles");
//			return;
//		}
//		Optional<Vehicle> vehicle = vehicles.stream().filter(v -> v.getId() == Long.valueOf(idToSearch)).findFirst();
//
//		if(!vehicle.isPresent()) {
//			addSessionAttribute(req, "message", "Veículo não encontrado");
//			sendRedirectTo(req, resp, "/vehicles");
//			return;
//		}
//
//		req.setAttribute("vehicle", vehicle.get());
//		setFormAttributes(req);
//		req.getRequestDispatcher("/WEB-INF/vehicles/edit.jsp").forward(req, resp);
//	}
//
//
//	private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//		Vehicle parsedVehicle = parseVehicle(req);
//
//		vehicles.removeIf(v -> v.getId() == parsedVehicle.getId());
//		vehicles.add(parsedVehicle);
//		vehicles.sort(Comparator.comparing(Vehicle::getId));
//
//		addSessionAttribute(req, "message", "Veículo atualizado com sucesso!");
//		addSessionAttribute(req, "severity", "success");
//
//		sendRedirectTo(req, resp, "/vehicles");
//	}
//
//	private void destroy(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//		Long idToRemove = Long.valueOf(req.getParameter("vehicleId"));
//
//		vehicles = vehicles.stream().filter(vehicle -> vehicle.getId() != idToRemove).collect(Collectors.toList());
//
//		addSessionAttribute(req, "message", "Veículo removido com sucesso");
//		addSessionAttribute(req, "severity", "success");
//
//		sendRedirectTo(req, resp, "/vehicles");
//	}
//
//
//
//	private void store(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//		Vehicle parsedVehicle = parseVehicle(req);
//
//		vehicles.add(parsedVehicle);
//
//		addSessionAttribute(req, "message", "Veículo cadastrado com sucesso!");
//		addSessionAttribute(req, "severity", "success");
//
//		sendRedirectTo(req, resp, "/vehicles");
//	}
//
//	private Vehicle parseVehicle(HttpServletRequest req) {
//		Long idReq;
//		String model = req.getParameter("txtVehicleModel");
//		int year = Integer.valueOf(req.getParameter("nbrVehicleYear"));
//		Manufacturer manufacturer = Manufacturer.valueOf(req.getParameter("slcVehicleManufacturer"));
//		VehicleType classe = VehicleType.valueOf(req.getParameter("slcVehicleType"));
//		String valueReq = req.getParameter("vlrVehicleValue");
//
//		if(valueReq.contains(".") || valueReq.contains(",")) {
//			valueReq = valueReq.replaceAll("\\D", "");
//			valueReq = valueReq.substring(0, valueReq.length() - 2) + "." + valueReq.substring(valueReq.length() - 2);
//		}
//
//		double value = Double.valueOf(valueReq);
//
//		try {
//			idReq = Long.valueOf(req.getParameter("idVehicle").trim());
//		} catch (NumberFormatException e) {
//			idReq = id++;
//		}
//
//		Vehicle vehicle = new Vehicle();
//
//		vehicle.setId(idReq);
//		vehicle.setClasse(classe);
//		vehicle.setYear(year);
//		vehicle.setManufacturer(manufacturer);
//		vehicle.setValue(value);
//		vehicle.setModel(model);
//		vehicle.setCreatedAt(LocalDateTime.now());
//
//		return vehicle;
//	}

	private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String year = req.getParameter("nbrYear");

		List<Vehicle> vehicles = new ArrayList<>();

		try {
			vehicles = dao.findAll();
		} catch (SQLException ex) {
			Logger.getLogger(RoutesController.class.getName()).log(Level.SEVERE, null, ex);
		}

		if(year == null || year.isEmpty()) {
			req.setAttribute("vehiclesList", vehicles);
		} else {
			List<Vehicle> filteredVehicles = vehicles
					.stream()
					.filter(vehicle -> vehicle.getYear() == Integer.valueOf(year))
					.collect(Collectors.toList());

			req.setAttribute("vehiclesList", filteredVehicles);
		}

		req.getRequestDispatcher("/vehicles.jsp").forward(req, resp);
	}
}
