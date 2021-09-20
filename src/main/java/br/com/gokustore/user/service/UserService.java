package br.com.gokustore.user.service;

import br.com.gokustore.user.response.dto.Envelope;

public interface UserService {
	void login(String payload, Envelope env);
	void signUp(String payload);
}
