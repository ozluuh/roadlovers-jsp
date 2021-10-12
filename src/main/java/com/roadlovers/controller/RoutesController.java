package com.roadlovers.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.roadlovers.model.Manufacturer;
import com.roadlovers.model.Vehicle;
import com.roadlovers.model.VehicleType;
import com.roadlovers.util.ServletUtil;

@WebServlet(
    description = "Requests/Responses of Vehicles",
    urlPatterns = { "/vehicles", "/vehicles/new", "/vehicles/edit" }
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
            vehicle.setVehicleType(classes.get(i));
            vehicle.setManufacturer(manufacturers.get(i));
            vehicle.setValue(values.get(i));
            
            vehicles.add(vehicle);
        }

        return vehicles;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = ServletUtil.getURI(req);
        
        switch (uri) {
		case "/vehicles":
			if(req.getMethod().equals("POST")) {
				store(req, resp);
	            return;
			}
			index(req, resp);			
			break;
		case "/vehicles/new":
			req.getRequestDispatcher("/WEB-INF/vehicles/new.jsp").forward(req, resp);
			break;
		case "/vehicles/edit":
			req.getRequestDispatcher("/WEB-INF/vehicles/edit.jsp").forward(req, resp);
			break;
		default:
			resp.sendRedirect("index.jsp");
			break;
		}
    }

    private void store(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	resp.sendRedirect("index.jsp");
	}

	private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("vehicles", vehicles);
		req.getRequestDispatcher("vehicles.jsp").forward(req, resp);
	}
}
