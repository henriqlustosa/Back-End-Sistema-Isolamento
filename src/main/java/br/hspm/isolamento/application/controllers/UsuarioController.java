package br.hspm.isolamento.application.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.hspm.isolamento.application.dtos.request.UsuarioFormDto;
import br.hspm.isolamento.application.dtos.response.UsuarioDto;
import br.hspm.isolamento.domain.services.UsuarioService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
@Api(tags = "Usuários")
public class UsuarioController {
	@Autowired
	private UsuarioService service;	
	@GetMapping
	public Page<UsuarioDto> listar(@PageableDefault(size = 10) Pageable paginacao)
	{

		return service.getUsuarios(paginacao);
	}
	@PostMapping 
	public ResponseEntity<UsuarioDto> cadastrar(@RequestBody @Valid UsuarioFormDto dto, UriComponentsBuilder uriBuilder){
		
		
		UsuarioDto usuarioDto = service.createUsuario(dto);
		
		URI uri = uriBuilder
					.path("/usuarios/{id}")
					.buildAndExpand(usuarioDto.getId())
					.toUri();
					 	

		return ResponseEntity.created(uri).body(usuarioDto);
	}
}
