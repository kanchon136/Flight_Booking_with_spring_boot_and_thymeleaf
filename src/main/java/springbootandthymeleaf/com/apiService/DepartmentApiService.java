package springbootandthymeleaf.com.apiService;

 
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.sun.org.apache.regexp.internal.recompile;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springbootandthymeleaf.com.ApiModel.ApiResponse;
import springbootandthymeleaf.com.ApiModel.Department;
import springbootandthymeleaf.com.CustomException.DepartmentNotFoundException;

@Service
public class DepartmentApiService {
	
	private static final Logger log = LogManager.getLogger(DepartmentApiService.class);
	
 
//	private final WebClient webclient;
//
//	public DepartmentApiService(WebClient webclient) {
//		super();
//		this.webclient = webclient;
//	}
	
	private WebClient.Builder webclientBuilder;
	
	 
	
public DepartmentApiService(Builder webclientBuilder) {
	super();
	this.webclientBuilder = webclientBuilder;
}



public Flux<Department> getAllDepartments(){
	
	WebClient webclient = webclientBuilder.build();
	
	return webclient.get().uri("/findAllDepartments").retrieve()
			.onStatus(httpSatatus-> !httpSatatus.is2xxSuccessful(), clientresponse-> handleClientError(clientresponse.statusCode()))
			.bodyToFlux(Department.class)
			.onErrorResume(Exception.class, e-> Flux.empty());
	 
}

public Flux<Department> getAllDepartmentsPageble(Pageable pageable){
	
	
	WebClient eClient = webclientBuilder.build();
	return eClient.get().uri("/findAllDepartments").retrieve()
			.onStatus(httpSatatus-> !httpSatatus.is2xxSuccessful(), clientresponse-> handleClientError(clientresponse.statusCode()))
			.bodyToFlux(Department.class)
			.onErrorResume(Exception.class, e-> Flux.empty());
	 
	
}


 public Mono<Department> findDepartmentById(Long  departmentId){
	 WebClient webClient = webclientBuilder.build();
	 return webClient.get()
			 .uri("/findDepartmentById/"+departmentId).exchangeToMono(this::handleResponse);
	 
 }
 
 public Department updateDeaprtmentByBlock(Long deptId) {
	 WebClient client = webclientBuilder.build();
	 try {
		 Department department = client.get()
				 .uri("findDepartmentById/"+deptId)
				 .retrieve()
				 .bodyToMono(Department.class).block();
		 
		    return department;
	 }catch (WebClientResponseException  e) {
	        log.error(e.getMessage());
	        return null;
	}
 }

 public Mono<ResponseEntity<ApiResponse>> createDepartmentOld(Department department){
	 WebClient webClient = webclientBuilder.build();
	 
	 return  webClient.post()
			 .uri("/saveDepartment")
			   .body(Mono.just(department), Department.class)
			   .retrieve()
			   .onStatus(HttpStatus::is4xxClientError, response-> {
				   return Mono.error(new WebClientResponseException("Bad Request", response.statusCode().value(), null, null, null, null, null));
			   })
			   .onStatus(HttpStatus:: is5xxServerError, response->{
				   return Mono.error(new WebClientResponseException(response.statusCode().value(), "Server errror", null, null, null));
			   }).toEntity(ApiResponse.class);
 }
 
 
 
 public ApiResponse saveDepartmentSynStyle(Department department) {
	 WebClient webClient = webclientBuilder.build();
	 ApiResponse response  = new ApiResponse();            
	 try {
		  response = webClient.post()
				.uri("/saveDepartment")
				.body(Mono.just(department), Department.class)
				.retrieve()
				.bodyToMono(ApiResponse.class)
				.block();
		return response;
	 }catch(WebClientResponseException e) {
		 response.setStatus(false);
		 response.setMessage(e.getMessage());
		 return response;
	 } 
 }
 
 public ApiResponse deleteDepartmentById(Long deptId) {
	 try {
	 WebClient webClient = webclientBuilder.build();
	 ApiResponse response = webClient.delete()
			 .uri(uribuilder-> 
				 uribuilder.path("/deleteDepartmetnById")
				 .queryParam("deptId", deptId).build())
			 .retrieve()
			 .bodyToMono(ApiResponse.class).block();
    	 return response;
	 }catch (WebClientResponseException e) {
		 return new ApiResponse(false, e.getMessage());
		 
	}		 
 }
 
 
 public ApiResponse updateDepartment(Department department ,Long deptId) {
	 WebClient webClient   =webclientBuilder.build();
	 ApiResponse response = new ApiResponse();
	 try {
		 response = webClient.put()
				 .uri(uribuilder-> 
				   uribuilder.path("/updateDepartment")
				   .queryParam("deptId", deptId)
				   .build())
				   .body(Mono.just(department),Department.class)
				   .retrieve()
				   .bodyToMono(ApiResponse.class).block();
		 
		 return response;
		
	 }catch (WebClientResponseException e) {
		 response.setStatus(false);
		 response.setMessage(e.getMessage());
		 return response;
	 }
		 
	 
 }
 
 public Mono<ResponseEntity<ApiResponse>> createDepartment(Department department) {
	    WebClient webClient = webclientBuilder.build();
	    return webClient.post()
	        .uri("/saveDepartment")
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(department)
	        .exchangeToMono(response -> {
	            if (response.statusCode().is2xxSuccessful()) {
	                return response.bodyToMono(ApiResponse.class)
	                    .map(apiResponse -> new ResponseEntity<>(apiResponse, response.statusCode()));
	            } else if (response.statusCode().is4xxClientError()) {
	                return response.createException()
	                    .flatMap(Mono::error);
	            } else {
	                return Mono.error(new RuntimeException("Server error"));
	            }
	        });
	}
 

private Mono<Department> handleResponse(ClientResponse response) {
    if (response.statusCode().is2xxSuccessful()) {
        return response.bodyToMono(Department.class);
    } else if (response.statusCode().is4xxClientError()) {
        return Mono.error(new DepartmentNotFoundException("Department not found"));
    } else if (response.statusCode().is5xxServerError()) {
        return Mono.error(new RuntimeException("Server error"));
    } else {
        return Mono.error(new RuntimeException("Unexpected error"));
    }
}
	 
	
	 
	
	

//passing token
//public Flux<Department> getAllDepartmentsPassingToken(String bearerToken) {
//    WebClient webClient = webclientBuilder.build();
//    
//    return webClient.get()
//            .uri("/findAllDepartments")
//            .header(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken) // Set the Bearer token here
//            .retrieve()
//            .onStatus(httpSatatus -> !httpSatatus.is2xxSuccessful(), clientresponse -> handleClientError(clientresponse.statusCode()))
//            .bodyToFlux(Department.class)
//            .onErrorResume(Exception.class, e -> Flux.empty());
//}


	
	
	private Mono<? extends Throwable> handleClientError(HttpStatus httpStatus){        
		return Mono.error(new DepartmentNotFoundException("Department Not Found"));
	}
	
	
//	private Mono<? extends Throwable> handleServerError(ClientResponse clientResponse){
//		return Mono.error(new RuntimeException("Internal Server error"));
//		
//	}

}
