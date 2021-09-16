package com.Api1.API1.Exception;

public class ContaNaoEncontradaNconta extends RuntimeException{
    public ContaNaoEncontradaNconta(String message, String nconta) {
        super(message);
    }
}
