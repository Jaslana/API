package com.Api1.API1.Model;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contas")
public class ContaModel {

    @Id
    private Integer codigo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsuarioModel usuario;

    @Column(length = 50)
    @NotNull @NotEmpty
    private String nconta;
    @NotNull @NotEmpty
    @Column(length = 50)
    private String agencia;
    @GeneratedValue(generator = "0")
    private Integer qtdSaques;
    @NotNull
    private double saldo;
    @Column
    @NotNull @Size(min = 2,max = 2)
    private int dverif;
    @Column(length = 20)
    private ContaEnum tipo;

    public ContaModel() {
    }

    public ContaModel(Integer codigo, UsuarioModel usuario, String nconta, String agencia, Integer qtdSaques, double saldo, int dverif, ContaEnum tipo) {
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

    public int getDverif() {
        return dverif;
    }

    public void setDverif(int dverif) {
        this.dverif = dverif;
    }

    public void setQtdSaques(Integer qtdSaques) {
        this.qtdSaques = qtdSaques;
    }

    public Integer getQtdSaques() {
        return qtdSaques;
    }
}
