package br.hspm.isolamento.domain.entities;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Collection;
import java.util.List;

@Data
@ToString(exclude = { "senha" })
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
@EqualsAndHashCode(of="id")
public class Usuario implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String login;
	private String senha;
    private String email;
    @ManyToMany
	@JoinTable(name = "perfis_usuarios", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"

	), inverseJoinColumns = @JoinColumn(name = "perfil_id", referencedColumnName = "id"

	))
	private List<Perfil> perfis = new ArrayList<>();

    public Usuario(Long id, String nome, String login, String senha,String email) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email= email;
    }

	public void atualizarInformacoes(String nome, String login) {
		this.nome = nome;
		this.login = login;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.perfis;
	}

	public void adicionarPerfil(Perfil perfil) {
		this.perfis.add(perfil);
	}

	@Override
	public String getPassword() {

		return senha;
	}

	@Override
	public String getUsername() {

		return login;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

}
