package com.Api1.API1.Dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class OperacoesDto {

    public Integer Id;
    @NotNull
    public String NumeroContaEntrada;
    @NotNull
    public String NumeroContaSaida;
    @NotNull
    public double valor;


    public OperacoesDto(Integer id, String numeroContaEntrada, String numeroContaSaida, double valor) {
        this.Id = id;
        this.NumeroContaEntrada = numeroContaEntrada;
        this.NumeroContaSaida = numeroContaSaida;
        this.valor = valor;

    }

    public OperacoesDto() {
    }
}
