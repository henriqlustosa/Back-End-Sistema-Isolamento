package br.hspm.isolamento.application.dtos.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioFormDto {
	
	    
	    @NotBlank
		public String nome;
	    @NotBlank
		public String login;
	
	    @NotNull
	    @JsonProperty("perfil_id")
	    private Long perfilId;
	    @NotBlank
	    @Email
	    private String email;
}
