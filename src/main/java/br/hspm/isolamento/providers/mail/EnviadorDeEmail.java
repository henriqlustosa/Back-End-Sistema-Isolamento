package br.hspm.isolamento.providers.mail;

import org.springframework.scheduling.annotation.Async;



public interface EnviadorDeEmail {

	    @Async
	    void enviarEmail(String destinatario, String assunto, String mensagem);
	    
}