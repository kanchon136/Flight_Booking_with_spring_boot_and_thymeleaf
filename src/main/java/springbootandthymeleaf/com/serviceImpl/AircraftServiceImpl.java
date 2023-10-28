

package springbootandthymeleaf.com.serviceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springbootandthymeleaf.com.entity.Aircraft;
import springbootandthymeleaf.com.repository.AircraftRepository;
import springbootandthymeleaf.com.service.AircraftService;
 
@Service
public class AircraftServiceImpl implements AircraftService{
	
	@Autowired
	private AircraftRepository aircraftRepo;

	
	
	
	@Override
	public Page<Aircraft> findAllAircraftPaged(int pageNo) {
		 
		return aircraftRepo.findAll(PageRequest.of(pageNo, 5, Sort.by("manufacturer")));
	}

	@Override
	public Aircraft findByAircraftId(Long aircraftId) {
		Aircraft aircraft = aircraftRepo.findAll()
				.parallelStream().filter(a-> a.getAircraftId().equals(aircraftId))
				.findFirst().orElse(null);
		 
		return aircraft;
	}

	@Override
	public Aircraft saveAircraft(Aircraft aircraft) {
		
		 
		return aircraftRepo.saveAndFlush(aircraft);
	}

	@Override
	public List<Aircraft> findAllAircraftLists() {
		 List<Aircraft> list = aircraftRepo.findAll()
				 .parallelStream()
				     .sorted(Comparator.comparing(Aircraft ::getAircraftId))
				       .collect(Collectors.toList());
		return list;
	}

	@Override
	public List<Aircraft> findAllByManufacturer(String manufacturer) {
		List<Aircraft> batch = null;
		
		List<Aircraft> list= aircraftRepo.findAll().stream()
				.filter((aircraft) -> aircraft.getManufacturer().equalsIgnoreCase("kanchon"))
				.sorted(Comparator.comparing(Aircraft:: getManufacturer)
				  .thenComparing(s-> s.getModel()))
				     .collect(Collectors.toList());
		
		java.util.Collections.shuffle(list);
		
		int batchSize =5;
		List<Aircraft> selectedList = new ArrayList<Aircraft>();
		
		while(!list.isEmpty()) {
			batch = list.subList(0, Math.min(batchSize, list.size()));
			selectedList.addAll(batch);
			
			List<Aircraft> newList = new ArrayList<Aircraft>(list);
			
			newList.removeAll(selectedList);
			
			list = newList;
			
			
			return batch;	
			
			
		}
				
	 
		return null;
	}

	@Override
	public Aircraft EditAircraftById(Integer aircraftId, Aircraft aircraft) {
		// first way get aircraftByid
		Aircraft aircraft1= aircraftRepo.findAll().parallelStream()
				.filter(a-> a.getAircraftId().equals(aircraftId))
				.findFirst().orElse(null);
		
		// second way get aircraftByid
		Optional<Aircraft> air = aircraftRepo.findAll()
				  .parallelStream().filter(f-> f.getAircraftId().equals(aircraftId))
				  .findFirst();
				
		if(air.isPresent()){
			aircraft1.setAircraftId(Long.valueOf(aircraftId));
			aircraft1.setManufacturer(aircraft.getManufacturer());
			aircraft1.setModel(aircraft.getModel());
			aircraft1.setNumberOfSeats(aircraft.getNumberOfSeats());
			aircraftRepo.save(aircraft1);
		}
		return air.get();
	}

	@Override
	public void deleteAircraftById(Long aircraftId) {
		
		if(!aircraftId.toString().isEmpty()) {
			Aircraft a = aircraftRepo.findAll().parallelStream()
					.filter(f-> f.getAircraftId()==Long.valueOf(aircraftId) )
					.findFirst().orElse(null);
	
			if(a != null) {		
				aircraftRepo.delete(a);
			}
		}
	}

	 

}
