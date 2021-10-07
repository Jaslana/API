package com.Api1.API1.Controller;

import com.Api1.API1.Dto.RequestDTO.OperacoesRequestDTO;
import com.Api1.API1.Dto.ResponseDTO.OperacoesResponseDTO;
import com.Api1.API1.Model.OperacoesModel;
import com.Api1.API1.Service.OperacoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/contas")
public class OperacoesController {

    @Autowired
    private OperacoesService operacoesService;

    @GetMapping("/extrato/")
    public ResponseEntity<OperacoesResponseDTO> buscarExtrato(OperacoesRequestDTO operacoesRequestDTO) {
        return operacoesService.buscarExtrato(operacoesRequestDTO);
    }

    @PostMapping("/deposito")
    public ResponseEntity<OperacoesResponseDTO> salvarTransacaoDeposito(@RequestBody @Valid OperacoesRequestDTO operacoesRequestDTO) {
        return operacoesService.salvarTransacaoDeposito(operacoesRequestDTO);
    }

//    @PutMapping("/operacoes/transferir")
//    public Optional<Object> transferencias(@RequestBody @Valid OperacoesRequestDTO operacoesRequestDTO) {
//        return operacoesService.transferencias(operacoesRequestDTO);
//    }
//
//    @PostMapping("/saque")
//    public ResponseEntity<?> salvarTransacaoSaque(@RequestBody @Valid OperacoesModel model,
//                                                  UriComponentsBuilder uriBuilder)
//            throws ExecutionException, InterruptedException, ExecutionException {
//        return operacoesService.salvarTransacaoSaque(model, uriBuilder);
//    }


}
