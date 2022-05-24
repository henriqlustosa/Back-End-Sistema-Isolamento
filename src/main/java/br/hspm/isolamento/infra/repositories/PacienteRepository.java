package br.hspm.isolamento.infra.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import br.hspm.isolamento.domain.entities.Paciente;


public interface PacienteRepository extends JpaRepository<Paciente,Long> {
	
}
