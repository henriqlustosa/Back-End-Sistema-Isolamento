package br.hspm.isolamento.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import br.hspm.isolamento.domain.entities.Perfil;



public interface PerfilRepository extends JpaRepository<Perfil,Long> {

}
