//package springbootandthymeleaf.com.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled= true,proxyTargetClass=true)
//@SuppressWarnings("deprecation")
//public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
//
// @Qualifier("customUserServiceImpl")	
// 
//	
//@Autowired
//private UserDetailsService userservice;	
//
// 
//
//
// @Bean
// public PasswordEncoder passwordEncoder() {
// 	return new BCryptPasswordEncoder();
// }
//
// @Bean
// public DaoAuthenticationProvider authProvider() {
//	 
//	 DaoAuthenticationProvider autprovide = new DaoAuthenticationProvider();
//	 autprovide.setUserDetailsService(userservice);
//	 autprovide.setPasswordEncoder(passwordEncoder());
//	 
//	 
//	 return autprovide;
//	 
//	 
//	 
//	 
// }
//
//
//
//@Override
//public void configure(AuthenticationManagerBuilder auth) throws Exception{
//	
//	auth.authenticationProvider(authProvider());
//	
//}
//
//@Override
//protected void configure(HttpSecurity http) throws Exception {
//
//	http
//    .authorizeRequests()
//        .antMatchers("/index").authenticated()
//        .anyRequest().permitAll()
//        .and()
//    .formLogin()
//        .loginPage("/login")
//        .defaultSuccessUrl("/index")
//        .failureUrl("/login?error")
//        .permitAll()
//        .and()
//    .logout()
//        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//        .logoutSuccessUrl("/login?logout")
//        .permitAll()
//        .and()
//    .csrf().disable()
//    .headers()
//        .frameOptions().sameOrigin()
//        .and()
//    .exceptionHandling();
//	
//}
//
//	
//
//
//
//}
