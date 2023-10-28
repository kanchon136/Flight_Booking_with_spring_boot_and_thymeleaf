package springbootandthymeleaf.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootandthymeleaf.com.entity.Aircraft;

public interface AircraftRepository extends JpaRepository<Aircraft, Long>{
	
	Aircraft findByManufacturer(String maufacturer);
	Optional<Aircraft> findAircraftByManufacturer(String manufacture);
	

}
