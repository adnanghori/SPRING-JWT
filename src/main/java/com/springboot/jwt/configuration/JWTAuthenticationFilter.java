package com.springboot.jwt.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.jwt.helper.JWTUTIL;
import com.springboot.jwt.service.UserDetailService;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
		@Autowired
		private JWTUTIL jwtutil;
		@Autowired
		private UserDetailService detailService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		// jwt token
		
		// is starting with bearer
		 	 // validate
		
		String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		// checking null and format
		if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
			jwtToken = requestTokenHeader.substring(7);
			System.out.println(jwtToken);
			try {
				username = this.jwtutil.extractUsername(jwtToken);
				System.out.println(username);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			UserDetails userDetails = this.detailService.loadUserByUsername(username);
			// security validation
			if(username!= null && SecurityContextHolder.getContext().getAuthentication()== null) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("Token not validated");
			}
			
		}
		filterChain.doFilter(request, response);
	}
	
		
}
