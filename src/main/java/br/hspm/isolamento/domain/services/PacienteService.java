package br.hspm.isolamento.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.hspm.isolamento.application.dtos.response.PacienteDto;
import br.hspm.isolamento.domain.entities.Paciente;
import br.hspm.isolamento.domain.entities.Usuario;
import br.hspm.isolamento.infra.repositories.PacienteRepository;
import br.hspm.isolamento.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

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
	public Page<PacienteDto> listAll(Pageable paginacao, Usuario usuarioLogado) {
		Page<Paciente> allLivros = pacienteRepository.findAllByUsuario(paginacao, usuarioLogado);
		return allLivros.map(t -> modelMapper.map(t, PacienteDto.class));

	}
}
