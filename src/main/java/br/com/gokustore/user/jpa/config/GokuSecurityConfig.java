package br.com.gokustore.user.jpa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gokustore.security.utils.JwtSecurity;

@Configuration
public class GokuSecurityConfig {

	@Value("${application.seed}")
	private String seed;
	
	@Bean
	public JwtSecurity loadSecurity() {
		return new JwtSecurity(seed);
	}
	
}
