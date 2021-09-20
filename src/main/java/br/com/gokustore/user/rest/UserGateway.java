package br.com.gokustore.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gokustore.user.response.dto.Envelope;
import br.com.gokustore.user.service.UserService;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserGateway extends AbstractGateway{

	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/login", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Envelope> login(@RequestBody String payload){
		Envelope env = new Envelope();
		userService.login(payload, env);
		return new ResponseEntity<>(env, HttpStatus.OK);
	}
	
	@PostMapping(path = "/sign-up", 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> signUp(@RequestBody String payload){
		userService.signUp(payload);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
