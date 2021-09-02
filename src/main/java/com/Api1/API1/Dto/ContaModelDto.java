package com.Api1.API1.Dto;

import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Repository.ContaRepository;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Getter
@Setter
public class ContaModelDto {

    @NotNull
    @NotEmpty
    private String nconta;
    @NotNull
    @NotEmpty
    private String agencia;
    @NotNull
    @Max(value = 99)
    private int dverif;

    public ContaModelDto(ContaModel contaModel) {
        this.nconta = contaModel.getNconta();
        this.agencia = contaModel.getAgencia();
        this.dverif = contaModel.getDverif();
    }

    public ContaModelDto() {
    }

    public static List<ContaModelDto> converter(List<ContaModel> clienteModel) {
        return clienteModel.stream().map(ContaModelDto::new).collect(Collectors.toList());
    }

    public ContaModel atualizar(String nconta, ContaRepository contaRepository) {
        Optional<ContaModel> contaModel = contaRepository.findBynconta(nconta);
        contaModel.map(alter -> {
            alter.setAgencia(this.getAgencia());
            alter.setNconta(this.getNconta());
            alter.setDverif(this.getDverif());
            ContaModel updated = contaRepository.save(alter);
            return ResponseEntity.ok().body(updated);
        });
        return contaModel.get();
    }

}

