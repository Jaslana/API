package com.Api1.API1.Dto;

import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Getter
@Setter
public class UsuarioModelDto {
    private String nome;
    private String cpf;
    private String endereco;
    private String fone;

    public UsuarioModelDto(UsuarioModel clienteModel) {
        this.nome = clienteModel.getNome();
        this.cpf = clienteModel.getCpf();
        this.fone = clienteModel.getFone();
        this.endereco = clienteModel.getEndereco();
    }

    public UsuarioModelDto() {
    }

    public static List<UsuarioModelDto> converter(List<UsuarioModel> clienteModel) {
        return clienteModel.stream().map(UsuarioModelDto::new).collect(Collectors.toList());
    }

    public UsuarioModel atualizar(String cpf, UsuarioRepository clienteRepository) {
        Optional<UsuarioModel> clienteModel = clienteRepository.findByCpf(cpf);
        clienteModel.map(alter -> {
            alter.setNome(this.getNome());
            alter.setCpf(this.getCpf());
            alter.setFone(this.getFone());
            alter.setEndereco(this.getEndereco());
            UsuarioModel updated = clienteRepository.save(alter);
            return ResponseEntity.ok().body(updated);
        });
        return clienteModel.get();
    }
}

