package com.Api1.API1.Service;


import com.Api1.API1.Dto.UsuarioModelDto;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.UsuarioRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContaRepository contaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
    }

    public ResponseEntity<?> salvarUsuario(UsuarioModel usuarioModel, UriComponentsBuilder uriBuilder) {
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

    public ResponseEntity consutarCpf(String cpf) {
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

    public List<UsuarioModel> consultarTodos() {
        return usuarioRepository.findAll();
    }


    @Transactional
    public ResponseEntity<?> atualizar(String cpf, UsuarioModelDto usuario, UriComponentsBuilder uriBuilder) {
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

    public ResponseEntity<?> delete(String cpf) {
        Optional<UsuarioModel> usuario = usuarioRepository.findByCpf(cpf);
        List<ContaModel> conta = contaRepository.findAllByUsuarioCpf(cpf);

        if (conta.size() == 0) {
            if (usuario.isPresent()) {
                usuarioRepository.delete(usuario.get());
                JSONObject json = new JSONObject();
                json.put("Menssagem", "Deletada com sucesso!");
                json.put("Campo", usuario);
                return ResponseEntity.accepted().body(json);
            } else {
                JSONObject json = new JSONObject();
                json.put("Erro", "Esse usuario nao existe!");
                json.put("Campo", cpf);
                return ResponseEntity.badRequest().body(json);
            }
        }
        JSONObject json = new JSONObject();
        json.put("Campo", conta);
        json.put("Erro", "Existe Conta/s atreladas a esse usuario");

        return ResponseEntity.badRequest().body(json);
    }
}



