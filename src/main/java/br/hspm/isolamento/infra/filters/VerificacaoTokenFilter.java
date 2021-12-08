package br.hspm.isolamento.infra.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.hspm.isolamento.domain.entities.*;
import br.hspm.isolamento.domain.services.TokenService;
import br.hspm.isolamento.infra.repositories.*;

public class VerificacaoTokenFilter extends OncePerRequestFilter{
	
	private TokenService tokenService;

	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}

		token = token.replace("Bearer ", "");

		boolean tokenValido = tokenService.isValido(token);
		if (tokenValido) {
			
			Long idUsuario = tokenService.extrairIdUsuario(token);
			Usuario logado = usuarioRepository.carrregaPorIdComPerfis(idUsuario).get();
			Authentication authentication = new UsernamePasswordAuthenticationToken(logado, null, logado.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}
	public VerificacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {

		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}
}
