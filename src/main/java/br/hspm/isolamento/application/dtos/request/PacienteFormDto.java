package br.hspm.isolamento.application.dtos.request;




import java.time.LocalDate;




import javax.validation.constraints.PastOrPresent;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PacienteFormDto {
	
	
	 private Long prontuario;
	 
	 private String nome;
	 
	 private String vinculo;
	 
	 private String orgaoPrefeitura;
	 
	 private Long rfMatricula;
	 
	 private String nomeMae;
	 
	 @PastOrPresent
	 private LocalDate dataNascimento;
	


	@NotNull
    @JsonAlias("usuario_id")
    private Long usuarioId;

}
