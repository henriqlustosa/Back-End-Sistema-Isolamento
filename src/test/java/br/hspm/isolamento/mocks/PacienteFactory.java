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
        return new Paciente(1L ,  11209913L , "Henrique Lustosa Ribeiro Faria" , "Munícipe" , "HSPM-SP"          ,     85331300L , "Sonia Maria Dias Lustosa" , LocalDate.parse("1982-02-03")      ,  UsuarioFactory.criarUsuario());
    }
    public static Paciente criarPacienteSemId() {
        return new Paciente(null, 11209913L , "Henrique Lustosa Ribeiro Faria" , "Munícipe" , "HSPM-SP"          ,     85331300L , "Sonia Maria Dias Lustosa" , LocalDate.parse("1982-02-03")      ,  UsuarioFactory.criarUsuario());
    }
    
    public static Paciente criarPacienteAtualizado() {
        return new Paciente(1L ,  11209913L , "Update Lorem Ipsum" , "Munícipe" , "HSPM-SP"          ,     85331300L , "Update Sonia Maria Dias Lustosa" , LocalDate.parse("1982-02-03")      ,  UsuarioFactory.criarUsuario());
    }
    public static Paciente criarPaciente(Long prontuario,String nome,String vinculo,String orgaoPrefeitura, Long rfMatricula, String nomeMae, LocalDate dataLancamento,  Usuario usuario) {
        return new Paciente(null, prontuario, nome, vinculo, orgaoPrefeitura, rfMatricula, nomeMae, dataLancamento, usuario);
    }
    public static Paciente criarPaciente( Long id,Long prontuario,String nome,String vinculo,String orgaoPrefeitura, Long rfMatricula, String nomeMae, LocalDate dataLancamento,  Usuario usuario) {
        return new Paciente(id, prontuario, nome, vinculo, orgaoPrefeitura, rfMatricula, nomeMae, dataLancamento, usuario);
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
        return PacienteUpdateFormDto.builder().id(200L).build();
    }
}
