package com.Api1.API1.Service;


import com.Api1.API1.Dto.UsuarioModelDto;

import com.Api1.API1.Exception.RuntimeExceptionCPF;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        //Retornar o corpo json do usuario que ja existe
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
                new RuntimeExceptionCPF("Usuario nao encontrado"));

        usuarioModelDto.atualizar(cpf, usuarioRepository);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioModel);
        //Retornar mensagem que o usuario foi atualizado com sucesso
    }

    public ResponseEntity<UsuarioModel> delete(String cpf) {

        UsuarioModel usuarioModel = usuarioRepository.findByCpf(cpf).orElseThrow(() ->
                new RuntimeExceptionCPF("Usuario nao encontrado"));
        usuarioRepository.delete(usuarioModel);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioModel);
        //retornar mensagem que o usuario foi exluido com sucesso
    }
}



