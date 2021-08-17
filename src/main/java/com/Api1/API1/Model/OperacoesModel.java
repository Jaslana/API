package com.Api1.API1.Model;



import com.Api1.API1.Dto.OperacoesDto;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Entity(name = "operacoes")
public class OperacoesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @NotEmpty
    public String cpf;
    @NotNull
    public double valor;
    @NotNull
    private TipoOperacaoEnum tipoOperacao;
    private LocalDateTime data = LocalDateTime.now();
    public OperacoesModel(Integer id, String cpf, double valor, TipoOperacaoEnum tipoOperacao, LocalDateTime data) {
        this.id = id;
        this.cpf = cpf;
        this.valor = valor;
        this.tipoOperacao = tipoOperacao;
        this.data = data;
    }
    public OperacoesModel() {
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public TipoOperacaoEnum getTipoOperacao() {
        return tipoOperacao;
    }
    public void setTipoOperacao(TipoOperacaoEnum tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}