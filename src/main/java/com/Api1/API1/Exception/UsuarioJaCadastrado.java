package com.Api1.API1.Exception;

public class UsuarioJaCadastrado extends RuntimeException {

    public UsuarioJaCadastrado(String message, String cpf) {
        super(message);
    }
}
