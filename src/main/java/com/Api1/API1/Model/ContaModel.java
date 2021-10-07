package com.Api1.API1.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contas")
public class ContaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

//    @ManyToOne
//    @JoinColumn(name = "user_cpf")
    private String userCpf;

    @Column(length = 50)
    private String numConta;

    @Column(length = 50)
    private String agencia;
    private Integer qtdSaques;
    private double saldo;
    @Column
    private Integer dverif;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private ContaEnum tipo;

}
