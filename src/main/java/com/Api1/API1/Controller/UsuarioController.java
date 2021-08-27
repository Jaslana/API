package com.Api1.API1.Controller;


import com.Api1.API1.Dto.UsuarioModelDto;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
    }

    @PostMapping(path = "/api/usuarios/salvar")
    @Transactional
    public ResponseEntity<?> salvar(@RequestBody @Valid UsuarioModel clienteModel, UriComponentsBuilder uriBuilder) {
        Optional<UsuarioModel> usuario = repository.findByCpf(clienteModel.getCpf());
        if (usuario.isPresent()) {
            String json = "Deu ruim , esse CPF ja existe :( " + clienteModel.getCpf();
            return ResponseEntity.badRequest().body(json);
        }
        URI uri = uriBuilder.path("/clientes").buildAndExpand(clienteModel.getId()).toUri();
        return ResponseEntity.created(uri).body(repository.save(clienteModel));
    }

    @GetMapping(path = "/api/usuarios/")
    public ResponseEntity consutarCpf(@RequestParam String cpf) {
        Optional<UsuarioModel> usuario = repository.findByCpf(cpf);
        if (usuario.isPresent()) {
            return ResponseEntity.ok().body(usuario);
        } else {
            String erro = "Deu ruim, esse cpf :" + cpf + " Não existe!";
            return ResponseEntity.status(200).body(erro);
        }
    }

    @PostMapping(path = "/api/usuarios/consultar")
    public List<UsuarioModel> consultarTodos() {
        return repository.findAll();
    }


    @PutMapping("/api/usuarios/alterar/")
    @Transactional
    public ResponseEntity<UsuarioModelDto> atualizar(@RequestParam String cpf, @RequestBody
    @Valid UsuarioModelDto cliente, UriComponentsBuilder uriBuilder) {
        Optional<UsuarioModel> busca = repository.findByCpf(cpf);
        if (busca.isPresent()) {
            UsuarioModel clienteModel = cliente.atualizar(cpf, repository);
            URI uri = uriBuilder.path("/clientes").buildAndExpand(clienteModel.getId()).toUri();
            return ResponseEntity.created(uri).body(new UsuarioModelDto(clienteModel));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping(value = "api/usuarios/delete/")
    public ResponseEntity<?> delete(@RequestParam Integer id) {
        Optional<UsuarioModel> cliente = repository.findById(id);
        if (cliente.isPresent()) {
            repository.delete(cliente.get());
            String json = "Cliente com id " + id + " deletado com sucesso.";
            return ResponseEntity.accepted().body(new String[]{json, "CPF -> " + cliente.get().getCpf() + "."});
        } else {
            String json = "Cliente não encontrado.";
            return ResponseEntity.badRequest().body(json);
        }
    }

}
