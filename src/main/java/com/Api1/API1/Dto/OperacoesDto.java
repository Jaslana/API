package com.Api1.API1.Dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class OperacoesDto {

    public Integer Id;
    @CPF
    public String cpfEntrada;
    @CPF
    public String cpfSaida;
    @NotNull
    public double valor;


    public OperacoesDto(Integer id, String cpfEntrada, String cpfSaida, double valor) {
        this.Id = id;
        this.cpfEntrada = cpfEntrada;
        this.cpfSaida = cpfSaida;
        this.valor = valor;

    }

    public OperacoesDto() {
    }
}
