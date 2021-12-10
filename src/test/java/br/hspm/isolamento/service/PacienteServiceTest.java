package br.hspm.isolamento.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import lombok.var;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;


import org.modelmapper.config.Configuration;

import org.springframework.dao.EmptyResultDataAccessException;

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
	private Usuario usuario = UsuarioFactory.criarUsuario();

	@Test
	void deveriaCadastrarUmPaciente() {
		Configuration configurationMock = Mockito.mock(Configuration.class);
		when(configurationMock.setMatchingStrategy(MatchingStrategies.STRICT)).thenReturn(configurationMock);

		when(modelMapper.getConfiguration()).thenReturn(configurationMock);

		when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
		when(modelMapper.map(pacienteFormDto, Paciente.class)).thenReturn(paciente);
		when(modelMapper.map(paciente, PacienteDto.class)).thenReturn(pacienteDto);
		when(pacienteRepository.save(Mockito.any(Paciente.class))).thenAnswer(i -> i.getArguments()[0]);
		when(usuarioRepository.getById(pacienteFormDto.getUsuarioId())).thenReturn(usuario);

		when(usuarioRepository.getById(pacienteFormDto.getUsuarioId())).thenReturn(usuarioLogado);

		PacienteDto dto = pacienteService.cadastroPaciente(pacienteFormDto, usuarioLogado);
		assertEquals(pacienteFormDto.getProntuario(), dto.getProntuario());
		assertEquals(pacienteFormDto.getNome(), dto.getNome());
		assertEquals(pacienteFormDto.getVinculo(), dto.getVinculo());
		assertEquals(pacienteFormDto.getOrgaoPrefeitura(), dto.getOrgaoPrefeitura());
		assertEquals(pacienteFormDto.getRfMatricula(), dto.getRfMatricula());
		assertEquals(pacienteFormDto.getNomeMae(), dto.getNomeMae());
		assertEquals(pacienteFormDto.getDataNascimento(), dto.getDataNascimento());

		verify(pacienteRepository, times(1)).save(any());

	}

	@Test
	void atualizarDeveLancarResourceNotFoundQuandoPacienteIdInvalido() {
		when(pacienteRepository.getById(anyLong())).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> pacienteService.update(pacienteUpdateFormDto, usuarioLogado));
	}

	@Test
	void findByIdDeveRetornarTransacaoQuandoIdValido() {
		when(pacienteRepository.findById(anyLong())).thenReturn(Optional.of(paciente));
		when(modelMapper.map(paciente, PacienteDetalhadoDto.class)).thenReturn(pacienteDetalhado);
		PacienteDetalhadoDto pacienteResponseDto = pacienteService.findById(1l, usuarioLogado);
		assertEquals(pacienteFormDto.getProntuario(), pacienteResponseDto.getProntuario());
		assertEquals(pacienteFormDto.getNome(), pacienteResponseDto.getNome());
		assertEquals(pacienteFormDto.getVinculo(), pacienteResponseDto.getVinculo());
		assertEquals(pacienteFormDto.getOrgaoPrefeitura(), pacienteResponseDto.getOrgaoPrefeitura());
		assertEquals(pacienteFormDto.getRfMatricula(), pacienteResponseDto.getRfMatricula());
		assertEquals(pacienteFormDto.getNomeMae(), pacienteResponseDto.getNomeMae());
		assertEquals(pacienteFormDto.getDataNascimento(), pacienteResponseDto.getDataNascimento());
		
		verify(pacienteRepository, times(1)).findById(anyLong());

	}

	@Test
	void findByIdDeveLancarResouceNotFoundQuandoIdTransacaoInvalido() {

		assertThrows(ResourceNotFoundException.class, () -> pacienteService.findById(10l, usuarioLogado));
	}

	@Test
	void removerDeveriaLancarResourceNotFoundQuandoIdInvalido() {
		
		doThrow(EntityNotFoundException	.class).when(pacienteRepository).getById(100l);

		assertThrows(ResourceNotFoundException.class, () -> pacienteService.deletar(100L, usuarioLogado));
	}

	@Test
	void removerNaoDeveTerRetornoComIdValido() {
		var validId = 1l;
		when(pacienteRepository.getById(validId)).thenReturn(paciente);
		pacienteService.deletar(validId, usuarioLogado);

		verify(pacienteRepository, times(1)).deleteById(1l);
	}

}
