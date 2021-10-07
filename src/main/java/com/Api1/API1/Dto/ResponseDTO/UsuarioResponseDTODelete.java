package com.Api1.API1.Dto.ResponseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTODelete {
    UsuarioResponseDTO usuarioDeletado;
    String mensagem;
}
