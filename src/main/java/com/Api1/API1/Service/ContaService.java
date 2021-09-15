package com.Api1.API1.Service;


import com.Api1.API1.Dto.ContaModelDto;
import com.Api1.API1.Exception.ExceptionDefault;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.UsuarioRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ContaService(ContaRepository contaRepository) {
    }


    public ResponseEntity<?> salvar(ContaModel contaModel,
                                    UriComponentsBuilder uriBuilder) {
        Optional<ContaModel> conta = contaRepository.findBynconta(contaModel.getNconta());
        Optional<UsuarioModel> busca = usuarioRepository.findById(contaModel.getUsuario().getId());
        if (conta.isPresent()) {
            JSONObject json = new JSONObject();
            json.put("Erro", "Essa conta ja existe!");
            json.put("Campo", conta);
            return ResponseEntity.badRequest().body(json);
        } else {
            if (busca.isPresent()) {
                URI uri = uriBuilder.path("/conta").buildAndExpand(contaModel.getCodigo()).toUri();
                contaModel.setQtdSaques(0);
                contaModel.setUsuario(busca.get());
                return ResponseEntity.created(uri).body(contaRepository.save(contaModel));
            }
            JSONObject json = new JSONObject();
            json.put("Menssagem", "Esse usuario nao existe!");
            json.put("Campo:", "ID: " + contaModel.getUsuario().getId());
            return ResponseEntity.badRequest().body(json);
        }
    }


    public ResponseEntity<ContaModel> consutarNConta(String nconta) {

        ContaModel contaModel = contaRepository.findBynconta(nconta).orElseThrow(() -> new ExceptionDefault("Conta não encontrada"));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(contaModel) ;
    }


    public List<ContaModel> consultarTodos() {
        return contaRepository.findAll();
    }

    @Transactional
    public ResponseEntity<ContaModel> atualizar(String nConta, ContaModelDto contaModelDto) {

        ContaModel conta = contaRepository.findBynconta(nConta).orElseThrow(() -> new ExceptionDefault("Conta não encontrada"));
        contaModelDto.atualizar(nConta, contaRepository);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(conta);
    }

    public ResponseEntity<ContaModel> deletarConta(String nconta) {

            ContaModel contaModel = contaRepository.findBynconta(nconta).orElseThrow(()-> new ExceptionDefault("Conta não encontrada"));
            contaRepository.delete(contaModel);

            return ResponseEntity.noContent().build();
    }
}






