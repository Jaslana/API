package com.Api1.API1.Dto.ResponseDTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class UsuarioContasResponse {
    UsuarioResponseDTO usuario;
    List<ContaResponseDTO> contas;

}
