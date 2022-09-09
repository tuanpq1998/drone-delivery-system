package hust.tuanpq.finalproject.dronecontrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hust.tuanpq.finalproject.dronecontrol.bo.Response;
import hust.tuanpq.finalproject.dronecontrol.bo.SystemResponse;
import hust.tuanpq.finalproject.dronecontrol.dto.AccountDto;
import hust.tuanpq.finalproject.dronecontrol.entity.Account;
import hust.tuanpq.finalproject.dronecontrol.jwt.JwtUtility;
import hust.tuanpq.finalproject.dronecontrol.model.LoginRequest;
import hust.tuanpq.finalproject.dronecontrol.service.AccountService;
import hust.tuanpq.finalproject.dronecontrol.utility.CustomPasswordEncoder;
import hust.tuanpq.finalproject.dronecontrol.utility.CustomPasswordUtility;
import hust.tuanpq.finalproject.dronecontrol.utility.StringResponse;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

	 @Autowired
	    private AccountService accountService;
	 
	 @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtUtility jwtUtility;
  
	 
//	    @PostMapping(value = "/resgister")
//	    public ResponseEntity<?> resgister(@RequestBody ResgisterIn resgisterIn){
//	        return new ResponseEntity<>(accountService.resgister(resgisterIn), HttpStatus.OK);
//	    }
	 
	    @PostMapping(value = "/login")
	    public ResponseEntity<SystemResponse<Object>> login(@RequestBody LoginRequest loginRequest){	    
	    	Account account = accountService.findByUsername(loginRequest.getUsername());
	        if(account == null )
	        {
	            return Response.badRequest(StringResponse.ACCOUNT_NOT_FOUND);
	        }
	        if(BCrypt.checkpw(loginRequest.getPassword(),account.getPassword()) == false) {
	            return Response.badRequest(StringResponse.PASSWORD_WRONG);
	        }
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = jwtUtility.generateJwtToken(loginRequest.getUsername());
	        return Response.ok(jwt);
	    }
	    
	    @GetMapping(value = "/me")
	    public AccountDto getMyInfomation(Authentication authentication){
	    	Account account = accountService.findByUsername(authentication.getName());
	    	return new AccountDto(account);
	    }
}
