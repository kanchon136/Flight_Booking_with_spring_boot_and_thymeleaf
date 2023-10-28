package springbootandthymeleaf.com.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springbootandthymeleaf.com.ApiModel.ApiResponse;
import springbootandthymeleaf.com.ApiModel.Department;
import springbootandthymeleaf.com.apiService.DepartmentApiService;
@Controller
public class DepartmentApiController {
	
	private static final  Logger log = LogManager.getLogger(DepartmentApiController.class); 
	
	@Autowired
	private DepartmentApiService departmentApiService;
	
	 
	
	@GetMapping("/departments")
	public String  getAllDepartments(Model model) {
		
		Flux<Department> alldepartments = departmentApiService.getAllDepartments();
		
		
		Map<String, Department> mapDepartments= alldepartments.collectList().block()
				.stream()
			    	.filter(d-> StringUtils.isNotBlank(d.getDepartmentName()))
				     .collect(Collectors.toMap(Department::getDepartmentName, valueMapper->valueMapper, (existing,replace)-> existing));
		 
		List<Department> checkDupDepartments = mapDepartments.entrySet()
				.stream()
				      .map(Map.Entry::getValue).collect(Collectors.toList());
 
		model.addAttribute("departments", checkDupDepartments.stream()
				.sorted(Comparator.comparing(Department::getDepartmentId,Comparator.nullsLast(Long::compareTo)))
				       .collect(Collectors.toList())	);
		return "product/departments";
	 
	}
	
	@GetMapping("/departmentsTryPagingation")
	public String getAllDepartmentsForPagination(Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size) {
	  
		Pageable pageble = PageRequest.of(page, size,Sort.by("departmentId").ascending());
		
		Flux<Department> fluxList = departmentApiService.getAllDepartmentsPageble(pageble);
		Page<Department> pageblelist = (Page<Department>) fluxList.collectList().block();
	 

	    model.addAttribute("departments", pageblelist);
	    return "product/departments";
	}

	
	
	
	
	@GetMapping("/saveDepartment")
	public String showDepartmentNewPage(Model model) {
		model.addAttribute("department", new Department());
		return"product/newDepartment";
	}
	
	
   @PostMapping("/saveDepartment")
	public String createDepartment(@ModelAttribute("department") Department department, Model model) {
		
		 ApiResponse response = departmentApiService.saveDepartmentSynStyle(department);
		if(response.isStatus()) {
			model.addAttribute("deSaveResponse", response);
			return "redirect:/departments";
		}else {
			model.addAttribute("department", new Department());
		 
			return "product/newDepartment";
		}
	 
	}
   
   @GetMapping("/showUpdaeDepartmentPage")
   public String showUpdateDemartmentPage(@RequestParam("deptId") Long deptId,  
		                                                                          Model model) {
	   
	   Department deparment = departmentApiService.findDepartmentById(deptId).block();
	   if(deparment != null) {
		   model.addAttribute("department", deparment);
		   return "product/updateDepartment";
	   }else {
		   return "redirect:/departments";
	   }
   }
   
  // @PutMapping("/updateDepartment")
   @PostMapping("/updateDepartment")
   public String UpdateDepartment(@ModelAttribute("department") Department department,
		                                                        @RequestParam("departmentId") Long deptId,
		                                                        Model model
		                                                        ) {
	   
	   ApiResponse apiResponse = departmentApiService.updateDepartment(department, deptId);
	   if(apiResponse != null && apiResponse.isStatus()) {
		   
		   model.addAttribute("depUpdResponse", apiResponse);  
		   return "redirect:/departments";
		   
	   }else {
		   model.addAttribute("department", new Department());
		   return "product/updateDepartment";
	   }
	   
   }
   
	@GetMapping("/deleteDepartmentById")
   public String deleteDepartmentById(@RequestParam(name="deptId") Long deptId,Model model) {
	   ApiResponse response = departmentApiService.deleteDepartmentById(deptId);
	   if(response.isStatus()){
		   model.addAttribute("deleteResponse", response)  ;
		   return "redirect:/departments";
	   }else {
		   model.addAttribute("deleteResponse", response)  ;
		   return "redirect:/departments";
	   }
	   
   }
   
   
	//@PostMapping("/saveDepartment")
	public Mono<String> createDepartmentNew(@ModelAttribute("department") Department department, Model model) {
	    return departmentApiService.createDepartment(department)
	        .flatMap(response -> {
	            if (response.getBody().isStatus() && response.getBody().getMessage().equalsIgnoreCase("Save Successfully")) {
	                model.addAttribute("deSaveResponse", response.getBody());
	                return departmentApiService.getAllDepartments()
	                    .collectList()
	                    .doOnNext(departments -> model.addAttribute("departments", departments))
	                    .map(departments -> "product/departments");
	            } else {
	                model.addAttribute("department", new Department());
	                return Mono.just("product/newDepartment");
	            }
	        });
	}
	

}
