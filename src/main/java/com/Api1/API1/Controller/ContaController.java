package com.Api1.API1.Controller;


import com.Api1.API1.Dto.ContaModelDto;
import com.Api1.API1.Dto.UsuarioModelDto;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ContaController {

    @Autowired
    private ContaRepository repository;


   @GetMapping(path = "/api/contas/")
    public ResponseEntity<?> consutar(@RequestParam String nconta){
       Optional<ContaModel> conta = repository.findBynconta(nconta);
       if(conta.isPresent()){
           return ResponseEntity.ok().body(conta);
       }else {
           String erro = "Deu ruim, essa conta :" + nconta + "Não existe!";
            return ResponseEntity.status(200).body(erro);
       }
    }

    @PostMapping(path = "/api/contas/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid ContaModel contaModel,
                                               UriComponentsBuilder uriBuilder) {
       Optional<ContaModel> conta = repository.findBynconta(contaModel.getNconta());
       if (conta.isPresent()) {
           String json = "Deu ruim , essa conta ja existe :( " + contaModel.getNconta();
           return ResponseEntity.badRequest().body(json);
       }
        URI uri = uriBuilder.path("/conta").buildAndExpand(contaModel.getCodigo()).toUri();
        Integer inicio = 0;
        contaModel.setQtdSaques(inicio);
        return ResponseEntity.created(uri).body(repository.save(contaModel));
    }

    @DeleteMapping(value = "api/contas/delete/")
    public  ResponseEntity<?> deletarConta(@RequestParam String nconta) {
        Optional<ContaModel> conta = repository.findBynconta(nconta);
        if (conta.isPresent()) {
            repository.delete(conta.get());
            String json = "Conta " + nconta + " deletada com sucesso.";
            return ResponseEntity.accepted().body(new String[]{json, "NUMERO DA CONTA: " + conta.get().getNconta() + "."});
        } else {
            String json = "Conta não encontrada.";
            return ResponseEntity.badRequest().body(json);
        }

    }

    @PostMapping(path = "api/contas/consulta")
    public List<ContaModel> consultarTodos(){
        return repository.findAll();
    }

    @PutMapping("/api/contas/alterar/{nConta}")
    @Transactional
    public ResponseEntity<ContaModelDto> atualizar(@PathVariable("nConta") String nConta, @RequestBody
    @Valid ContaModelDto conta, UriComponentsBuilder uriBuilder) {
        Optional <ContaModel> busca = repository.findBynconta(nConta);
        if (busca.isPresent()) {
            ContaModel contaModel = conta.atualizar(nConta, repository);
            URI uri = uriBuilder.path("/conta").buildAndExpand(contaModel.getCodigo()).toUri();
            return ResponseEntity.created(uri).body(new ContaModelDto(contaModel));
        }
            return ResponseEntity.badRequest().build();

    }
}





