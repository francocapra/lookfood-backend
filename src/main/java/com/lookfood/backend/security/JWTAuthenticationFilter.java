package com.lookfood.backend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lookfood.backend.dto.CredentialsDTO;

//filtro intercepta a requisição, executa alguma(lógica antes), e se tudo der certo, ele devolve para requisição!!
//Esse filtro vai interceptar a requisição do endpoint LOGIN

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(
								HttpServletRequest request, 
								HttpServletResponse response)
			throws AuthenticationException {

		try {
			
//			tentar pegar o objeto que veio pela requisição! e converter-lo pra DTO
			// Instanciando objeto DTO, apartir dos dados que vieram na requisição
			CredentialsDTO creds = new ObjectMapper()
										.readValue(request.getInputStream(), CredentialsDTO.class);
			
			// Pegando os dados do DTO: Email e Senha
			// Instaciando um objeto tipo TOKEN(não é o JWT, é do Spring security)
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(),
					creds.getPassword(), new ArrayList<>());
			
//			Esse TOKEN vai verificar se os dados da requeição são validos, como??? 
//			usando as implementações dos contratos(interfaces) que esta no "UserSS" "UserSSService" !!
//			isso é do Spring Boot, sendo validos ou não, retornamos um objeto do tipo  "Authentication"
			Authentication auth = authenticationManager.authenticate(authToken);
			
			return auth;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	//Esse método básicamente gera um TOKEN(JWT) e devolve no Header do Response
	@Override
	protected void successfulAuthentication(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		//authResult.getPrincipal(): retorna o "Usuario do Spring Security"
		//faço um CAST tipo do meu tipo de implementação "UserSS"
		String username = ((SSUserDetails) authResult.getPrincipal()).getUsername(); 
		
		//Pego o username e gero num Token JWT
		String token = jwtUtil.generateToken(username);
		
		//coloco o JWToken no header do response!
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization"); 
	}
	
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);
	}
	
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		 
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json"); 
            response.getWriter().append(json());
        }
        
        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
        }
    }
	
}
