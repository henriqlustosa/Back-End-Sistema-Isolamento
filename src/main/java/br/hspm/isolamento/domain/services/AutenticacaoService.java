package br.hspm.isolamento.domain.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.hspm.isolamento.application.dtos.request.LoginFormDto;
import  br.hspm.isolamento.infra.repositories.*;
@Service
public class AutenticacaoService implements UserDetailsService{
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private TokenService tokenService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return repository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(""));
	}

	public String autenticar(@Valid LoginFormDto dto) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				dto.getLogin(),
				dto.getSenha());
		authentication = authenticationManager.authenticate(authentication);

		return tokenService.gerarToken(authentication);
	}

}
