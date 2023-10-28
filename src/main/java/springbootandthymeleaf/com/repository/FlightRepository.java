package springbootandthymeleaf.com.repository;

 
 

import org.springframework.data.jpa.repository.JpaRepository;
import springbootandthymeleaf.com.entity.Flight;
public interface FlightRepository extends JpaRepository<Flight, Long>{
	
//	List<Flight> findBydepartureAirportEqualsAnddestinatonAirportEqualsAnddepartureDateEquals(Airport depAirport, Airport desAirport,LocalDate depDate);

}
