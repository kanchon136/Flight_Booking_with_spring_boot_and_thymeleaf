package springbootandthymeleaf.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
 
 

@Configuration
public class WebClientConfig {
	
//	@Bean
//	public WebClient.Builder webClientBuilder() {
//	    HttpClient httpClient = HttpClient.create()
//	        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
//	        .doOnConnected(conn -> conn
//	            .addHandlerLast(new ReadTimeoutHandler(10))
//	            .addHandlerLast(new WriteTimeoutHandler(10)));
//
//	    return WebClient.builder()
//	        .clientConnector(new ReactorClientHttpConnector(httpClient))
//	        .baseUrl("https://192.168.0.79:9001/department-service/department")
//	        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//	}
//	
//	@Bean
//	public WebClient webClient(WebClient.Builder webClientBuilder) {
//	    return webClientBuilder.build();
//	}
	
	
	// running
//	 @Bean
//	  public WebClient webClient() {
//
//	    WebClient webClient = WebClient.builder()
//	    		//http://localhost:9001/department-service/department/findAllDepartments
//	      .baseUrl("http://localhost:9001/department-service/department")
//	      .defaultCookie("cookie-name", "cookie-value")
//	      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//	      .build();
//		return webClient;
//	  }

	 @Bean
	 public WebClient.Builder webClientBuilder() {
	     return WebClient.builder()
	             .baseUrl("http://localhost:9001/department-service/department")
	             .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	 }
	 
}
