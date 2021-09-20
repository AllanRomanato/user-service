package br.com.gokustore.user.service.impl;

import static br.com.gokustore.user.service.utils.Constants.APP_NAME;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gokustore.security.utils.HMAC;
import br.com.gokustore.security.utils.JwtSecurity;
import br.com.gokustore.user.jpa.model.User;
import br.com.gokustore.user.jpa.repository.UserRepository;
import br.com.gokustore.user.request.dto.LoginRequestDto;
import br.com.gokustore.user.request.dto.SignupDto;
import br.com.gokustore.user.response.dto.Envelope;
import br.com.gokustore.user.response.dto.LoginResponseDto;
import br.com.gokustore.user.service.UserService;
import br.com.gokustore.utils.exceptions.BadRequestException;
import br.com.gokustore.utils.exceptions.NotFoundException;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	
	@Value("${application.hmac-seed}")
	private String hmacSeed;
	
	@Value("${application.token-time}")
	private Integer tokenTime;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtSecurity jwtSec;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public void login(String payload, Envelope env) {
		try {
			log.info(" ---- EXECUTING LOGIN ---- ");
			LoginRequestDto dto = mapper.readValue(payload, LoginRequestDto.class);
			if(dto.getEmail()!=null && dto.getPassword()!=null) {
				User user = userRepo.getUserInfoByCredentials(dto.getEmail(), HMAC.getHmac(hmacSeed, dto.getPassword()));
				if(user == null) {
					log.info(" ---- NO DATA FOUND TO EMAIL "+dto.getEmail()+"  ---- ");
					throw new NotFoundException("Email ou senha inválidos");
				}
				String token = jwtSec.createJWTToken(String.valueOf(user.getId()), user.getName(), user.getEmail(), tokenTime);
				env.setSuccess(true);
				env.setData(new LoginResponseDto(token));
			} else {
				log.info(" ---- ALL THE FIELDS MUST BE POPULATED ---- ");
				throw new BadRequestException("Todos os campos tem que ser populados");
			}
		} catch (IOException e) {
			log.error(" ---- AN ERROR HAS HAPPENED ----", e);
			throw new BadRequestException("Erro ao parserar a requisição");
		}
	}
	
	public void signUp(String payload) {
		try {
			log.info(" ---- SIGNING UP ---- ");
			SignupDto dto = mapper.readValue(payload, SignupDto.class);
			dto.setPassword(HMAC.getHmac(hmacSeed, dto.getPassword()));
			if(dto.getEmail()!=null && dto.getPassword()!=null && 
					dto.getName()!=null && dto.getUserName()!=null) {
				
				User user = userRepo.checkUserEmail(dto.getEmail());
				
				if(user!=null) {
					log.info(" ---- USER WITH EMAIL "+dto.getEmail()+" ALREADY EXISTS ---- ");
					throw new BadRequestException("Usuário existente.");
				}
				
				user = modelMapper.map(dto, User.class);
				user.setCreatedAt(new Date());
				user.setCreateUser(APP_NAME);
				
				log.info(" ---- SAVING ON DB ---- ");
				userRepo.save(user);
				
			}
		} catch (IOException e) {
			log.error(" ---- AN ERROR HAS HAPPENED ----", e);
			throw new BadRequestException("Erro ao parserar a requisição");
		}
	}
}
