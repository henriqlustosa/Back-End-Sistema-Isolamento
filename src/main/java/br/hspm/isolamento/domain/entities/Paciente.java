package br.hspm.isolamento.domain.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {
	
	
	 @Id
	 private Long prontuario;
	 
	 private String nome;
	 
	 private LocalDate dtNascimento;
	 
	 private String sexo;
	 
	 private String obito;
	 
	 private LocalDate dtObito;
	 
	 
	 public void atualizarInformacoes( String nome, LocalDate dtNascimento, String sexo,String obito,LocalDate dtObito) {
		
			this.nome = nome;
			this.dtNascimento = dtNascimento;
			this.sexo = sexo;
			this.obito = obito;
			this.dtObito = dtObito;
		

		}
	 
	 

}
