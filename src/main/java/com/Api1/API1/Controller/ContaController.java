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


   @GetMapping(path = "/api/contas/{cpf}")
    public ResponseEntity consutar(@PathVariable("codigo") String cpf){
        return repository.findByUsuarioCpf(cpf)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/contas/salvar")
    public ResponseEntity<ContaModel> salvar(@RequestBody @Valid ContaModel contaModel,
                                               UriComponentsBuilder uriBuilder) {
        URI uri = uriBuilder.path("/conta").buildAndExpand(contaModel.getCodigo()).toUri();
        return ResponseEntity.created(uri).body(repository.save(contaModel));
    }

    @DeleteMapping(value = "api/contas/delete/{nconta}")
    public String delete (@PathVariable("nconta") String nconta) {
        Optional<ContaModel> cliente = repository.findBynconta(nconta);
        if (cliente.isPresent()) {
            repository.delete(cliente.get());
            return "Conta deletada " + nconta;
        } else {
            throw new RuntimeException("Conta não exite " + nconta);
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
        return  ResponseEntity.badRequest().build();
    }


}





