package com.Api1.API1.Interface;


import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.OperacoesModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


public interface Taxa {
    public Optional<ResponseEntity<ContaModel>> sacarConta(@RequestBody OperacoesModel model);

    public String reconhecerTipoConta(String cpf);

    public Optional<ResponseEntity<ContaModel>> depositarConta(@RequestBody OperacoesModel model);

    public Optional<ResponseEntity<ContaModel>> transferirContas(@RequestBody OperacoesModel model);
    //public Boolean varificarSaldo ( String numConto, OperacoesModel operacoesModel);

}
