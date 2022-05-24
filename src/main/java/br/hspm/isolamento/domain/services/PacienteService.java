package br.hspm.isolamento.domain.services;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import br.hspm.isolamento.application.dtos.request.PacienteFormDto;
import br.hspm.isolamento.application.dtos.request.PacienteUpdateFormDto;
import br.hspm.isolamento.application.dtos.response.PacienteDetalhadoDto;
import br.hspm.isolamento.application.dtos.response.PacienteDto;
import br.hspm.isolamento.domain.entities.Paciente;
import br.hspm.isolamento.domain.entities.Usuario;
import br.hspm.isolamento.domain.exceptions.DomainException;
import br.hspm.isolamento.domain.exceptions.ResourceNotFoundException;
import br.hspm.isolamento.infra.repositories.PacienteRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PacienteService {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Transactional(readOnly = true)
	public Page<PacienteDto> listarTodos(Pageable paginacao) {
		Page<Paciente> allpacientes = pacienteRepository.findAll(paginacao);
		return allpacientes.map(t -> modelMapper.map(t, PacienteDto.class));

	}
	
	public void deletar(Long prontuario) {
		try {
		
			
			
			pacienteRepository.deleteById(prontuario);
		 } catch (EntityNotFoundException e) {
	            throw new ResourceNotFoundException("paciente inexistente: " + prontuario);
	        }
	}
	

	public PacienteDto cadastroPaciente(PacienteFormDto pacienteFormDto) {
		try {
			
	    
		Paciente pacienteToSave = modelMapper.map(pacienteFormDto, Paciente.class);
		
		

		Paciente savedpaciente = pacienteRepository.save(pacienteToSave);
		return modelMapper.map(savedpaciente, PacienteDto.class);

		} catch (DataIntegrityViolationException e) {
			throw new DomainException("Usuario inválido");
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Usuario inválido!");
		}
	}
	
	@Transactional(readOnly = true)
	public PacienteDetalhadoDto findById(Long prontuario,  Usuario usuarioLogado) {
		
		Paciente paciente = verifyIfExists(prontuario);
		
		return modelMapper.map(paciente, PacienteDetalhadoDto.class);
	}
	
	@Transactional
	public PacienteDto update(PacienteUpdateFormDto pacienteUpdateFormDto) {
		try {
			
			Paciente paciente = pacienteRepository.getById(pacienteUpdateFormDto.getProntuario());
			
			paciente.atualizarInformacoes( pacienteUpdateFormDto.getNome(),pacienteUpdateFormDto.getDtNascimento(), pacienteUpdateFormDto.getSexo(),
					pacienteUpdateFormDto.getObito(),pacienteUpdateFormDto.getDtObito());
			
			pacienteRepository.save(paciente);
			
			return modelMapper.map(paciente, PacienteDto.class);

		} catch (EntityNotFoundException e) {

			throw new ResourceNotFoundException("paciente inexistente: " + pacienteUpdateFormDto.getProntuario());
		}
	}
	private Paciente verifyIfExists(Long id) {
		return pacienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("paciente não encontrado: " + id));
	}
}
