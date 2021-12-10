package br.hspm.isolamento.application.dtos.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PacienteUpdateFormDto  extends PacienteFormDto  {
	@NotNull
	private Long id;
}
