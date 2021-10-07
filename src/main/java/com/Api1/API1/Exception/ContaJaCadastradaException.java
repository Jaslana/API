package com.Api1.API1.Exception;

import com.Api1.API1.Dto.ResponseDTO.ContaResponseDTO;

public class ContaJaCadastradaException extends RuntimeException{
    public ContaJaCadastradaException(String message) {
        super(message);
    }
}
