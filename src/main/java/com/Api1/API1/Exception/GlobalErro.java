package com.Api1.API1.Exception;

public class GlobalErro {

        private String campo;
        private String erro;

        public GlobalErro(String campo, String erro) {
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
