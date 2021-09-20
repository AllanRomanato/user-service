package br.com.gokustore.user.response.dto;

public class LoginResponseDto {
	private String token;

	public LoginResponseDto(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
}
