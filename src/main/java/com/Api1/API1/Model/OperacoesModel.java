package com.Api1.API1.Model;


import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity(name = "operacoes")
public class OperacoesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String numeroConta;
    @NotNull
    public double valor;
    public double taxa;
    @NotNull
    private TipoOperacaoEnum tipoOperacao;
    @NotNull
    private LocalDateTime data = LocalDateTime.now();

    public OperacoesModel(Integer id, double taxa, String numeroConta, double valor, TipoOperacaoEnum tipoOperacao, LocalDateTime data) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.valor = valor;
        this.tipoOperacao = tipoOperacao;
        this.data = data;
        this.taxa = taxa;
    }

    public OperacoesModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
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