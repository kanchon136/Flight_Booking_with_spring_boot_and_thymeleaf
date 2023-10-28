
package springbootandthymeleaf.com.serviceImpl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import springbootandthymeleaf.com.entity.Airport;
import springbootandthymeleaf.com.entity.Flight;
import springbootandthymeleaf.com.repository.FlightRepository;
import springbootandthymeleaf.com.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService{
	
	@Autowired
	private FlightRepository flightRepo;
	

	@Override
	public Page<Flight> findAllFlightsPaged(int pageNo) {
		 
		return flightRepo.findAll(PageRequest.of(pageNo, 5, Sort.by("flightId")));
	}

	@Override
	public Optional<Flight> findByFlightId(Long flightId) {
		 
		return flightRepo.findAll().parallelStream()
				.filter(f-> f.getFlightId()==flightId)
				.findFirst();
	}

	@Override
	public Flight findByflightId(Long id) {
		 
		return flightRepo.findById(id).get();
	}

	@Override
	public Flight saveFlight(Flight flight) {
		 
		return flightRepo.save(flight);
	}

	
	@Override
	public void deletFligthById(Long flightId) {
		if(StringUtils.isNotBlank(flightId.toString())) {
			Optional<Flight> flight = flightRepo.findAll().parallelStream()
					.filter(f-> f.getFlightId().toString().contentEquals(String.valueOf(flightId)) )
					.findFirst();
			
			flightRepo.delete(flight.get());
		}
	 
	}

	@Override
	public Flight updateFlight(Long flightId, Flight flight) {
		
		Optional<Flight> flt = flightRepo.findAll().parallelStream()
				.filter(f-> f.getFlightId()==flightId)
				.findFirst();
		
		if(flt.isPresent()) {
			flt.get().setFlightId(flight.getFlightId());
			flt.get().setFlightNumber(flight.getFlightNumber());
			flt.get().setAircraft(flight.getAircraft());
			flt.get().setArivalDate(flight.getArivalDate());
			flt.get().setArivalTime(flight.getArivalTime());
			flt.get().setDepartureAirport(flight.getDepartureAirport());
			flt.get().setDepartureDate(flight.getDepartureDate());
			flt.get().setDepartureTime(flight.getDepartureTime());
			flt.get().setDestinationAirport(flight.getDestinationAirport());
		//	flt.get().setPassangers(flight.getPassangers());
			
			flightRepo.save(flt.get());  
			
		}
		 
		return flt.get();
	}

	@Override
	public List<Flight> findAllFlights() {
		 
		return flightRepo.findAll().parallelStream()
				.distinct().sorted(Comparator.comparing(Flight::getDepartureDate)
						.thenComparing(Flight:: getFlightNumber))
				.collect(Collectors.toList());
	}

	@Override
	public List<Flight> findFlightsByDepartureAirportAndDestinationAirportAndDepartureDate(Airport deparAirport,
			Airport destiAirport, LocalDate departureDate) {
		
		List<Flight> flights = flightRepo.findAll().parallelStream()
				.filter(f-> f.getDepartureAirport().getAirportId()== deparAirport.getAirportId()
				   && f.getDestinationAirport().getAirportId()==destiAirport.getAirportId()
				   && f.getDepartureDate().equals(departureDate))
				
				.sorted(Comparator.comparing(Flight::getFlightNumber)
						.thenComparing(Flight::getFlightId))
				.collect(Collectors.toList());
		 
		return flightRepo.findAll().parallelStream()
			.filter(f-> f.getDepartureAirport().getAirportId()==deparAirport.getAirportId()
			         && f.getDestinationAirport().getAirportId()==destiAirport.getAirportId()
			         && f.getDepartureDate().equals(departureDate))
			  .sorted(Comparator.comparing(Flight::getFlightNumber, Comparator.reverseOrder())
					.thenComparing(Flight::getDepartureDate, Comparator.reverseOrder()))
			.collect(Collectors.toList());
	}
	
	
	

}
