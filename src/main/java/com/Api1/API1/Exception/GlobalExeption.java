package com.Api1.API1.Exception;


public class GlobalExeption {
    private String campo;
    private String erro;

    public GlobalExeption(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }

}
