package br.hspm.isolamento.application.dtos.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateFormDto extends UsuarioFormDto {

    @NotNull
    private Long id;
}
