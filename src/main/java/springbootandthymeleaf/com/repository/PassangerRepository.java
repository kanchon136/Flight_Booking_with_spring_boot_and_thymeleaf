package springbootandthymeleaf.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootandthymeleaf.com.entity.Passanger;

public interface PassangerRepository extends JpaRepository<Passanger, Long>{
	
	Optional<Passanger> findByPassangerId(Long passangerId);
	Optional<Passanger> findByFirstName(String firstName);
	boolean existsByFirstName(String firstName );
	boolean existsByPassangerId(Long passangerId);
	

}
