package springbootandthymeleaf.com.serviceImpl;

import java.util.Collection;
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

import springbootandthymeleaf.com.entity.Passanger;
import springbootandthymeleaf.com.repository.PassangerRepository;
import springbootandthymeleaf.com.service.PassangerService;
@Service
public class PassangerServiceImpl implements PassangerService {

 @Autowired
 private PassangerRepository passangerRepo;
	
	
	

	@Override
	public Page<Passanger> findAllPassangersByPagedNo(int pageNo) {
		 
		return passangerRepo.findAll(PageRequest.of(pageNo, 5, Sort.by("passangerId","phoneNumber")));
	}

	@Override
	public Collection<? extends Passanger> findAllPassangersByFirstNameAndPhoneNumberAndEmail(String firstName,
			                                                                    String phoneNo, String email) {
		
		List<Passanger> list = passangerRepo.findAll().parallelStream()
				.filter(p-> p.getFirstName().equalsIgnoreCase(firstName)
						&& p.getPhoneNumber().equalsIgnoreCase(phoneNo)
						&& p.getEmail().equalsIgnoreCase(email))
				 .distinct()
				    .sorted(Comparator.comparing(Passanger::getFirstName)
				    		.thenComparing(Passanger::getPhoneNumber)
				    		.thenComparing(Passanger::getEmail))
				    .collect(Collectors.toList());
		 
		return list;
	}

	@Override
	public Passanger findByPassangerId(Long passangerId) {
		Optional<Passanger> passmger = passangerRepo.findAll().parallelStream()
				.filter(p -> p.getPassangerId().toString().contentEquals(String.valueOf(passangerId)))
				.findFirst();
		 
		return passmger.get();
	}

	@Override
	public Optional<Passanger> findByPassangerIdAndPassportNo(Long passangerId, String passport) {
		 
		return passangerRepo.findAll().parallelStream()
				.filter(p-> p.getPassangerId()==passangerId &&
				p.getPassportNumber().contentEquals(passport))
				.findFirst();
	}

	@Override
	public Passanger saveOrUpdatePassanger(Long passangerId, Passanger passanger) {
		if(StringUtils.isNotBlank(passangerId.toString())) {
			Optional<Passanger> pas = passangerRepo.findAll().parallelStream()
					.filter(p-> p.getPassangerId()==passangerId).findFirst();
			
			if(pas.isPresent()) {
				pas.get().setPassangerId(passangerId);
				pas.get().setFirstName(passanger.getFirstName());
				pas.get().setLastName(passanger.getLastName());
				pas.get().setPhoneNumber(passanger.getPhoneNumber());
				pas.get().setPassportNumber(passanger.getPassportNumber());
				pas.get().setEmail(passanger.getEmail());
				pas.get().setAddress(passanger.getAddress());
				passangerRepo.save(pas.get());
				
				return pas.get();	
			}else {
				return null;
			}
			
			
		}else {
			return passangerRepo.save(passanger);
		}
		 
		
	}

	@Override
	public List<Passanger> findAllPassangersByNameAndPhone(String name, String phone) {
	 
		return passangerRepo.findAll().stream()
				.filter(p-> p.getFirstName().contentEquals(name))
				.filter(p1-> p1.getPhoneNumber().equalsIgnoreCase(phone))
				.distinct()
				.sorted(Comparator.comparing(Passanger::getFirstName)
						.thenComparing(Passanger::getPhoneNumber))
				.collect(Collectors.toList());
	}

	@Override
	public void deletePassangerById(Long passangerId) {
		if(StringUtils.isNotBlank( passangerId.toString())) {
		Optional<Passanger> passanger = passangerRepo.findAll().parallelStream()
				.filter(p-> p.getPassangerId().toString().equalsIgnoreCase(String.valueOf(passangerId)))
				.findFirst();
		
		if(passanger.isPresent()) {
			passangerRepo.delete(passanger.get());
		}
			
		}
	 
		
	}

	@Override
	public Passanger updatePassanger(Long passangerId, Passanger passanger) {
		 //saveAndUpdate ek sathe kora hoiche
		return null;
	}

}
