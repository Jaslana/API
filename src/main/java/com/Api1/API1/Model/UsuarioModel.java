package com.Api1.API1.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(length = 50)
    public String nome;
    @Column(unique = true)
    public String cpf;
    @Column(length = 50)
    public String endereco;
    @Column(length = 20)
    public String fone;
    public ContaEnum tipo;

}

