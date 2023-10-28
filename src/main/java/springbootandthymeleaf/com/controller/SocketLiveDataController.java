package springbootandthymeleaf.com.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SocketLiveDataController {
	
	
	@MessageMapping("/send-live-data")
	@SendTo("/topic/live-data")
	public String sendLiveData(String  livedata) {
		
		
		
		return livedata;
		
	}
	
	@GetMapping("/testingLivedata")
	public String testingLiveData() {
		  
		
		
		return "socketLiveData";
	}

}
