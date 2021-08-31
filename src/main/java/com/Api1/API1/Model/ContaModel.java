package com.Api1.API1.Model;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contas")
public class ContaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsuarioModel usuario;

    @Column(length = 50)
    @NotEmpty
    private String nconta;
    @NotNull
    @NotEmpty
    @Column(length = 50)
    private String agencia;
    private Integer qtdSaques;
    @NotNull
    private double saldo;
    @Column
    @Max(value = 99)
    @NotNull
    private Integer dverif;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private ContaEnum tipo;

    public ContaModel() {
    }

    public ContaModel(Integer codigo, UsuarioModel usuario, String nconta, String agencia, Integer qtdSaques, double saldo, Integer dverif, ContaEnum tipo) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.nconta = nconta;
        this.agencia = agencia;
        this.qtdSaques = qtdSaques;
        this.saldo = saldo;
        this.dverif = dverif;
        this.tipo = tipo;
    }

    public ContaEnum getTipo() {
        return tipo;
    }

    public void setTipo(ContaEnum tipo) {
        this.tipo = tipo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getNconta() {
        return nconta;
    }

    public void setNconta(String nconta) {
        this.nconta = nconta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Integer getDverif() {
        return dverif;
    }

    public void setDverif(Integer dverif) {
        this.dverif = dverif;
    }

    public void setQtdSaques(Integer qtdSaques) {
        this.qtdSaques = qtdSaques;
    }

    public Integer getQtdSaques() {
        return qtdSaques;
    }
}
