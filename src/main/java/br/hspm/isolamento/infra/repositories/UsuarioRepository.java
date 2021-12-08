package br.hspm.isolamento.infra.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.hspm.isolamento.domain.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByLogin(String login);

	@Query("select u from Usuario u JOIN FETCH u.perfis where u.id = :userId")
	Optional<Usuario> carrregaPorIdComPerfis(Long userId);

}
