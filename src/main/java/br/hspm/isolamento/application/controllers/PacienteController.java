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
	public Page<PacienteDto> listar(@PageableDefault(size = 10) Pageable paginacao ) {
		return pacienteService.listarTodos(paginacao);

	}
	@DeleteMapping("/{prontuario}")
	public ResponseEntity<PacienteDto>  deleteById(@PathVariable Long prontuario )  {
		pacienteService.deletar(prontuario);
		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@PostMapping
	public ResponseEntity<PacienteDto> createPaciente(@RequestBody @Valid PacienteFormDto pacienteFormDto, UriComponentsBuilder uriBuilder ) {
		
		
		PacienteDto pacienteDto = pacienteService.cadastroPaciente(pacienteFormDto);
		
		
		URI uri = uriBuilder
				.path("/pacientes/{prontuario}")
				.buildAndExpand(pacienteDto.getProntuario())
				.toUri();

		
		return ResponseEntity.created(uri).body(pacienteDto);
	}

	
	@GetMapping("/{prontuario}")
	public ResponseEntity<PacienteDto>findById(@PathVariable Long prontuario,@ApiIgnore @AuthenticationPrincipal Usuario usuarioLogado ) {
		return new ResponseEntity<>(pacienteService.findById(prontuario,usuarioLogado), HttpStatus.OK);

	}		
	@PutMapping
	public ResponseEntity<PacienteDto>  update( @RequestBody @Valid PacienteUpdateFormDto pacienteUpdateFormDto )
		{
		
		
			return new ResponseEntity<>(pacienteService.update(pacienteUpdateFormDto), HttpStatus.OK);
	}
	
	
	
}
