package springbootandthymeleaf.com.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import springbootandthymeleaf.com.entity.Passanger;

public interface PassangerService {
	
	Page<Passanger> findAllPassangersByPagedNo(int pageNo);
	
	Collection<? extends Passanger> findAllPassangersByFirstNameAndPhoneNumberAndEmail(String firstName, String phoneNo,String email);
	
	Passanger findByPassangerId(Long passangerId);
	
	Optional<Passanger> findByPassangerIdAndPassportNo(Long passangerId, String passport);
	
	Passanger saveOrUpdatePassanger( Long passangerId,Passanger passanger);
	
	List<Passanger> findAllPassangersByNameAndPhone(String name, String phone);
	
	void deletePassangerById(Long passangerId);
	
	Passanger updatePassanger(Long passangerId, Passanger passanger);
	

	
	

}
