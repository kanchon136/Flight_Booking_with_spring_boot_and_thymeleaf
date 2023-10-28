package springbootandthymeleaf.com.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import springbootandthymeleaf.com.dto.LoginDto;

@Controller
public class LoginController {

@GetMapping("/login")	
public String login() {
		
	//model.addAttribute("loginDto", new LoginDto());
	return "login/login";
	//return "redirect:/index";
		
	}

@GetMapping("/index")
public String home() {
	return "indexNew";
}


@PostMapping("/mainDashboard")
public String loginFianl(LoginDto loginDto, Model model) {
	System.out.println(loginDto.getUserName());
	System.out.println(loginDto.getPassword());
 
	if(StringUtils.isNoneBlank(loginDto.getUserName()) && StringUtils.isNoneBlank(loginDto.getPassword())) {
		if(loginDto.getUserName().equals("kanchon") && loginDto.getPassword().equals("kanchon11")) {
			model.addAttribute("submited", true);
			//return "home/index";
			return "indexNew";
			
			
		}else {
			return "login/login";
		}
	}else {
		return "login/login";
	}
	
//	
}
	
}


