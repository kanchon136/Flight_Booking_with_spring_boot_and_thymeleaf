package springbootandthymeleaf.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootandthymeleaf.com.entity.Airport;

public interface AirportRepository extends JpaRepository<Airport, Integer>{

	Airport findByAirportName(String airportName);
	Airport findAirportByCity(String cityname);
}
