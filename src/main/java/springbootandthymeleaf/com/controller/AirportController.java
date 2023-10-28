package springbootandthymeleaf.com.controller;

import javax.validation.Valid;

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

import springbootandthymeleaf.com.entity.Airport;
import springbootandthymeleaf.com.service.AirportService;

@Controller
public class AirportController {
private static final Logger log = LogManager.getLogger(AirportController.class);

@Autowired
private AirportService airportservice;	
	
	
@GetMapping("/")
public String showHomePage() {
	
	return "indexNew";
}

//<div th:each="airport, iterStat:${airports}">
@GetMapping("/airports")
public String showAirportLists(@RequestParam(defaultValue="0") int pageNo, Model model) {
	 model.addAttribute("airports", airportservice.getAllAirportPaged(pageNo));
	 model.addAttribute("currentPage", pageNo);
	
	return "airports";
}



@GetMapping("/airport/new")
public String showAddAirportPage(Model model ) {
	model.addAttribute("airport", new Airport());
  return "newAirport";	
	
}

@PostMapping("/airport/new")
public String saveAirport(@Valid @ModelAttribute("airport") Airport airport, BindingResult bindingresult, Model model) {
	
	if(bindingresult.hasErrors()) {
		model.addAttribute("errors    ", bindingresult.getAllErrors());
		model.addAttribute("airport", new Airport());
		return "newAirport";
	}
	
	airportservice.saveAirport(airport);
	model.addAttribute("airports", airportservice.getAllAirportPaged(0));
	model.addAttribute("currentPage", 0);
	
	
	return "airports";
}

@GetMapping("/airport/delete")
public String deleteAirport(@RequestParam("airportId") Integer airportId, Model model) {
	
	if(airportId !=null) {
		airportservice.deletAirportById(airportId);
		model.addAttribute("airports", airportservice.getAllAirportPaged(0));
		model.addAttribute("currentPage", 0);
		
		return "airports";
	}
	
	return null;
}


}
