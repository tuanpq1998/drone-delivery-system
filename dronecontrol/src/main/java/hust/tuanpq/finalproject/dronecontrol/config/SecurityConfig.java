package hust.tuanpq.finalproject.dronecontrol.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hust.tuanpq.finalproject.dronecontrol.jwt.JwtFilter;
import hust.tuanpq.finalproject.dronecontrol.service.AccountService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = false, jsr250Enabled = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
	    private AccountService accountService;
	    @Autowired
	    private JwtFilter jwtFilter;
	    @Bean
	    public BCryptPasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder(10);}

	    @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.cors();
	        http.csrf().disable();

	        http.authorizeRequests()
	                .antMatchers("/api/v1/seller/**").hasRole("SELLER")
	                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
	                .antMatchers("/api/v1/account/me", "/api/v1/homeLocation").hasAnyRole("ADMIN", "SELLER")
	                .antMatchers("/api/v1/account/login","/api/v1/tracking/*", "/api/v1/chat-websocket/**")
	                .permitAll()
	                .anyRequest()
	                .authenticated();
	        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	    }

	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());

	    }
}
