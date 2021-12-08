package br.hspm.isolamento.infra.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.hspm.isolamento.domain.entities.Paciente;
import br.hspm.isolamento.domain.entities.Usuario;

public interface PacienteRepository extends JpaRepository<Paciente,Long> {
	Page<Paciente> findAllByUsuario(Pageable paginacao, Usuario usuarioLogado);
}
