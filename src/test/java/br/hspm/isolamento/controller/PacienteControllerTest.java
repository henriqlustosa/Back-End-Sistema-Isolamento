package br.hspm.isolamento.controller;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.hspm.isolamento.application.dtos.request.PacienteFormDto;
import br.hspm.isolamento.application.dtos.request.PacienteUpdateFormDto;
import br.hspm.isolamento.application.dtos.response.PacienteDto;
import br.hspm.isolamento.domain.entities.Paciente;
import br.hspm.isolamento.domain.entities.Perfil;
import br.hspm.isolamento.domain.entities.Usuario;
import br.hspm.isolamento.domain.services.TokenService;
import br.hspm.isolamento.infra.repositories.PacienteRepository;
import br.hspm.isolamento.infra.repositories.UsuarioRepository;
import br.hspm.isolamento.mocks.PacienteFactory;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PacienteControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    
    @Autowired
    private PacienteRepository pacienteRepository;

    private Long existingId ;
    private Long nonExistingId = 10L;
    private PacienteDto pacienteResponseDto;
   
    private PacienteDto pacienteAtualizaResponseDto;
    private PacienteFormDto pacienteFormDto;
    private PacienteUpdateFormDto pacienteUpdateFormDto;
    
    private Usuario usuarioLogado ;
    private Paciente paciente;
    private Paciente pacienteUpdated;
	
    @Autowired
	private UsuarioRepository usuarioRepository;
    
    
   

	@Autowired
	private TokenService tokenService;

    private String token;
    
    private Usuario usuario;
   
    private static ModelMapper modelMapper = new ModelMapper();


    @BeforeEach
    void setUp() {
    	usuario = new Usuario(null, "Admin", "admin@mail.com", "SuperSecret123","henriqlustosa@outlook.com");
        usuario.adicionarPerfil(new Perfil(1l,"ROLE_ADMIN"));
    	usuarioLogado= usuarioRepository.save(usuario);
    	
		Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioLogado, usuarioLogado.getLogin());
		token = "Bearer " + tokenService.gerarToken(authentication);
		
	
		
		
		paciente = PacienteFactory.criarPaciente(  11209913L , "Lorem Ipsum" , "Munícipe" , "HSPM-SP"          ,     85331300L , "Update Sonia Maria Dias Lustosa" , LocalDate.parse("1982-02-03"),usuarioLogado) ;
		
		
		
		Paciente pacienteSaved =pacienteRepository.save(paciente);
		
		existingId = pacienteSaved.getId();
		
		pacienteUpdated = PacienteFactory.criarPaciente(existingId ,  11209913L , "Update Lorem Ipsum" , "Munícipe" , "HSPM-SP"          ,     85331300L , "Update Sonia Maria Dias Lustosa" , LocalDate.parse("1982-02-03"),usuarioLogado) ;
        pacienteResponseDto = modelMapper.map(pacienteSaved, PacienteDto.class);
    
    	 
    
         
         pacienteFormDto = modelMapper.map(pacienteSaved, PacienteFormDto.class);
         pacienteUpdateFormDto = modelMapper.map(pacienteUpdated, PacienteUpdateFormDto.class);
         pacienteAtualizaResponseDto = modelMapper.map(pacienteUpdated, PacienteDto.class);

    }

    @Test
    void findByIdShouldReturnAnBookWhenIdExists() throws Exception {
        mockMvc.perform(get("/pacientes/{id}", existingId).header(HttpHeaders.AUTHORIZATION, token)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pacienteResponseDto.getId()));
    }
    @Test
    void criarShouldReturnBadRequestWhenInvalidDataWasProvided() throws Exception {
        String invalidData = "{}";

        mockMvc.perform(post("/pacientes").contentType(MediaType.APPLICATION_JSON).content(invalidData).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void criarShouldReturnAnBookWhenSuccessfully() throws Exception {
        String validData = objectMapper.writeValueAsString(pacienteFormDto);

        mockMvc.perform(post("/pacientes").contentType(MediaType.APPLICATION_JSON).content(validData).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(header().exists("Location")).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(pacienteResponseDto.getId() + 1l));
        
    }

    @Test
    void atualizarShouldReturnAnBookWhenSuccessfully() throws Exception {
        String validData = objectMapper.writeValueAsString(pacienteUpdateFormDto);

        mockMvc.perform(put("/pacientes").contentType(MediaType.APPLICATION_JSON).content(validData).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(pacienteAtualizaResponseDto.getId()))
                .andExpect(jsonPath("$.nome").value(pacienteAtualizaResponseDto.getNome()));
    }

    @Test
    void atualizarShouldReturnBadRequestWhenInvalidData() throws Exception {
        pacienteUpdateFormDto.setDataNascimento( LocalDate.parse("2022-01-01"));
        String invalidData = objectMapper.writeValueAsString(pacienteUpdateFormDto);

        mockMvc.perform(put("/pacientes").contentType(MediaType.APPLICATION_JSON).content(invalidData).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletarShouldReturnBadRequestWhenInvalidId() throws Exception {
        mockMvc.perform(delete("/pacientes/{id}", nonExistingId).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletarShouldDoNothingWhenValidId() throws Exception {
        mockMvc.perform(delete("/pacientes/{id}", existingId).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNoContent());
    }

}
