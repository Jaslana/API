package com.Api1.API1.Controller;


import com.Api1.API1.Dto.ContaModelDto;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.UsuarioRepository;
import org.json.simple.JSONObject;
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
    private ContaRepository contaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ContaController(ContaRepository contaRepository) {
    }


    @PostMapping(path = "/api/contas/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid ContaModel contaModel,
                                    UriComponentsBuilder uriBuilder) {
        Optional<ContaModel> conta = contaRepository.findBynconta(contaModel.getNconta());
        Optional<UsuarioModel> busca = usuarioRepository.findById(contaModel.getUsuario().getId());
        if (conta.isPresent()) {
            JSONObject json = new JSONObject();
            json.put("Erro", "Essa conta ja existe!");
            json.put("Campo", conta);
            return ResponseEntity.badRequest().body(json);
        } else {
            if (busca.isPresent()) {
                URI uri = uriBuilder.path("/conta").buildAndExpand(contaModel.getCodigo()).toUri();
                contaModel.setQtdSaques(0);
                contaModel.setUsuario(busca.get());
                return ResponseEntity.created(uri).body(contaRepository.save(contaModel));
            }
            JSONObject json = new JSONObject();
            json.put("Menssagem", "Esse usuario nao existe!");
            json.put("Campo:", "ID: " + contaModel.getUsuario().getId());
            return ResponseEntity.badRequest().body(json);
        }
    }


    @GetMapping(path = "/api/contas/")
    public ResponseEntity<?> consutarNConta(@RequestParam String nconta) {
        Optional<ContaModel> conta = contaRepository.findBynconta(nconta);
        if (conta.isPresent()) {
            JSONObject json = new JSONObject();
            json.put("Campo", conta);
            json.put("Menssagem", "Conta econtrada com sucesso!");
            return ResponseEntity.accepted().body(json);
        } else {
            JSONObject json = new JSONObject();
            json.put("Menssagem", "Essa conta nao existe!");
            json.put("Campo:", "NumeroConta: " + nconta);
            return ResponseEntity.badRequest().body(json);
        }
    }

    @PostMapping(path = "api/contas/consulta")
    public List<ContaModel> consultarTodos() {
        return contaRepository.findAll();
    }

    

    @PutMapping("/api/contas/alterar/")
    @Transactional
    public ResponseEntity<?> atualizar(@RequestParam String nConta, @RequestBody
    @Valid ContaModelDto conta, UriComponentsBuilder uriBuilder) {
        Optional<ContaModel> busca = contaRepository.findBynconta(nConta);
        if (busca.isPresent()) {
            ContaModel contaModel = conta.atualizar(nConta, contaRepository);
            URI uri = uriBuilder.path("/conta").buildAndExpand(contaModel.getCodigo()).toUri();
            return ResponseEntity.created(uri).body(new ContaModelDto(contaModel));
        }
        JSONObject json = new JSONObject();
        json.put("Erro", "Essa conta nao existe!");
        json.put("Campo", "NumeroConta: " + nConta);
        return ResponseEntity.badRequest().body(json);

    }

    @DeleteMapping(value = "api/contas/delete/")
    public ResponseEntity<?> deletarConta(@RequestParam String nconta) {
        Optional<ContaModel> conta = contaRepository.findBynconta(nconta);
        if (conta.isPresent()) {
            contaRepository.delete(conta.get());
            JSONObject json = new JSONObject();
            json.put("Campo", conta);
            json.put("Menssagem", "Conta deletada com sucesso!");
            return ResponseEntity.accepted().body(json);
        } else {
            JSONObject json = new JSONObject();
            json.put("Erro", "Essa conta nao existe!");
            json.put("Campo", nconta);
            return ResponseEntity.badRequest().body(json);
        }


    }
}





