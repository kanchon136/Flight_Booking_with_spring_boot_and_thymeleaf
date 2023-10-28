
package springbootandthymeleaf.com.service;

import java.util.List;

import org.springframework.data.domain.Page;

import springbootandthymeleaf.com.entity.Aircraft;

public interface AircraftService {
	
	Page<Aircraft> findAllAircraftPaged(int pageNo);
	Aircraft findByAircraftId(Long aircraftId);
	Aircraft saveAircraft(Aircraft aircraft);
	List<Aircraft> findAllAircraftLists();
	List<Aircraft> findAllByManufacturer(String manufacturer);
	Aircraft EditAircraftById(Integer aircraftId, Aircraft aircraft);
	void deleteAircraftById(Long aircraftId);

}
