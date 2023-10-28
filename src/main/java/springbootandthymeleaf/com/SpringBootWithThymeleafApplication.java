package springbootandthymeleaf.com;

 
 
 
 

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
 
import org.springframework.security.crypto.password.PasswordEncoder;

import springbootandthymeleaf.com.entity.Role;
import springbootandthymeleaf.com.entity.User;
import springbootandthymeleaf.com.repository.RoleRepository;
import springbootandthymeleaf.com.repository.StudentRepository;
import springbootandthymeleaf.com.repository.TeacherRepository;
import springbootandthymeleaf.com.repository.UserRepository;

@SpringBootApplication
public class SpringBootWithThymeleafApplication implements CommandLineRunner{
	
	@Autowired
	private TeacherRepository tRepo;
	
	@Autowired
	private StudentRepository stRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepor;
	
	@Autowired
	private PasswordEncoder passwordencoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWithThymeleafApplication.class, args);
	}

 
	
	
@Override
public void run(String... args) throws Exception {
 

//     	Role r = new Role();
//     	//r.setRoleId(UUID.randomUUID());
//     	r.setRoleName("ROLE_ADMIN");
//     	roleRepor.save(r);
//     	//List<Role> roles = new ArrayList<>();
//     	
//     	
//        User user = new User();
//      //  user.setUserId(UUID.randomUUID());
//        user.setUserName("kanchon");
//        user.setEmail("jahirul@atilimited.net");
//        user.setPassword(passwordencoder.encode("kanchon11"));
//        user.setRoles(Collections.singletonList(r));
//        
//        userRepo.save(user);
     	
     	
		
		
		System.out.println("===========Successfully==========");
		   
				  
		
		 
		  
		  
		  
		
	}
	
	

}
