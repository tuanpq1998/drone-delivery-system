package hust.tuanpq.finalproject.dronecontrol.config;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import hust.tuanpq.finalproject.dronecontrol.jwt.JwtUtility;
import hust.tuanpq.finalproject.dronecontrol.service.AccountService;
import hust.tuanpq.finalproject.dronecontrol.service.MissionService;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Autowired
    private JwtUtility jwtUtility;
	
	@Autowired
    private AccountService accountService;
	
	@Autowired
	private MissionService missonService;
	
	@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app", "/appTracking");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/v1/chat-websocket").setAllowedOriginPatterns("*").withSockJS();
    }
    
    
  

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		registration.interceptors(new ChannelInterceptor() {

			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				 StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
				 System.out.println(accessor);
				return message;
			}
			
			
		});
	}

	@Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
      registration.interceptors(new ChannelInterceptor() {
    	  
    	  
    	  
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
          StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
          System.out.println(accessor.getDestination());
          if (StompCommand.CONNECT.equals(accessor.getCommand()) 
        		  || StompCommand.SUBSCRIBE.equals(accessor.getCommand()) 
        		  || StompCommand.MESSAGE.equals(accessor.getCommand())) {
List <String> ah = accessor.getNativeHeader("Authorization");
if (ah != null && ah.size() > 0) {
	String bearerToken = ah.get(0).replace("Bearer ", "");
	if (bearerToken != null && jwtUtility.validateJwtToken(bearerToken)) {
		String userName = jwtUtility.getUserNameFromJwtToken(bearerToken);
  	  System.out.println(userName);
  	  UserDetails userDetails = accountService.loadUserByUsername(userName);
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                  userDetails,null,userDetails.getAuthorities()
          );
          SecurityContextHolder.getContext().setAuthentication(authentication);

  	  accessor.setUser(authentication);
  	return message;
	}
//	return null;
} else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
	String des = accessor.getDestination();
	if (des.indexOf("/appTracking/locationTracking/") == 0) {
		String payload = des.replace("/appTracking/locationTracking/", "");
		String missionIdenfifier = payload.substring(0, payload.indexOf("."));
		String password = payload.substring(payload.indexOf(".")+1);
		if (missonService.findByIdentifier(missionIdenfifier, password) == null) {
			throw new IllegalArgumentException("Wrong password!");
		}
		
	}
	
}
//return null;
//            Optional.ofNullable(accessor.getNativeHeader("Authorization")).ifPresent(ah -> {
//              String bearerToken = ah.get(0).replace("Bearer ", "");
////              System.out.println("Received bearer token: " + bearerToken);
//              if (bearerToken != null && jwtUtility.validateJwtToken(bearerToken)) {
//            	  String userName = jwtUtility.getUserNameFromJwtToken(bearerToken);
////            	  System.out.println(userName);
//            	  UserDetails userDetails = accountService.loadUserByUsername(userName);
//	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//	                        userDetails,null,userDetails.getAuthorities()
//	                );
//	                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            	  accessor.setUser(authentication);
//              }
//              //              JWSAuthenticationToken token = (JWSAuthenticationToken) authenticationManager
////                  .authenticate(new JWSAuthenticationToken(bearerToken));
////              accessor.setUser(token);
//              else {
////            	  UserDetails userDetails = accountService.loadUserByUsername(guestAccountName);
////	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
////	                        userDetails,null,userDetails.getAuthorities()
////	                );
////	                SecurityContextHolder.getContext().setAuthentication(authentication);
////	                System.out.println("acc");
////	                System.out.println(accessor);
////          	  accessor.setUser(authentication);
//              }
//            });
            
            
          }
          return message;
        }
      });
    }
}
