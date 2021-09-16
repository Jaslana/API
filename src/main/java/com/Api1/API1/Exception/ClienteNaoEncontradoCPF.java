package com.Api1.API1.Exception;

public class ClienteNaoEncontradoCPF extends RuntimeException{
    public ClienteNaoEncontradoCPF(String message, String cpf) {
        super(message);
    }
}
