package com.springboot.jwt.model;

public class JWTResponse {
	
		private String token;

		@Override
		public String toString() {
			return "JWTResponse [token=" + token + "]";
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public JWTResponse(String token) {
			super();
			this.token = token;
		}

		public JWTResponse() {
			super();
			// TODO Auto-generated constructor stub
		}
}
