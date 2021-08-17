package com.Api1.API1.Model;

import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "user")
public class UsuarioModel {

        @Id
        public Integer id;

        @Column( length = 50)
        @Size (min = 5, max = 35, message = "Esse campo deve conter o nome e sobrenome")
        @NotNull (message = "O nome é um campo obrigatorio")
        public String nome;
        @Column(length = 50)
        @CPF (message = "Número do registro de contribuinte individual brasileiro (CPF) inválido")
        public String cpf;
        @Column(length = 50)
//        @Pattern(regexp ="^(RUA|Rua|R.|AVENIDA|Avenida|AV.|TRAVESSA|Travessa|TRAV.|Trav.) ([a-zA-Z_\s]+)[, ]+(\d+)\s?([-/\da-zDA-Z\\ ]+)?$", RegexOptions.IgnoreCase)
        @NotNull(message = "O endereço é um campo obrigatorio")
        public String endereco;
        @Column(length = 20)
        @NotNull (message = "O telefone é um campo obrigatorio")
//        @Pattern(regexp = "(\\(?\\d{2}\\)?\\s)?(\\d{5}\\-\\d{4})")
        public String fone;
        @NotNull (message = "O campo tipo é um campo obrigatorio")
        public ContaEnum tipo;

        public UsuarioModel() {
        }

        public UsuarioModel(Integer id, String nome, String cpf, String endereco, String fone, ContaEnum tipo) {
                this.id = id;
                this.nome = nome;
                this.cpf = cpf;
                this.endereco = endereco;
                this.fone = fone;
                this.tipo = tipo;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getCpf() {
                return cpf;
        }

        public UsuarioModel setCpf(String cpf) {
                this.cpf = cpf;
                return null;
        }

        public String getEndereco() {
                return endereco;
        }

        public void setEndereco(String endereco) {
                this.endereco = endereco;
        }

        public String getFone() {
                return fone;
        }

        public void setFone(String fone) {
                this.fone = fone;
        }

        public ContaEnum getTipo() {
                return tipo;
        }

        public void setTipo(ContaEnum tipo) {
                this.tipo = tipo;
        }
}

