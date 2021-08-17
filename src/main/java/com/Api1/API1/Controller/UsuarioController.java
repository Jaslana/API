package com.Api1.API1.Controller;


import com.Api1.API1.Dto.UsuarioModelDto;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
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

    @GetMapping(path = "/api/usuarios/{cpf}")
    public ResponseEntity consutar(@PathVariable ("cpf") String cpf){
        return repository.findByCpf(cpf)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/usuarios/salvar")
    public ResponseEntity<UsuarioModel> salvar(@RequestBody @Valid UsuarioModel clienteModel, UriComponentsBuilder uriBuilder) {
        repository.save(clienteModel);
        URI uri = uriBuilder.path("/clientes").buildAndExpand(clienteModel.getId()).toUri();
        return ResponseEntity.created(uri).body(clienteModel);
    }
    @DeleteMapping(value = "/api/usuarios/delete/{cpf}")
    public String delete (@PathVariable("cpf") String cpf) {
        Optional<UsuarioModel> cliente = repository.findByCpf(cpf);
        if (cliente.isPresent()) {
            repository.delete(cliente.get());
            return "Usuario excluido " + cpf;
        } else {
            throw new RuntimeException("Usuario nao encontrado " + cpf);
        }
    }

    @PostMapping(path = "/api/usuarios/consultar")
    public List<UsuarioModel> consultarTodos(){
        return repository.findAll();
    }


@PutMapping("/api/usuarios/alterar/{cpf}")
@Transactional
public ResponseEntity<UsuarioModelDto> atualizar(@PathVariable("cpf") String cpf, @RequestBody @Valid UsuarioModelDto cliente, UriComponentsBuilder uriBuilder) {
    Optional <UsuarioModel> busca = repository.findByCpf(cpf);
    if (busca.isPresent()) {
        UsuarioModel clienteModel = cliente.atualizar(cpf, repository);
        URI uri = uriBuilder.path("/clientes").buildAndExpand(clienteModel.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioModelDto(clienteModel));
    }
    return  ResponseEntity.notFound().build();
}

}
