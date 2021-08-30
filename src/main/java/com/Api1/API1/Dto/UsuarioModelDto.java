package com.Api1.API1.Dto;

import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.ResponseEntity;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Getter
@Setter
public class UsuarioModelDto {

    @Column(length = 50)
    @Size(min = 5, max = 35, message = "Esse campo deve conter o nome e sobrenome")
    @NotNull(message = "O nome é um campo obrigatorio")
    private String nome;
    @Column(length = 50)
    @CPF
    private String cpf;
    @Pattern(regexp = "([\\w\\W]+)\\s(\\d+)", message = "Informe o nome da Rua e o número apenas.")
    @NotNull(message = "O endereço é um campo obrigatorio")
    private String endereco;
    @NotNull(message = "O telefone é um campo obrigatorio")
    @Pattern(regexp = "^(?:(?:\\+|00)?(55)\\s?)?(?:(?:\\(?[1-9][0-9]\\)?)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})-?(\\d{4}))$", message = "Telefone Invalido")
    public String fone;

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

