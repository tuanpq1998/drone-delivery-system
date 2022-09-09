package hust.tuanpq.finalproject.dronecontrol.service;

import org.springframework.stereotype.Service;

import hust.tuanpq.finalproject.dronecontrol.bo.Response;
import hust.tuanpq.finalproject.dronecontrol.bo.SystemResponse;
import hust.tuanpq.finalproject.dronecontrol.entity.Account;
import hust.tuanpq.finalproject.dronecontrol.entity.Role;
import hust.tuanpq.finalproject.dronecontrol.jwt.JwtUtility;
import hust.tuanpq.finalproject.dronecontrol.model.LoginRequest;
import hust.tuanpq.finalproject.dronecontrol.repository.AccountRepository;
import hust.tuanpq.finalproject.dronecontrol.repository.RoleRepository;
import hust.tuanpq.finalproject.dronecontrol.utility.StringResponse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Service
public class AccountService implements UserDetailsService {

	@Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account user = accountRepository.findByUsername(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();

        Role role = user.getRole();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role.getName().toUpperCase());
        grantedAuthorityList.add(grantedAuthority);


        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthorityList);
	}

	public Account findByUsername(String username) {
		return accountRepository.findByUsername(username);
	}
}
