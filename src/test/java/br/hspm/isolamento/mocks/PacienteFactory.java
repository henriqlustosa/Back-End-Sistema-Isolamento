package br.hspm.isolamento.mocks;
import java.time.LocalDate;

import org.modelmapper.ModelMapper;

import br.hspm.isolamento.application.dtos.request.PacienteFormDto;
import br.hspm.isolamento.application.dtos.request.PacienteUpdateFormDto;
import br.hspm.isolamento.application.dtos.response.PacienteDetalhadoDto;
import br.hspm.isolamento.application.dtos.response.PacienteDto;
import br.hspm.isolamento.domain.entities.Paciente;
import br.hspm.isolamento.domain.entities.Usuario;
public class PacienteFactory {
	
    private static ModelMapper modelMapper = new ModelMapper();
    
    public static Paciente criarPaciente() {
        return new Paciente( 11209913L , "Henrique Lustosa Ribeiro Faria" , LocalDate.parse("1982-02-03") ,"Masculino" , "Sim" , LocalDate.parse("2022-02-03")  );
    }
    public static Paciente criarPacienteSemId() {
        return new Paciente(null,"Helio RIberio de Faria" , LocalDate.parse("1950-01-01") ,"Masculino" , "Sim" , LocalDate.parse("2022-05-06")   );
    }
    
    public static Paciente criarPacienteAtualizado() {
        return new Paciente(11209913L , "Update Lorem Ipsum" , LocalDate.parse("1950-01-01") ,"Masculino" , "Sim" , LocalDate.parse("2022-05-06")   );
    }
    public static Paciente criarPaciente(String nome,LocalDate dtNascimento,String sexo, String obito, LocalDate dtObito) {
        return new Paciente( null, nome, dtNascimento, sexo, obito, dtObito);
    }
    public static Paciente criarPaciente( Long prontuario,String nome,LocalDate dtNascimento,String sexo, String obito, LocalDate dtObito) {
        return new Paciente( prontuario, nome, dtNascimento, sexo, obito, dtObito);
    }
    public static PacienteDto criarPacienteResponseDto() {
        return modelMapper.map(criarPaciente(), PacienteDto.class);
    }
    public static PacienteDetalhadoDto criarPacienteDetalhadoDto() {
        return modelMapper.map(criarPaciente(), PacienteDetalhadoDto.class);
    }
    public static PacienteDto criarPacienteAtualizadoResponseDto() {
        return modelMapper.map(criarPacienteAtualizado(), PacienteDto.class);
    }

    public static PacienteFormDto criarPacienteFormDto() {
        return modelMapper.map(criarPaciente(), PacienteFormDto.class);
    }

    public static PacienteUpdateFormDto criarPacienteUpdateFormDto() {
        return modelMapper.map(criarPacienteAtualizado(), PacienteUpdateFormDto.class);
    }
    
	
	
    public static PacienteUpdateFormDto criarPacienteUpdateFormDtoComIdInvalido() {
        return PacienteUpdateFormDto.builder().prontuario(200L).build();
    }
}
