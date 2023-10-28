package springbootandthymeleaf.com.service;

import java.util.List;

import org.springframework.data.domain.Page;

import springbootandthymeleaf.com.entity.Airport;

public interface AirportService {

	List<Airport> getAllAirports();
	Airport findById(Integer airportId);
	Page<Airport> getAllAirportPaged(int pageNum);
	Airport saveAirport(Airport airport);
	void deletAirportById(Integer airportId);
	
	Airport findByName(String airportName);
	
	List<Airport> getAllAirportsByCity(String city);
	
	List<Airport> getAllAirportByCountry(String country);
	
	Airport getAirportByCountry(String country);
	
	
}
