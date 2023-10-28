package springbootandthymeleaf.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import springbootandthymeleaf.com.entity.Product;
@Controller
public class ProductController {

@GetMapping("/addproduct")	
public String product(Product product, Model model) {
	model.addAttribute("product", new Product());
	System.out.println("=============PPPPPPPPPPP================");
	
	return "product/addProduct";
	
}







}
