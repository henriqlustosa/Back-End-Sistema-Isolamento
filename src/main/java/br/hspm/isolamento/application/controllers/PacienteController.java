package br.hspm.isolamento.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import br.hspm.isolamento.application.dtos.response.PacienteDto;
import br.hspm.isolamento.domain.entities.Usuario;
import br.hspm.isolamento.domain.services.PacienteService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pacientes")
@Api(tags = "Pacientes")
public class PacienteController {
	@Autowired
	private PacienteService pacienteService;
	
	@GetMapping
	public Page<PacienteDto> listar(@PageableDefault(size = 10) Pageable paginacao,@ApiIgnore @AuthenticationPrincipal Usuario usuarioLogado ) {
		return pacienteService.listAll(paginacao, usuarioLogado);

	}
	

}
