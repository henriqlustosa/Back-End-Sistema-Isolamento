package br.hspm.isolamento.application.controllers;
import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import br.hspm.isolamento.application.dtos.request.PacienteFormDto;
import br.hspm.isolamento.application.dtos.request.PacienteUpdateFormDto;
import br.hspm.isolamento.application.dtos.response.PacienteDto;
import br.hspm.isolamento.domain.entities.Usuario;
import br.hspm.isolamento.domain.services.PacienteService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
@RequiredArgsConstructor
@RestController
@RequestMapping("/pacientes")
@Api(tags = "Pacientes")
public class PacienteController {
	@Autowired
	private PacienteService pacienteService;
	
	@GetMapping
	public Page<PacienteDto> listar(@PageableDefault(size = 10) Pageable paginacao,@ApiIgnore @AuthenticationPrincipal Usuario usuarioLogado ) {
		return pacienteService.listarTodos(paginacao, usuarioLogado);

	}
	@DeleteMapping("/{id}")
	public ResponseEntity<PacienteDto>  deleteById(@PathVariable Long id, @ApiIgnore @AuthenticationPrincipal Usuario usuarioLogado )  {
		pacienteService.deletar(id,usuarioLogado);
		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@PostMapping
	public ResponseEntity<PacienteDto> createPaciente(@RequestBody @Valid PacienteFormDto pacienteFormDto, UriComponentsBuilder uriBuilder,@ApiIgnore @AuthenticationPrincipal Usuario usuarioLogado ) {
		
		
		PacienteDto pacienteDto = pacienteService.cadastroPaciente(pacienteFormDto, usuarioLogado);
		
		
		URI uri = uriBuilder
				.path("/pacientes/{id}")
				.buildAndExpand(pacienteDto.getId())
				.toUri();

		
		return ResponseEntity.created(uri).body(pacienteDto);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<PacienteDto>findById(@PathVariable Long id,@ApiIgnore @AuthenticationPrincipal Usuario usuarioLogado ) {
		return new ResponseEntity<>(pacienteService.findById(id, usuarioLogado), HttpStatus.OK);

	}		
	@PutMapping
	public ResponseEntity<PacienteDto>  update( @RequestBody @Valid PacienteUpdateFormDto pacienteUpdateFormDto,@ApiIgnore @AuthenticationPrincipal Usuario usuarioLogado )
		{
		
		
			return new ResponseEntity<>(pacienteService.update(pacienteUpdateFormDto, usuarioLogado), HttpStatus.OK);
	}
	
	
	
}
