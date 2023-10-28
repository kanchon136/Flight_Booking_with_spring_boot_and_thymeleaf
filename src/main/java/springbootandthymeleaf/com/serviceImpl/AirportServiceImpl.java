package springbootandthymeleaf.com.serviceImpl;

 
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import springbootandthymeleaf.com.entity.Airport;
import springbootandthymeleaf.com.repository.AirportRepository;
import springbootandthymeleaf.com.service.AirportService;

@Service
public class AirportServiceImpl  implements AirportService{
	
	private AirportRepository airportRepo;
	

	public AirportServiceImpl(AirportRepository airportRepo) {
		super();
		this.airportRepo = airportRepo;
	}

	
	@Override
	public List<Airport> getAllAirports() {
		 List<Airport> listByCountry = airportRepo.findAll().stream()
				 .filter(a -> "Bangladesh".equalsIgnoreCase(a.getCountry()))
				   .distinct()
				    .sorted(Comparator.comparing(Airport::getAirportName))
				     .collect(Collectors.toList());
		 
		return airportRepo.findAll().parallelStream()
				.sorted(Comparator.comparing(Airport::getAirportName)).collect(
						Collectors.toList());
	}

	@Override
	public Airport findById(Integer airportId) {
		
		 return airportRepo.findAll().stream()
				 .filter(airport-> airport.getAirportId()==airportId)
				 .findFirst().orElse(null);
	}

	@Override
	public Page<Airport> getAllAirportPaged(int pageNum) {
		 
		return airportRepo.findAll(PageRequest.of(pageNum, 5, Sort.by("airportId")));
	}

	@Override
	public Airport saveAirport(Airport airport) {
	 
		return airportRepo.save(airport);
	}

	@Override
	public void deletAirportById(Integer airportId) {
		
		Airport airport = airportRepo.findAll()
				.stream().filter(a-> a.getAirportId().equals(airportId))
				.findFirst().orElse(null);
		if(airport !=null) {
			airportRepo.delete(airport);
		}
		
		 
		
	}

	@Override
	public Airport findByName(String airportName) {
	 
		return airportRepo.findAll().stream()
				.filter(a-> a.getAirportName().equalsIgnoreCase(airportName))
				.findFirst().orElse(null);
	}

	@Override
	public List<Airport> getAllAirportsByCity(String city) {
	 
		return airportRepo.findAll().stream()
				.filter(a-> a.getCity().equalsIgnoreCase(city))
				     .sorted(Comparator.comparing(Airport:: getCity))
			             .collect(Collectors.toList());
	}

	@Override
	public List<Airport> getAllAirportByCountry(String country) {
		
		List<Airport> airportsByCountryAndSortByCountryName= airportRepo.findAll()
				.stream().filter(a-> a.getCountry().contentEquals(country))
				   .distinct().sorted(Comparator.comparing(Airport::getCountry)
						   .thenComparing(Comparator.comparing(Airport::getCity)))
				      .collect(Collectors.toList());
		 
		return airportsByCountryAndSortByCountryName;
	}

	@Override
	public Airport getAirportByCountry(String country) {
		 
		return airportRepo.findAirportByCity(country);
	}

}
