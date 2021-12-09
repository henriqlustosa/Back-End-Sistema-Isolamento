package br.hspm.isolamento.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.hspm.isolamento.application.dtos.response.UsuarioDto;
import br.hspm.isolamento.domain.entities.Perfil;
import br.hspm.isolamento.domain.entities.Usuario;
import br.hspm.isolamento.domain.services.TokenService;
import br.hspm.isolamento.infra.repositories.UsuarioRepository;
import br.hspm.isolamento.mocks.UsuarioFactory;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UsuarioControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;
	private String token;

	private Usuario usuario;
	private Usuario usuarioLogado;

	@BeforeEach
	void setUp() {
		usuario = new Usuario(null, "Admin", "admin@mail.com", "SuperSecret123", "henriqlustosa@outlook.com");
		usuario.adicionarPerfil(new Perfil(1l, "ROLE_ADMIN"));
		usuarioLogado = usuarioRepository.save(usuario);

		Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioLogado,
				usuarioLogado.getLogin());
		token = "Bearer " + tokenService.gerarToken(authentication);

	}

	@Test
	void naoDeveriaCadastrarUsuarioComDadosIncompletos() throws Exception {
		String json = "{}";

		mvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(json)
				.header(HttpHeaders.AUTHORIZATION, token)).andExpect(status().isBadRequest());
	}

	@Test
	void deveriaCadastrarUmUsuarioComDadosCompletos() throws Exception {
		String json = objectMapper.writeValueAsString(UsuarioFactory.criarUsuarioFormDto());
		UsuarioDto usuarioResponseDto = UsuarioFactory.criarUsuarioResponseDto();

		mvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(json)
				.header(HttpHeaders.AUTHORIZATION, token)).andExpect(status().isCreated())
				.andExpect(header().exists("Location")).andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.nome").value(usuarioResponseDto.getNome()));
	}

}