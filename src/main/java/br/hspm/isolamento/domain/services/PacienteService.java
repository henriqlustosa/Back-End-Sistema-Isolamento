package br.hspm.isolamento.domain.services;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.convention.MatchingStrategies;

import br.hspm.isolamento.application.dtos.request.PacienteFormDto;
import br.hspm.isolamento.application.dtos.request.PacienteUpdateFormDto;
import br.hspm.isolamento.application.dtos.response.PacienteDetalhadoDto;
import br.hspm.isolamento.application.dtos.response.PacienteDto;
import br.hspm.isolamento.domain.entities.Paciente;
import br.hspm.isolamento.domain.entities.Usuario;
import br.hspm.isolamento.domain.exceptions.DomainException;
import br.hspm.isolamento.domain.exceptions.ResourceNotFoundException;
import br.hspm.isolamento.infra.repositories.PacienteRepository;
import br.hspm.isolamento.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
@Service
@RequiredArgsConstructor
public class PacienteService {

	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Transactional(readOnly = true)
	public Page<PacienteDto> listarTodos(Pageable paginacao, Usuario usuarioLogado) {
		Page<Paciente> allpacientes = pacienteRepository.findAllByUsuario(paginacao, usuarioLogado);
		return allpacientes.map(t -> modelMapper.map(t, PacienteDto.class));

	}
	
	public void deletar(Long id,  Usuario usuarioLogado) {
		try {
			Paciente paciente = pacienteRepository.getById(id);
			
			if (!paciente.getUsuario().equals(usuarioLogado)) {
				throw obterExcecaoDeAcessoNegado();
			}
			pacienteRepository.deleteById(id);
		 } catch (EntityNotFoundException e) {
	            throw new ResourceNotFoundException("paciente inexistente: " + id);
	        }
	}
	
	private AccessDeniedException obterExcecaoDeAcessoNegado() {
		return new AccessDeniedException("Acesso negado!");
	}
	public PacienteDto cadastroPaciente(PacienteFormDto pacienteFormDto,  Usuario usuarioLogado) {
		try {
			Usuario usuario = usuarioRepository.getById(pacienteFormDto.getUsuarioId());

			if (!usuario.equals(usuarioLogado)) {
				throw obterExcecaoDeAcessoNegado();
			}
	    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Paciente pacienteToSave = modelMapper.map(pacienteFormDto, Paciente.class);
		pacienteToSave.setId(null);
		pacienteToSave.setUsuario(usuarioRepository.getById(pacienteFormDto.getUsuarioId()));
		

		Paciente savedpaciente = pacienteRepository.save(pacienteToSave);
		return modelMapper.map(savedpaciente, PacienteDto.class);

		} catch (DataIntegrityViolationException e) {
			throw new DomainException("Usuario inválido");
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Usuario inválido!");
		}
	}
	
	@Transactional(readOnly = true)
	public PacienteDetalhadoDto findById(Long id,  Usuario usuarioLogado) {
		Paciente paciente = verifyIfExists(id);
		if (!paciente.getUsuario().equals(usuarioLogado)) {
			throw obterExcecaoDeAcessoNegado();
		}
		return modelMapper.map(paciente, PacienteDetalhadoDto.class);
	}
	
	@Transactional
	public PacienteDto update(PacienteUpdateFormDto pacienteUpdateFormDto, Usuario usuarioLogado) {
		try {
			Paciente paciente = pacienteRepository.getById(pacienteUpdateFormDto.getId());
			if (!paciente.getUsuario().equals(usuarioLogado)) {
				throw obterExcecaoDeAcessoNegado();
			}
			paciente.atualizarInformacoes(pacienteUpdateFormDto.getProntuario(), pacienteUpdateFormDto.getNome(),pacienteUpdateFormDto.getVinculo(), pacienteUpdateFormDto.getOrgaoPrefeitura(),
					pacienteUpdateFormDto.getRfMatricula(),pacienteUpdateFormDto.getNomeMae(), pacienteUpdateFormDto.getDataNascimento());
			pacienteRepository.save(paciente);
			return modelMapper.map(paciente, PacienteDto.class);

		} catch (EntityNotFoundException e) {

			throw new ResourceNotFoundException("paciente inexistente: " + pacienteUpdateFormDto.getId());
		}
	}
	private Paciente verifyIfExists(Long id) {
		return pacienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("paciente não encontrado: " + id));
	}
}
