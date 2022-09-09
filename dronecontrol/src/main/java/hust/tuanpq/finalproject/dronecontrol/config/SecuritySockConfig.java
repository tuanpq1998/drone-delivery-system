package hust.tuanpq.finalproject.dronecontrol.config;

import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Component
@EnableWebSocketMessageBroker
public class SecuritySockConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages
			
			.simpDestMatchers("/app/**")
			.hasRole("ADMIN")
			.simpDestMatchers("/appTracking/**")
			.permitAll()
			.simpSubscribeDestMatchers("/app/**")
			.hasRole("ADMIN")
			.simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.MESSAGE, SimpMessageType.UNSUBSCRIBE,
					SimpMessageType.DISCONNECT, SimpMessageType.HEARTBEAT, SimpMessageType.SUBSCRIBE)
				.permitAll()
			.anyMessage().denyAll();
	}

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}

}
