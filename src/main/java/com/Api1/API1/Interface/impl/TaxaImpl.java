package com.Api1.API1.Interface.impl;

import com.Api1.API1.Interface.Taxa;
import com.Api1.API1.Model.ContaEnum;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.OperacoesModel;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.OperacoesRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@Setter
@Getter
public class TaxaImpl implements Taxa {

    ContaEnum tipoconta;
    Double taxa = 0.0;
    Integer qtdsaques = 0;

    @Autowired
    private ContaRepository repository;

    @Autowired
    private OperacoesRepository operacoesRepository;


    @Override
    public Optional<ResponseEntity<ContaModel>> sacarConta(@RequestBody OperacoesModel model) {
        reconhecerTipoConta(model.getNumeroConta());
        Optional<ContaModel> conta = repository.findBynconta(model.getNumeroConta());
            return conta.map(record -> {
                if (record.getQtdSaques() > getQtdsaques()) {
                    record.setSaldo(record.getSaldo() - model.getValor() - getTaxa());
                    ContaModel updated = repository.save(record);
                    operacoesRepository.save(model);
                    return ResponseEntity.ok().body(updated);
                } else {
                    record.setSaldo(record.getSaldo() - model.getValor());
                    ContaModel updated = repository.save(record);
                    operacoesRepository.save(model);
                    return ResponseEntity.ok().body(updated);
                }
            });
        }

    @Override
    public Optional<ResponseEntity<ContaModel>> depositarConta(@RequestBody OperacoesModel model) {
        Optional<ContaModel> conta = repository.findBynconta(model.getNumeroConta());
        conta.map(record -> {
            record.setSaldo(record.getSaldo() + model.getValor());
            ContaModel updated = repository.save(record);
            return ResponseEntity.ok().body(updated);
        });
        return null;
    }

    @Override
    public Optional<ResponseEntity<ContaModel>> transferirContas(@RequestBody OperacoesModel operacoesModel) {
        Optional<ContaModel> contaEntrada = repository.findBynconta(operacoesModel.getNumeroConta());
        contaEntrada.map(record -> {
            record.setSaldo(record.getSaldo() + operacoesModel.getValor());
            ContaModel updated = repository.save(record);
            return ResponseEntity.ok().body(updated);
        });
        return null;
    }

    public Optional<ResponseEntity<ContaModel>> transferirContasSaida(@RequestBody OperacoesModel mode) {
        Optional<ContaModel> contaSaida = repository.findBynconta(mode.getNumeroConta());
        contaSaida.map(record -> {
            record.setSaldo(record.getSaldo() - mode.getValor());
            ContaModel update = repository.save(record);
            return ResponseEntity.ok().body(update);
        });
        return null;
    }

    @Override
    public String reconhecerTipoConta(String nConta) {
        Optional<ContaModel> conta = repository.findBynconta(nConta);
        conta.map(map -> {
            setTipoconta(map.getTipo());
            setTaxa(tipoconta.getTaxa());
            setQtdsaques(tipoconta.getQtSaque());
            return tipoconta;
        });
        return tipoconta.getDesc();
    }


}


