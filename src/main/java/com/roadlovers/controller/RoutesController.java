package com.roadlovers.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

@WebServlet(
    description = "Requests/Responses of Vehicles",
    urlPatterns = { "/vehicles", "/vehicles/new", "/vehicles/edit", "/vehicles/delete", "/vehicles/store", "/vehicles/update" }
)
public class RoutesController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Long id = 1L;

    private static List<Vehicle> vehicles = getCars();
    
    private static final List<Vehicle> getCars() {
        List<String> models = List.of("Corvette (C2)","Cobalt SS","CLS55 AMG","Diablo");
        List<Integer> years = List.of(1963, 2006, 2009, 1993);
        List<Double> values = List.of(55000d,20000d,270000d,138000d);
        List<VehicleType> classes = List.of(VehicleType.MUSCLE, VehicleType.SPORTS, VehicleType.LUXURY, VehicleType.LUXURY, VehicleType.EXOTIC);
        List<Manufacturer> manufacturers = List.of(Manufacturer.CHEVROLET, Manufacturer.CHEVROLET, Manufacturer.MERCEDES_BENZ, Manufacturer.LAMBORGHINI);

        List<Vehicle> vehicles = new ArrayList<>();

        for (int i = 0; i < models.size(); i++) {
            Vehicle vehicle = new Vehicle();
            
            vehicle.setId(id++);
            vehicle.setModel(models.get(i));
            vehicle.setYear(years.get(i));
            vehicle.setClasse(classes.get(i));
            vehicle.setManufacturer(manufacturers.get(i));
            vehicle.setValue(values.get(i));
            vehicle.setCreatedAt(LocalDateTime.now());
            
            vehicles.add(vehicle);
        }

        return vehicles;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = ServletUtil.getURI(req);
        
        switch (uri) {
		case "/vehicles":
			index(req, resp);			
			break;
		case "/vehicles/new":
			setFormAttributes(req);
			req.getRequestDispatcher("/WEB-INF/vehicles/new.jsp").forward(req, resp);
			break;
		case "/vehicles/edit":
			edit(req, resp);
			break;
		case "/vehicles/delete":
			destroy(req, resp);
			break;
		case "/vehicles/store":
			store(req, resp);
			break;
		case "/vehicles/update":
			update(req, resp);
			break;
		default:
			sendRedirectTo(req, resp, "/index.jsp");
			break;
		}
    }

	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idToSearch = req.getParameter("id");
		
		if(idToSearch == null || idToSearch.isEmpty()) {
			addSessionAttribute(req, "message", "Código inválido ou não informado");
			addSessionAttribute(req, "severity", "danger");
			sendRedirectTo(req, resp, "/vehicles");
			return;
		}
		Optional<Vehicle> vehicle = vehicles.stream().filter(v -> v.getId() == Long.valueOf(idToSearch)).findFirst();
		
		if(!vehicle.isPresent()) {
			addSessionAttribute(req, "message", "Veículo não encontrado");
			sendRedirectTo(req, resp, "/vehicles");
			return;
		}
		
		req.setAttribute("vehicle", vehicle.get());
		setFormAttributes(req);
		req.getRequestDispatcher("/WEB-INF/vehicles/edit.jsp").forward(req, resp);
	}

	private void setFormAttributes(HttpServletRequest req) {
		req.setAttribute("classes", VehicleType.values());
		req.setAttribute("manufacturers", Manufacturer.values());
	}

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    	Vehicle parsedVehicle = parseVehicle(req);
    	
    	vehicles.removeIf(v -> v.getId() == parsedVehicle.getId());
    	vehicles.add(parsedVehicle);
    	vehicles.sort(Comparator.comparing(Vehicle::getId));
    	
    	addSessionAttribute(req, "message", "Veículo atualizado com sucesso!");
    	addSessionAttribute(req, "severity", "success");
    	
    	sendRedirectTo(req, resp, "/vehicles");
	}

	private void destroy(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    	Long idToRemove = Long.valueOf(req.getParameter("vehicleId"));
		
    	vehicles = vehicles.stream().filter(vehicle -> vehicle.getId() != idToRemove).collect(Collectors.toList());
    	
    	addSessionAttribute(req, "message", "Veículo removido com sucesso");
    	addSessionAttribute(req, "severity", "success");
    	
    	sendRedirectTo(req, resp, "/vehicles");
	}

	private void addSessionAttribute(HttpServletRequest req, String name, Object value) {
		HttpSession session = req.getSession(false);
    	session.setAttribute(name, value);
	}

	private void sendRedirectTo(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
		resp.sendRedirect(req.getContextPath() + path);
	}

	private void store(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Vehicle parsedVehicle = parseVehicle(req);
		
		vehicles.add(parsedVehicle);
		
		addSessionAttribute(req, "message", "Veículo cadastrado com sucesso!");
		addSessionAttribute(req, "severity", "success");
		
    	sendRedirectTo(req, resp, "/vehicles");
	}

	private Vehicle parseVehicle(HttpServletRequest req) {
		Long idReq;
		String model = req.getParameter("txtVehicleModel");
		int year = Integer.valueOf(req.getParameter("nbrVehicleYear"));
		Manufacturer manufacturer = Manufacturer.valueOf(req.getParameter("slcVehicleManufacturer"));
		VehicleType classe = VehicleType.valueOf(req.getParameter("slcVehicleType"));
		String valueReq = req.getParameter("vlrVehicleValue");
		
		if(valueReq.contains(".") || valueReq.contains(",")) {
			valueReq = valueReq.replaceAll("\\D", "");
			valueReq = valueReq.substring(0, valueReq.length() - 2) + "." + valueReq.substring(valueReq.length() - 2);			
		}

		double value = Double.valueOf(valueReq);
		
		try {
			idReq = Long.valueOf(req.getParameter("idVehicle").trim());
		} catch (NumberFormatException e) {
			idReq = id++;
		}
		
		Vehicle vehicle = new Vehicle();
		
		vehicle.setId(idReq);
		vehicle.setClasse(classe);
		vehicle.setYear(year);
		vehicle.setManufacturer(manufacturer);
		vehicle.setValue(value);
		vehicle.setModel(model);
		vehicle.setCreatedAt(LocalDateTime.now());
		
		return vehicle;
	}

	private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String year = req.getParameter("nbrYear");
		
		if(year == null || year.isEmpty()) {
			req.setAttribute("vehiclesList", vehicles);		
			req.setAttribute("filter", false);
		} else {
			List<Vehicle> filteredVehicles = vehicles
					.stream()
					.filter(vehicle -> vehicle.getYear() == Integer.valueOf(year))
					.collect(Collectors.toList());
			
			req.setAttribute("vehiclesList", filteredVehicles);
			req.setAttribute("filter", true);
		}
		
		req.getRequestDispatcher("/vehicles.jsp").forward(req, resp);
	}
}
