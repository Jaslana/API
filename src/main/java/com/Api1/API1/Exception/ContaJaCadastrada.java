package com.Api1.API1.Exception;

public class ContaJaCadastrada extends RuntimeException{
    public ContaJaCadastrada(String message, String nconta) {
        super(message);
    }
}
