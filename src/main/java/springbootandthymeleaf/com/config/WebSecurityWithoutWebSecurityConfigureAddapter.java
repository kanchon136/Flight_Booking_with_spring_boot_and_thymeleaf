package springbootandthymeleaf.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true,proxyTargetClass=true)
public class WebSecurityWithoutWebSecurityConfigureAddapter {
	
	@Autowired
	private UserDetailsService userdetailservice;
	
	
	
  @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

 @Bean
  public AuthenticationProvider authprovider() {
	  DaoAuthenticationProvider authenticationprovider = new DaoAuthenticationProvider();
	  authenticationprovider.setUserDetailsService(userdetailservice);
	  authenticationprovider.setPasswordEncoder(passwordEncoder());
	  
	  return authenticationprovider;
  }
  
 
 
 public AuthenticationManager authenticationManger(AuthenticationManagerBuilder builder) throws Exception {
	 builder.authenticationProvider(authprovider());
	 
	 return builder.build();
	 
 }
 
 @Bean
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	 
	 
	
	 return http
	            .authorizeRequests()
	                .antMatchers("/index").authenticated()
	                .anyRequest().permitAll()
	                .and()
	            .formLogin()
	                .loginPage("/login")
	                .defaultSuccessUrl("/index")
	                .failureUrl("/login?error")
	                .permitAll()
	                .and()
	            .logout()
	                .logoutUrl("/logout")
	                .logoutSuccessUrl("/login?logout")
	                .permitAll()
	                .and()
	            .csrf().disable()
	            .headers()
	                .frameOptions().sameOrigin()
	                .and()
	            .exceptionHandling()
	                .and()
	            .build();
	 
 }
 
 
 
 

}
