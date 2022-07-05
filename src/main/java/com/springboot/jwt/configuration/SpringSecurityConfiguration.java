package com.springboot.jwt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.jwt.service.UserDetailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
		@Autowired
		private UserDetailService userDetailService;
		@Autowired
		private JWTAuthenticationFilter jwtFilter;
		@Autowired
		private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			// TODO Auto-generated method stub
			auth.userDetailsService(userDetailService);
		}
		@Override
		protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http	
			.cors()
			.disable()
			.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/token")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
		
			
		}
	
		
		@Bean
		public PasswordEncoder passwordEncoder() {
			return  NoOpPasswordEncoder.getInstance();
		}
		
		 @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
		   @Override
		   public AuthenticationManager authenticationManagerBean() throws Exception {
		       return super.authenticationManagerBean();
		   }
		 
}
