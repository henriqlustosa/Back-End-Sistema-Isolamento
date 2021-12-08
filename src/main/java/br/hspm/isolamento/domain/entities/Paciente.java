package br.hspm.isolamento.domain.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 private Long prontuario;
	 
	 private String nome;
	 
	 private String vinculo;
	 
	 private String orgaoPrefeitura;
	 
	 private Long rfMatricula;
	 
	 private String nomeMae;
	 
	 private LocalDate dataNascimento;
	 
	 
	 
	 
	 
	 
	 

}
