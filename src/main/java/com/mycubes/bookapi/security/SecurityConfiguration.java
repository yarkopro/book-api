package com.mycubes.bookapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Qualifier("userProvider")
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
			.csrf().and()
			.authorizeRequests().anyRequest().authenticated()
			.and()
			.userDetailsService(userDetailsService)
			.httpBasic().authenticationEntryPoint((httpServletRequest, response, e) -> {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		});
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
