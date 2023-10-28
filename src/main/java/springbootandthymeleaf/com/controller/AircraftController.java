package springbootandthymeleaf.com.controller;

 

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springbootandthymeleaf.com.entity.Aircraft;
import springbootandthymeleaf.com.service.AircraftService;

@Controller
public class AircraftController {
	private static final Logger log = LogManager.getLogger(AircraftController.class);
	
private AircraftService aircraftservice;

	
public AircraftController(AircraftService aircraftservice) {
	super();
	this.aircraftservice = aircraftservice;
	}
	

@GetMapping("/aircrafts")
public String getAllAircraftPaged(@RequestParam(defaultValue="0") int pageNO, Model model) {
	
       	model.addAttribute("aircrafts", aircraftservice.findAllAircraftPaged(pageNO));
       	model.addAttribute("currentPage", pageNO);
        
		return "aircrafts";
	}

@GetMapping("/aircraft/new")
public String addAircraftpaged(Model model) {
     model.addAttribute("aircraft", new Aircraft());	
	return "newAircraft";
	
}


@PostMapping("/aircraft/new")
public String saveAricraft(@Valid @ModelAttribute("aircraft") Aircraft aircraft,BindingResult bindingResult, Model model) {
	
	if(bindingResult.hasErrors()) {
		model.addAttribute("errors", bindingResult.getAllErrors());
		model.addAttribute("aircraft", new Aircraft());
	}
	aircraftservice.saveAircraft(aircraft);
	model.addAttribute("aircrafts", aircraftservice.findAllAircraftPaged(0));
	model.addAttribute("currentPage", 0);
	
	
	return "aircrafts";
}

@GetMapping("/aircraft/delete")
public String aircraftDelete(@RequestParam("aircraftId") Long aircraftId, Model model) {
	if(aircraftId.toString() !=null) {
		aircraftservice.deleteAircraftById(aircraftId);
		
		model.addAttribute("aircrafts", aircraftservice.findAllAircraftPaged(0));
		model.addAttribute("currentPage", 0);
	}
	return "aircrafts";
}
		
		


}
