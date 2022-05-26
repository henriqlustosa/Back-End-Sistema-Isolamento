package br.hspm.isolamento.application.dtos.request;




import java.time.LocalDate;




import javax.validation.constraints.PastOrPresent;


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
	
	
	@NotNull
	private Long prontuario;
	
	 private String nome;
	 @PastOrPresent
	 private LocalDate dtNascimento;
	 
	 private String sexo;
	 
	 private String obito;
	 
	 @PastOrPresent
	 private LocalDate dtObito;

	
	




}
