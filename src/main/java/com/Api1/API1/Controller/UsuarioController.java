package com.Api1.API1.Controller;

import com.Api1.API1.Dto.UsuarioModelDto;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(path = "/api/usuarios/salvar")
    @Transactional
    public ResponseEntity<?> salvarUsuario(@RequestBody @Valid UsuarioModel usuarioModel,
                                           UriComponentsBuilder uriBuilder) {
        return usuarioService.salvarUsuario(usuarioModel, uriBuilder);

    }

    @GetMapping(path = "/api/usuarios/")
    public ResponseEntity consutarCpf(@RequestParam String cpf) {
        return usuarioService.consutarCpf(cpf);

    }

    @PostMapping(path = "/api/usuarios/consultar")
    public List<UsuarioModel> consultarTodos() {
        return usuarioService.consultarTodos();
    }

    @PutMapping("/api/usuarios/alterar/")
    public ResponseEntity<?> atualizar(@RequestParam String cpf, @RequestBody
    @Valid UsuarioModelDto usuario, UriComponentsBuilder uriBuilder) {
        return usuarioService.atualizar(cpf, usuario, uriBuilder);
    }

    @DeleteMapping(value = "api/usuarios/delete/")
    public ResponseEntity<?> delete(@RequestParam String cpf) {
        return usuarioService.delete(cpf);
    }
}
