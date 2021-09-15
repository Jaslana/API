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
    public ResponseEntity<UsuarioModel> salvarUsuario(@RequestBody @Valid UsuarioModel usuarioModel) {
        return usuarioService.salvar(usuarioModel);

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
    public ResponseEntity<UsuarioModel> atualizar(@RequestParam String cpf, @RequestBody
    @Valid UsuarioModelDto usuarioModelDto) {
        return usuarioService.atualizar(cpf, usuarioModelDto);
    }

    @DeleteMapping(value = "api/usuarios/delete/")
    public ResponseEntity<?> delete(@RequestParam String cpf) {
        return usuarioService.delete(cpf);
    }
}
