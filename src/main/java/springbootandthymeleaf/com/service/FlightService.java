package springbootandthymeleaf.com.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import springbootandthymeleaf.com.entity.Airport;
import springbootandthymeleaf.com.entity.Flight;

public interface FlightService {
	
	
	Page<Flight> findAllFlightsPaged(int pageNo);
	Optional<Flight> findByFlightId(Long flightId);
	Flight findByflightId(Long id);
	Flight saveFlight(Flight flight);
	
	void deletFligthById(Long flightId);
	
	Flight updateFlight(Long flightId, Flight flight);
	
	List<Flight> findAllFlights();
	
	List<Flight> findFlightsByDepartureAirportAndDestinationAirportAndDepartureDate(Airport deparAirport, Airport destiAirport, LocalDate departureDate);
	

}
