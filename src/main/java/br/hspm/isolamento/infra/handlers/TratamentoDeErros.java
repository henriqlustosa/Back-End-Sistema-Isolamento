package br.hspm.isolamento.infra.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.hspm.isolamento.domain.exceptions.*;

@RestControllerAdvice
public class TratamentoDeErros {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		return buildValidationErrorResponse(ex, HttpStatus.BAD_REQUEST, request.getRequestURI());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
			HttpServletRequest request) {
		return buildErrorResponse(ex, HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
		return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro desconhecido",
				request.getRequestURI());
	}

	private ResponseEntity<ErrorResponse> buildValidationErrorResponse(MethodArgumentNotValidException ex,
			HttpStatus status, String path) {

		ErrorResponse errorResponse = ErrorResponse.builder().status(status.value()).erro(ex.getClass().getSimpleName())
				.message("Erro de Validacao").caminho(path).build();

		ex.getFieldErrors()
				.forEach(error -> errorResponse.adicionaErrosDeValidacao(error.getField(), error.getDefaultMessage()));

		return ResponseEntity.status(status).body(errorResponse);
	}

	private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, String message,
			String path) {
		ErrorResponse errorResponse = ErrorResponse.builder().status(status.value()).erro(ex.getClass().getSimpleName())
				.message(message).caminho(path).build();

		return ResponseEntity.status(status).body(errorResponse);
	}
}


