package com.Api1.API1.Dto.RequestDTO;

import com.Api1.API1.Model.ContaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaRequestDTO {

    @CPF
    @Min(11)
    private String userCpf;
    @NotEmpty
    private String numConta;
    @NotNull
    @NotEmpty
    private String agencia;
    @NotNull
    private double saldo;
    @Max(value = 99)
    @NotNull
    private Integer dverif;
    @NotNull
    private ContaEnum tipo;

}
