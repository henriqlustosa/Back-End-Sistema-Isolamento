package br.hspm.isolamento.domain.exceptions;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;



@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();
	private Integer status;
	private String erro;
	private String message;
	private List<ValidationError> erros;
	private String caminho;

	public void adicionaErrosDeValidacao(String campo, String mensagem) {
		if (Objects.isNull(erros)) {
			erros = new ArrayList<>();
		}

		erros.add(new ValidationError(campo, mensagem));
	}
}
