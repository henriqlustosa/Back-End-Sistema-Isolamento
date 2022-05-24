package br.hspm.isolamento.application.dtos.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDto {



	private Long prontuario;

	private String nome;

	private LocalDate dtNascimento;

	private String sexo;

	private String obito;

	private LocalDate dtObito;

}
