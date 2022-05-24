package br.hspm.isolamento.service;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import br.hspm.isolamento.application.dtos.request.PacienteFormDto;
import br.hspm.isolamento.application.dtos.request.PacienteUpdateFormDto;
import br.hspm.isolamento.application.dtos.response.PacienteDetalhadoDto;
import br.hspm.isolamento.application.dtos.response.PacienteDto;
import br.hspm.isolamento.domain.entities.Paciente;
import br.hspm.isolamento.domain.entities.Usuario;
import br.hspm.isolamento.domain.exceptions.ResourceNotFoundException;
import br.hspm.isolamento.domain.services.PacienteService;
import br.hspm.isolamento.infra.repositories.PacienteRepository;
import br.hspm.isolamento.infra.repositories.UsuarioRepository;
import br.hspm.isolamento.mocks.PacienteFactory;
import br.hspm.isolamento.mocks.UsuarioFactory;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;


import org.modelmapper.config.Configuration;

//import org.springframework.dao.EmptyResultDataAccessException;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {
	
	@Mock
	private PacienteRepository pacienteRepository;

	@Mock
	private UsuarioRepository usuarioRepository;

	@Mock
	private ModelMapper modelMapper;
	@InjectMocks
	private PacienteService pacienteService;
	
	private PacienteFormDto pacienteFormDto = PacienteFactory.criarPacienteFormDto();

	private Paciente paciente = PacienteFactory.criarPaciente();
	private PacienteDetalhadoDto pacienteDetalhado = PacienteFactory.criarPacienteDetalhadoDto();

	private PacienteUpdateFormDto pacienteUpdateFormDto = PacienteFactory.criarPacienteUpdateFormDtoComIdInvalido();
	private PacienteDto pacienteDto = PacienteFactory.criarPacienteResponseDto();
	private Usuario usuarioLogado = UsuarioFactory.criarUsuario();
	

	@Test
	void deveriaCadastrarUmPaciente() {
		Configuration configurationMock = Mockito.mock(Configuration.class);
		

		

		
		when(modelMapper.map(pacienteFormDto, Paciente.class)).thenReturn(paciente);
		when(modelMapper.map(paciente, PacienteDto.class)).thenReturn(pacienteDto);
		when(pacienteRepository.save(Mockito.any(Paciente.class))).thenAnswer(i -> i.getArguments()[0]);
		

		PacienteDto dto = pacienteService.cadastroPaciente(pacienteFormDto);
	
		assertEquals(pacienteFormDto.getNome(), dto.getNome());
		assertEquals(pacienteFormDto.getDtNascimento(), dto.getDtNascimento());
		assertEquals(pacienteFormDto.getSexo(), dto.getSexo());
		assertEquals(pacienteFormDto.getObito(), dto.getObito());
		assertEquals(pacienteFormDto.getDtObito(), dto.getDtObito());


		verify(pacienteRepository, times(1)).save(any());

	}

	@Test
	void atualizarDeveLancarResourceNotFoundQuandoPacienteIdInvalido() {
		when(pacienteRepository.getById(anyLong())).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> pacienteService.update(pacienteUpdateFormDto));
	}

	@Test
	void findByIdDeveRetornarTransacaoQuandoIdValido() {
		when(pacienteRepository.findById(anyLong())).thenReturn(Optional.of(paciente));
		when(modelMapper.map(paciente, PacienteDetalhadoDto.class)).thenReturn(pacienteDetalhado);
		PacienteDetalhadoDto pacienteResponseDto = pacienteService.findById(1l, usuarioLogado);
		assertEquals(pacienteFormDto.getNome(), pacienteResponseDto.getNome());
		assertEquals(pacienteFormDto.getDtNascimento(), pacienteResponseDto.getDtNascimento());
		assertEquals(pacienteFormDto.getSexo(), pacienteResponseDto.getSexo());
		assertEquals(pacienteFormDto.getObito(), pacienteResponseDto.getObito());
		assertEquals(pacienteFormDto.getDtObito(), pacienteResponseDto.getDtObito());
	
		
		verify(pacienteRepository, times(1)).findById(anyLong());

	}

	@Test
	void findByIdDeveLancarResouceNotFoundQuandoIdTransacaoInvalido() {

		assertThrows(ResourceNotFoundException.class, () -> pacienteService.findById(10l, usuarioLogado));
	}

	@Test
	void removerDeveriaLancarResourceNotFoundQuandoIdInvalido() {
		
		doThrow(EntityNotFoundException	.class).when(pacienteRepository).deleteById(anyLong());

		assertThrows(ResourceNotFoundException.class, () -> pacienteService.deletar(1L));
	}

	@Test
	void removerNaoDeveTerRetornoComIdValido() {
		long validId = 1l;
		

	
		
		
		 doNothing().when(pacienteRepository).deleteById(anyLong());

	        assertDoesNotThrow(() -> pacienteService.deletar(validId));
	        verify(pacienteRepository, times(1)).deleteById(anyLong());
	}

}
