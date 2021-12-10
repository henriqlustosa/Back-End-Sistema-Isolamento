package br.hspm.isolamento.application.dtos.response;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PacienteDetalhadoDto extends PacienteDto {

    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data;
    private UsuarioDto autor;
}
