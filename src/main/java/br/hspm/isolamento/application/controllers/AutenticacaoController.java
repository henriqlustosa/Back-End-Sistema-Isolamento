package br.hspm.isolamento.application.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.hspm.isolamento.application.dtos.request.LoginFormDto;
import br.hspm.isolamento.application.dtos.response.TokenDto;
import br.hspm.isolamento.domain.services.AutenticacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@Api(tags = "Autenticação")
@RequiredArgsConstructor
public class AutenticacaoController {
	@Autowired @Lazy
	private AutenticacaoService service;

	@ApiOperation("Autenticar Usuario")
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginFormDto dto) {
		String token = service.autenticar(dto);
		return ResponseEntity.ok(new TokenDto(token));

	}

}
