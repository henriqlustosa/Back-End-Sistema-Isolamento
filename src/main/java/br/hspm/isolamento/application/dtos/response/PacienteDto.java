package br.hspm.isolamento.application.dtos.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDto {

	private Long id;

	private Long prontuario;

	private String nome;

	private String vinculo;

	private String orgaoPrefeitura;

	private Long rfMatricula;

	private String nomeMae;

	private LocalDate dataNascimento;

}
