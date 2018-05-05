package com.lookfood.backend.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lookfood.backend.domain.enums.TypeProfile;

//Classe que implementa uma Interface que determina um Contrato do Spring Security
public class UserSS implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public Integer getId() {
		return id;
	}
	
	public UserSS() {
		super();
	}

	public UserSS(Integer id, String email, String password, Set<TypeProfile> profiles ) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = profiles
							.stream()
							.map( x -> new SimpleGrantedAuthority( x.getDescription() ))  //Pegar a String do Perfil (Padrão do Spring Boot)
							.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
