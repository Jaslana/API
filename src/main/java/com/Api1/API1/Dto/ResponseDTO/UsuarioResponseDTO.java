package com.Api1.API1.Dto.ResponseDTO;

import com.Api1.API1.Model.ContaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private String nconta;
    private String agencia;
    private Integer qtdSaques;
    private double saldo;
    private Integer dverif;
    private ContaEnum tipo;

}
