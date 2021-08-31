package com.Api1.API1.Controller;


import com.Api1.API1.Dto.UsuarioModelDto;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.OperacoesModel;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.UsuarioRepository;
import org.json.simple.JSONObject;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContaRepository contaRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
    }

    @PostMapping(path = "/api/usuarios/salvar")
    @Transactional
    public ResponseEntity<?> salvar(@RequestBody @Valid UsuarioModel usuarioModel, UriComponentsBuilder uriBuilder) {
        Optional<UsuarioModel> usuario = usuarioRepository.findByCpf(usuarioModel.getCpf());
        if (usuario.isPresent()) {
            JSONObject json = new JSONObject();
            json.put("Campo", usuario);
            json.put("Mensagem:", "Esse usuario ja existe");
            return ResponseEntity.badRequest().body(json);
        }
        JSONObject json = new JSONObject();
        json.put("Campo", usuario);
        json.put("Menssagem", "Usuario cadastrado com sucesso!");
        URI uri = uriBuilder.path("/usuarios").buildAndExpand(usuarioModel.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioRepository.save(usuarioModel));
    }

    @GetMapping(path = "/api/usuarios/")
    public ResponseEntity consutarCpf(@RequestParam String cpf) {
        Optional<UsuarioModel> usuario = usuarioRepository.findByCpf(cpf);
        if (usuario.isPresent()) {
            JSONObject json = new JSONObject();
            json.put("Campo", usuario);
            json.put("Menssagem", "Usuario econtrado com sucesso!");

            return ResponseEntity.accepted().body(json);
        } else {
            JSONObject json = new JSONObject();
            json.put("Menssagem", "Essa usuario nao existe!");
            json.put("Campo:", "CPF: " + cpf);
            return ResponseEntity.badRequest().body(json);
        }
    }

    @PostMapping(path = "/api/usuarios/consultar")
    public List<UsuarioModel> consultarTodos() {
        return usuarioRepository.findAll();
    }


    @PutMapping("/api/usuarios/alterar/")
    @Transactional
    public ResponseEntity<?> atualizar(@RequestParam String cpf, @RequestBody
    @Valid UsuarioModelDto usuario, UriComponentsBuilder uriBuilder) {
        Optional<UsuarioModel> busca = usuarioRepository.findByCpf(cpf);
        if (busca.isPresent()) {
            UsuarioModel usuarioModel = usuario.atualizar(cpf, usuarioRepository);
            URI uri = uriBuilder.path("/usuarios").buildAndExpand(usuarioModel.getId()).toUri();
            JSONObject json = new JSONObject();
            json.put("Campo", usuario);
            json.put("Menssagem", "Usuario atualizado com sucesso!");
            return ResponseEntity.created(uri).body(new UsuarioModelDto(usuarioModel));
        } else {
            JSONObject json = new JSONObject();
            json.put("Erro", "Esse usuario nao existe!");
            json.put("Campo", cpf);
            return ResponseEntity.badRequest().body(json);
        }

    }

    @DeleteMapping(value = "api/usuarios/delete/")
    public ResponseEntity<?> delete(@RequestParam String cpf) {
        Optional<UsuarioModel> usuario = usuarioRepository.findByCpf(cpf);
        List<ContaModel> contas = contaRepository.findAllByUsuarioCpf(cpf);
        if (contas.isEmpty()) {
            if (usuario.isPresent()) {
                usuarioRepository.delete(usuario.get());
                JSONObject json = new JSONObject();
                json.put("Campo", usuario);
                json.put("Menssagem", "Deletada com sucesso!");
                return ResponseEntity.accepted().body(json);
            } else {
                JSONObject json = new JSONObject();
                json.put("Erro", "Esse usuario nao existe!");
                json.put("Campo", cpf);
                return ResponseEntity.badRequest().body(json);
            }
        }
        String json = "Existe " + contas.size() + "conta/s cadastrada nesse cpf ." + contas.get(contas.size());
        return ResponseEntity.badRequest().body(json);
    }


}
