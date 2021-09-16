package com.Api1.API1.Dto.RequestDTO;

import com.Api1.API1.Model.TipoOperacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperacoesRequestDTO {

    public String numeroConta;
    public double valor;
    public double taxa;
    private TipoOperacaoEnum tipoOperacao;
    private LocalDateTime data = LocalDateTime.now();
}
