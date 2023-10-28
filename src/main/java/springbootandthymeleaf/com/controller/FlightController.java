package springbootandthymeleaf.com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import springbootandthymeleaf.com.entity.Flight;
import springbootandthymeleaf.com.service.AircraftService;
import springbootandthymeleaf.com.service.AirportService;
import springbootandthymeleaf.com.service.FlightService;
import springbootandthymeleaf.com.service.PassangerService;

@Controller
public class FlightController {
	
	
	@Autowired
	private AircraftService aircraftService;
	
	@Autowired
	private AirportService airportService;
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PassangerService passangerService;
	
	
	@GetMapping("/flights")
	public String getAllFlights(@RequestParam(defaultValue = "0") int pageNo,  Model model ) {
		
		model.addAttribute("flights", flightService.findAllFlightsPaged(pageNo));
		model.addAttribute("currentPage", pageNo);
		return "flights";
	}
	
	
	@GetMapping("/flight/new")
	public String showFlightPage(Model model) {
		model.addAttribute("flight", new Flight());
		model.addAttribute("aircrafts", aircraftService.findAllAircraftLists());
		model.addAttribute("airports", airportService.getAllAirports());
		return "newFlight";
		
	}
	
	@PostMapping("/flight/new")
	public String saveFlight(@Valid @ModelAttribute("flight") Flight flight, BindingResult bindingResult,
			                  @RequestParam("departureAirport") int departureAirport,
			                  @RequestParam("destinationAirport") int destinationAirport,
			                  @RequestParam("aircraft") Long aircraft,
			                  @RequestParam("arivalTime") String arrivalTime,
			                  @RequestParam("departureTime") String departureTime,
			                  Model model) { 
    	
	  if(bindingResult.hasErrors()){
		 model.addAttribute("errors", bindingResult.getAllErrors());
		 model.addAttribute("flight", new Flight());
		 model.addAttribute("aircrafts", aircraftService.findAllAircraftLists());
		 model.addAttribute("airports", aircraftService.findAllAircraftLists());
		 return "newFlight";
		  
	  }
	  
	  if(departureAirport==destinationAirport) {
		  model.addAttribute("saveFlightError", "departureAirport and destinationAirport can't be same");
		  model.addAttribute("flight", new Flight());
		  model.addAttribute("aircrafts", aircraftService.findAllAircraftLists());
		  model.addAttribute("airports", aircraftService.findAllAircraftLists());
		  return "newFlight";
	  }
	  
	  
	   flight.setAircraft(aircraftService.findByAircraftId(aircraft));
	   flight.setDepartureAirport(airportService.findById(departureAirport));
	   flight.setDestinationAirport(airportService.findById(destinationAirport));
	   flight.setArivalTime(arrivalTime);
	   flight.setDepartureTime(departureTime);
	   
	   flightService.saveFlight(flight);
	 
	   model.addAttribute("flights", flightService.findAllFlightsPaged(0));
	   model.addAttribute("currentPage", 0);
		return "flights";
	}
	
	@GetMapping("/flight/delete")
	public String deleteFlight(@RequestParam("flightId") Long flightId , Model model) {
		
		flightService.deletFligthById(flightId);
		
		model.addAttribute("flights", flightService.findAllFlightsPaged(0));
		model.addAttribute("currentPage", 0);
		
		return "flights";
		
	}
	
	@GetMapping("/flight/book")
	public String showBookFlightPage(Model model) {
		model.addAttribute("airposrts", airportService.getAllAirports());
		
		return "bookFlight";
	}
	
	
	

}
