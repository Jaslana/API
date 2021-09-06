package com.Api1.API1.Controller;

import com.Api1.API1.Dto.OperacoesDto;
import com.Api1.API1.Model.OperacoesModel;
import com.Api1.API1.Service.OperacoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/contas")
public class OperacoesController {

    @Autowired
    private OperacoesService operacoesService;

    @GetMapping("/extrato/")
    public ResponseEntity<?> buscarExtrato(String numeroConta) {
        return operacoesService.buscarExtrato(numeroConta);
    }

    @PostMapping("/deposito")
    public ResponseEntity<?> salvarTransacaoDeposito(@RequestBody @Valid OperacoesModel model,
                                                     UriComponentsBuilder uriBuilder) {
        return operacoesService.salvarTransacaoDeposito(model, uriBuilder);
    }

    @PutMapping("/operacoes/transferir")
    public ResponseEntity<?> transferencias(@RequestBody @Valid OperacoesDto model,
                                            UriComponentsBuilder uriBuilder) {
        return operacoesService.transferencias(model, uriBuilder);
    }

    @PostMapping("/saque")
    public ResponseEntity<?> salvarTransacaoSaque(@RequestBody @Valid OperacoesModel model,
                                                  UriComponentsBuilder uriBuilder)
            throws ExecutionException, InterruptedException, ExecutionException {
        return operacoesService.salvarTransacaoSaque(model, uriBuilder);
    }


}
