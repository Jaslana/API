package com.Api1.API1.Dto.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    public String cpf;
    public String nome;
    public String endereco;
    public String fone;
}
