package com.Api1.API1.Dto.RequestDTO;

import com.Api1.API1.Model.ContaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDTO {

    @CPF
    public String cpf;
    @Size(min = 5, max = 35, message = "Esse campo deve conter o nome e sobrenome")
    @NotNull(message = "O nome é um campo obrigatorio")
    public String nome;
    @Pattern(regexp = "([\\w\\W]+)\\s(\\d+)", message = "Informe o nome da Rua e o número apenas.")
    @NotNull(message = "O endereço é um campo obrigatorio")
    public String endereco;
    @NotNull(message = "O telefone é um campo obrigatorio")
    @Pattern(regexp = "^(?:(?:\\+|00)?(55)\\s?)?(?:(?:\\(?[1-9][0-9]\\)?)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})-?(\\d{4}))$", message = "Telefone Invalido")
    public String fone;

}
