package springbootandthymeleaf.com.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import springbootandthymeleaf.com.entity.Airport;
import springbootandthymeleaf.com.entity.Flight;
import springbootandthymeleaf.com.entity.Passanger;
import springbootandthymeleaf.com.service.AircraftService;
import springbootandthymeleaf.com.service.AirportService;
import springbootandthymeleaf.com.service.FlightService;
import springbootandthymeleaf.com.service.PassangerService;

@Controller
@SessionAttributes("flighInofs")
public class FlightController {
	public static final Logger log = LogManager.getLogger(FlightController.class);

	@Autowired
	private AircraftService aircraftService;

	@Autowired
	private AirportService airportService;

	@Autowired
	private FlightService flightService;

	@Autowired
	private PassangerService passangerService;

	@GetMapping("/flights")
	public String getAllFlights(@RequestParam(defaultValue = "0") int pageNo, Model model) {

		model.addAttribute("flights", flightService.findAllFlightsPaged(pageNo));
		model.addAttribute("flighInofs", flightService.findAllFlights());
		model.addAttribute("currentPage", pageNo);
		
		System.out.println("============Practice Start========================");
		
		Double averagaCharge = flightService.findAllFlights().stream()
				.mapToDouble(Flight::getFlightCharge).average().orElse(0.00);
		
		
		List<Flight> FlightListGraterThanAverage = flightService.findAllFlights().stream()
				.filter(f-> f.getFlightCharge()> averagaCharge).collect(Collectors.toList());
		
		Map<String, Double> getTotalChargeEveryFlightNumber = flightService.findAllFlights()
		        .stream()
		        
		        .collect(Collectors.groupingBy(
		                Flight::getFlightNumber,
		                Collectors.summarizingDouble(Flight::getFlightCharge)
		        ))
		        .entrySet().stream()
		        .filter(entry -> entry.getValue().getSum() > 50000.00)
		        .sorted(Comparator.comparing(
		                entry -> entry.getValue().getSum(),
		                Comparator.nullsLast(Double::compareTo)
		        ))
		        .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                entry -> entry.getValue().getSum()
		        ));
		
		
		for(Map.Entry<String, Double> e: getTotalChargeEveryFlightNumber.entrySet()) {
			
			System.out.println("key=:"+e.getKey() +" value=:"+e.getValue());
		}
		
		List<String> allKeylist = getTotalChargeEveryFlightNumber.entrySet().stream()
				.map(e-> e.getKey()).collect(Collectors.toList());
		
		System.out.println("allKeylist===:"+allKeylist.toString());
		

        List<Double> allAssendingValueList= getTotalChargeEveryFlightNumber.entrySet()
        		.stream()
        		.filter(e-> e.getValue() != null)
        		.map(e-> e.getValue())
        		
        		  .sorted(Comparator.nullsLast(Comparator.naturalOrder()))
        		  .collect(Collectors.toList());
		
		


		System.out.println("=========allvalueList:"+allAssendingValueList);

		
		List<Double> allvalueDessendingList = getTotalChargeEveryFlightNumber.entrySet().stream()
				.map(e-> e.getValue())
				.sorted(Comparator.nullsLast(Comparator.reverseOrder()))
				.collect(Collectors.toList());
		
		System.out.println("=========allvalueDessendingList:"+allvalueDessendingList);
	
		
		System.out.println("================k==========FlightListGraterThanAverage:"+FlightListGraterThanAverage);
						 
						 
		System.out.println("============Practice End========================");
		
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
			@RequestParam("departureAirport") int departureAirportId,
			@RequestParam("destinationAirport") int destinationAirportId, @RequestParam("aircraft") Long aircraftId,
			@RequestParam("arivalTime") String arrivalTime, @RequestParam("departureTime") String departureTime,
			Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("errors", bindingResult.getAllErrors());
			model.addAttribute("flight", new Flight());
			model.addAttribute("aircrafts", aircraftService.findAllAircraftLists());
			model.addAttribute("airports", aircraftService.findAllAircraftLists());
			return "newFlight";

		}

		if (departureAirportId == destinationAirportId) {
			model.addAttribute("saveFlightError", "departureAirport and destinationAirport can't be same");
			model.addAttribute("flight", new Flight());
			model.addAttribute("aircrafts", aircraftService.findAllAircraftLists());
			model.addAttribute("airports", airportService.getAllAirports());
			return "newFlight";
		}

		flight.setAircraft(aircraftService.findByAircraftId(aircraftId));
		flight.setDepartureAirport(airportService.findById(departureAirportId));
		flight.setDestinationAirport(airportService.findById(destinationAirportId));
		flight.setArivalTime(arrivalTime);
		flight.setDepartureTime(departureTime);

		flightService.saveFlight(flight);

		model.addAttribute("flights", flightService.findAllFlightsPaged(0));
		model.addAttribute("currentPage", 0);
		return "flights";
	}

	@PostMapping("/flight/newway")
	public String saveFlightDeferentWay(@ModelAttribute("flight") Flight flight, HttpServletRequest request,
			Model model, BindingResult bindingResult) {
		Map<String, String[]> requestParamMap = request.getParameterMap();

		List<String> paramKeys = requestParamMap.entrySet().stream().map(e -> e.getKey()).collect(Collectors.toList());

		List<String> allKeys = requestParamMap.keySet().stream().collect(Collectors.toList());

		List<String> allParamValues = requestParamMap.values().stream().flatMap(Arrays::stream)
				.collect(Collectors.toList());

		List<String> allpamvls = requestParamMap.entrySet().stream().map(e -> e.getValue()).flatMap(Arrays::stream)
				.collect(Collectors.toList());

		log.info("DeparuteTime=of ==Flight==Object==Request===" + flight.getDepartureTime());
		log.info("ArivalTime of Flight Object Request:========" + flight.getArivalTime());

		String aircraftId = requestParamMap.get("aircraft")[0];
		String depurtureAirportId = requestParamMap.get("departureAirport")[0];
		String destinatinAiportId = requestParamMap.get("destinationAirport")[0];
		String depuretureDate = requestParamMap.get("departureDate")[0];
		String depuretureTime = requestParamMap.get("departureTime")[0];
		log.info("DeparuteTime=of ===Request=Param,==" + depuretureTime);
		String arivalDate = requestParamMap.get("arivalDate")[0];
		String arivalTime = requestParamMap.get("arivalTime")[0];
		log.info("ArivalTime of  Request:====Param====" + arivalTime);

		String flightNumber = requestParamMap.get("flightNumber")[0];
		String flightCharge = requestParamMap.get("flightCharge")[0];

		if (bindingResult.hasErrors()) {
			model.addAttribute("errors", bindingResult.getAllErrors());
			model.addAttribute("flight", new Flight());
			model.addAttribute("aircrafts", aircraftService.findAllAircraftLists());
			model.addAttribute("airports", airportService.getAllAirports());
			return "newFlight";

		}
		if (depurtureAirportId.equals(destinatinAiportId)) {
			model.addAttribute("saveFlightError", "DepartureAirport And DestinationAirport Can't be same...!");
			model.addAttribute("flight", new Flight());
			model.addAttribute("aircrafts", aircraftService.findAllAircraftLists());
			model.addAttribute("airports", airportService.getAllAirports());
			return "newFlight";

		}

		flight.setAircraft(aircraftService.findByAircraftId(Long.valueOf(aircraftId)));
		flight.setDepartureAirport(airportService.findById(Integer.valueOf(depurtureAirportId)));
		flight.setDestinationAirport(airportService.findById(Integer.valueOf(destinatinAiportId)));

		flightService.saveFlight(flight);

		return "redirect:/flights";
	}

	@GetMapping("/flight/delete")
	public String deleteFlight(@RequestParam("flightId") Long flightId, Model model) {

		flightService.deletFligthById(flightId);

		model.addAttribute("flights", flightService.findAllFlightsPaged(0));
		model.addAttribute("currentPage", 0);

		return "flights";

	}

	@GetMapping("/flight/book")
	public String showBookFlightPage(Model model) {
		model.addAttribute("airports", airportService.getAllAirports());

		return "bookFlight";
	}

	@PostMapping("flight/book")
	public String showPageForSearchFlight(HttpServletRequest request, Model model) {

		Map<String, String[]> requestParam = request.getParameterMap();

		String departureAirportId = requestParam.get("departureAirportId")[0];
		String destinationAirportId = requestParam.get("destinationAirportId")[0];
		String departureTime = requestParam.get("departuredateTime")[0];

		if (departureAirportId.equals(destinationAirportId)) {
			model.addAttribute("sameAiportError", "DepartmentAiprtId and DestinationAirportId can't be same");
			model.addAttribute("airports", airportService.getAllAirports());

			return "bookFlight";
		}
		Airport departureAirport = airportService.findById(Integer.valueOf(departureAirportId));
		Airport destinationAiport = airportService.findById(Integer.valueOf(destinationAirportId));
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate date = LocalDate.parse(departureTime, formater);

		List<Flight> flights = flightService.findFlightsByDepartureAirportAndDestinationAirportAndDepartureDate(
				departureAirport, destinationAiport, date);

//	List<Flight> checkListWithoutPassangerFlightId= flights.stream()
//			.filter(flight-> flight.getPassangers().stream()
//					.noneMatch(passanger-> passanger.getFlight().getFlightId() != null && 
//					          passanger.getFlight().getFlightId()==flight.getFlightId())).collect(Collectors.toList());
//	
//	List<Flight> checkAllMatch= flights.parallelStream()
//			.filter(f-> f.getPassangers().stream()
//					.allMatch(p-> p.getFlight().getFlightId()==null && p.getFlight().getFlightId() != f.getFlightId()))
//			.collect(Collectors.toList());
//	log.info("checkAllMatch size==================:"+checkAllMatch.size());
//	
//	
//	log.info("checkListWithoutPassangerFlightId size==================:"+checkListWithoutPassangerFlightId.size());
		if (flights.isEmpty()) {
			model.addAttribute("flightListEmpty", "Result not found ...........!");
			model.addAttribute("airports", airportService.getAllAirports());
			return "bookFlight";
		}
		model.addAttribute("flights", flights);
		log.info("Flights size==================:" + flights.size());

		LocalDate d = flights.stream().map(p -> p.getArivalDate()).findFirst().get();

		log.info("==============ArivalDate vlue =======================" + d);

		return "bookFlight";
	}

	@GetMapping("/flight/book/new")
	public String showCutomerInfoPage(@RequestParam("flightId") Long flightId, Model model) {
		model.addAttribute("flightId", flightId);
		model.addAttribute("passanger", new Passanger());

		return "newPassanger";
	}

	@PostMapping("/flight/book/new")
	public String bookFlight(@Valid @ModelAttribute("passanger") Passanger passanger,
			@RequestParam("flightId") Long flightId, BindingResult bindingResult, Model model,
			@RequestParam(value = "passangerId", required = false) Long passangeId) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("errorFields", bindingResult.getAllErrors());
			model.addAttribute("passanger", new Passanger());
			return "redirect:/flight/book/new";
		}

		Flight flight = flightService.findByflightId(flightId);

		Passanger passanger2 = passanger;
		passanger2.setFlight(flight);

		passangerService.saveOrUpdatePassanger(passangeId, passanger2);
		model.addAttribute("passenger", passanger2);
		// model.addAttribute("passenger", passangerService.findAllPassangers());

		return "conFirmPage";

	}

	@GetMapping("/flight/book/verify")
	public String showVeriFyBookingPage() {
		return "verifyBooking";

	}

	@PostMapping("/flight/book/verify")
	public String flightBerifyBookingResult(HttpServletRequest request, Model model, Principal principal) {
		
		System.out.println("Login User Name=====:" + principal.getName());
		
		Map<String, String[]> requestParam = request.getParameterMap();
		String flightId = requestParam.get("flightId")[0];
		String passangerId = requestParam.get("passengerId")[0];
		Passanger verifyingPassanger = null;

		
		if (StringUtils.isNotBlank(flightId) && StringUtils.isNoneBlank(passangerId)) {
			Flight verifyingFlight = flightService
					.findAllFlights().stream().filter(f -> f.getFlightId() == Long.valueOf(flightId)).filter(f -> f
					  .getPassangers().stream().anyMatch(p -> p.getPassangerId() == Long.valueOf(passangerId)))
					    .findFirst().orElse(null);
			
			if (verifyingFlight != null) {
				verifyingPassanger = verifyingFlight.getPassangers().stream()
						.filter(p -> p.getPassangerId() == Long.valueOf(passangerId))
						    .findFirst().orElse(null);
			}
			
			 if (verifyingFlight != null && verifyingPassanger != null) {
				model.addAttribute("flight", verifyingFlight);
				model.addAttribute("passenger", verifyingPassanger);
				return "verifyBooking";
			 } else {
				model.addAttribute("notFound", " Booking Flight Not Found....!!");
				return "verifyBooking";
			 }
			
		} else {
			model.addAttribute("notFound", " Booking Flight Not Found....!!");
			return "verifyBooking";
		}
	}

}
