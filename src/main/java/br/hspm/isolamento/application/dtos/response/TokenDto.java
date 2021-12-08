package br.hspm.isolamento.application.dtos.response;

import lombok.Getter;

@Getter
public class TokenDto {
	private String Token;
	
	public TokenDto(String token) {
		this.Token = token;
	}


}
