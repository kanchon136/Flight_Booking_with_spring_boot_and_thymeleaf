package springbootandthymeleaf.com.config;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
@Override	
public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
		
	}


public void registerStompEndpoints(StompEndpointRegistry rgistry) {
	
	rgistry.addEndpoint("/ws").withSockJS();
	
}


}
