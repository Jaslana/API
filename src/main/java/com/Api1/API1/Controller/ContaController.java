package com.Api1.API1.Controller;

import com.Api1.API1.Dto.ContaModelDto;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping(path = "/api/contas/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid ContaModel contaModel,
                                    UriComponentsBuilder uriBuilder) {
        return contaService.salvar(contaModel, uriBuilder);
    }

    @GetMapping(path = "/api/contas/")
    public ResponseEntity<?> consutarNConta(@RequestParam String nconta) {
        return contaService.consutarNConta(nconta);
    }

    @PostMapping(path = "api/contas/consulta")
    public List<ContaModel> consultarTodos() {
        return contaService.consultarTodos();
    }

    @PutMapping("/api/contas/alterar/")
    public ResponseEntity<?> atualizar(@RequestParam String nConta, @RequestBody
    @Valid ContaModelDto conta, UriComponentsBuilder uriBuilder) {
        return contaService.atualizar(nConta, conta, uriBuilder);
    }

    @DeleteMapping(value = "api/contas/delete/")
    public ResponseEntity<?> deletarConta(@RequestParam String nconta) {
        return contaService.deletarConta(nconta);
    }
}
