package com.Api1.API1.Service;


import com.Api1.API1.Dto.UsuarioModelDto;
import com.Api1.API1.Exception.ExceptionDefault;

import com.Api1.API1.Exception.RuntimeExceptionCPF;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.UsuarioRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
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

    public ResponseEntity<UsuarioModel> salvar(UsuarioModel usuarioModel) {
        UsuarioModel usuario = usuarioRepository.findByCpf(usuarioModel.getCpf()).map(busca->{
            if(busca.getId() >= 1){
                throw new IllegalStateException("Usuario ja existe");
            }
            return busca;
        }).orElseGet(() ->
            usuarioRepository.save(usuarioModel));

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    public ResponseEntity<UsuarioModel> consutarCpf(String cpf) {
        UsuarioModel usuarioModel = usuarioRepository.findByCpf(cpf).orElseThrow(() ->
                new RuntimeExceptionCPF("Usuario nao encontrado"));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioModel);
    }

    public List<UsuarioModel> consultarTodos() {
        return usuarioRepository.findAll();
    }


    @Transactional
    public ResponseEntity<UsuarioModel> atualizar(String cpf, UsuarioModelDto usuarioModelDto) {

        UsuarioModel usuarioModel = usuarioRepository.findByCpf(cpf).orElseThrow(() ->
                new ExceptionDefault("Usuario nao encontrado"));

        usuarioModelDto.atualizar(cpf, usuarioRepository);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioModel);

    }

    public ResponseEntity<UsuarioModel> delete(String cpf) {

        UsuarioModel usuarioModel = usuarioRepository.findByCpf(cpf).orElseThrow(()-> new ExceptionDefault("Usuario nao encontrado"));
        usuarioRepository.delete(usuarioModel);

        return  ResponseEntity.noContent().build();
    }
}



