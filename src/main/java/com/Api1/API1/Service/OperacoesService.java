package com.Api1.API1.Service;

import com.Api1.API1.Dto.OperacoesDto;
import com.Api1.API1.Exception.ExceptionDefault;
import com.Api1.API1.Interface.impl.TaxaImpl;
import com.Api1.API1.Kafka.KafkaProducerSaque;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.OperacoesModel;
import com.Api1.API1.Model.TipoOperacaoEnum;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.OperacoesRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping(path = "/api/contas")
public class OperacoesService extends TaxaImpl {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private OperacoesRepository operacoesRepository;

    public ResponseEntity<OperacoesModel> buscarExtrato(String numeroConta) {
        List <OperacoesModel> operacoesModel = Optional.ofNullable(operacoesRepository.findAllByNumeroConta(numeroConta))
                .orElseThrow(() -> new ExceptionDefault("Deu ruim parça"));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(operacoesModel.get(operacoesModel.size()));
    }

    public ResponseEntity<OperacoesModel> salvarTransacaoDeposito(String nconta) {
        OperacoesModel operacoesModel = operacoesRepository.findByNumeroConta(nconta).orElseThrow(() -> new ExceptionDefault("Essa transação nao existe"));
                operacoesRepository.save(operacoesModel);
                depositarConta(operacoesModel);
                return ResponseEntity.status(HttpStatus.CREATED).body(operacoesModel);
    }

    public Optional<Object> transferencias(OperacoesDto model, UriComponentsBuilder uriBuilder) {
        Optional<Object> conta1 = contaRepository.findBynconta(model.getNumeroContaEntrada()).map(record -> {
           Optional<Object> conta2 = contaRepository.findBynconta(model.getNumeroContaSaida()).map(busca -> {
                    OperacoesModel transacaoEntrada = new OperacoesModel(model.getId(), 0, model.getNumeroContaEntrada(), model.getValor(), TipoOperacaoEnum.TransferenciaEntrada, LocalDateTime.now());
                    OperacoesModel transacaoSaida = new OperacoesModel(model.getId(), 0, model.getNumeroContaSaida(), model.getValor(), TipoOperacaoEnum.TransferenciaSaida, LocalDateTime.now());
                    transferirContas(transacaoEntrada);
                    transferirContasSaida(transacaoSaida);
                    operacoesRepository.save(transacaoEntrada);
                    operacoesRepository.save(transacaoSaida);
                    return ResponseEntity.status(HttpStatus.CREATED).body(transacaoSaida);
                });
            return conta2;
        });
        if (conta1.isPresent()){
            new ExceptionDefault("As isso não existe");
        }
    return conta1;
    //responder com oj diferente
    }


    public ResponseEntity<?> salvarTransacaoSaque(OperacoesModel model, UriComponentsBuilder uriBuilder)
            throws  InterruptedException, ExecutionException {
        Optional<ContaModel> busca = contaRepository.findBynconta(model.getNumeroConta());
        if (busca.isPresent()) {
            if (model.getTipoOperacao().equals(TipoOperacaoEnum.Saque)) {
                return validacaodesaque(model, uriBuilder);
            }
        }
        JSONObject json = new JSONObject();
        json.put("Campo" , model.getNumeroConta());
        json.put("Mensagem:", "O numero de conta nao existe");
        return ResponseEntity.badRequest().body(json);
    }

    public int teste(Integer qtdsaques){
        switch (qtdsaques) {
            case 0:
               return 5 ;
            case 1:
                return 4 ;
            case 2:
                return 3 ;
            case 3:
                return 2 ;
            case 4:
                return 1 ;
            default:  return 0 ;
        }

    }

    public ResponseEntity<?> validacaodesaque(@RequestBody @Valid OperacoesModel model,
                                                           UriComponentsBuilder uriBuilder)
            throws ExecutionException, InterruptedException {
        Optional<ContaModel> busca = contaRepository.findBynconta(model.getNumeroConta());
        if (busca.get().getSaldo() <= model.getValor()) {
            return ResponseEntity.badRequest().build();
        }
        sacarConta(model);
        URI uri = uriBuilder.path("/contas/{id}").buildAndExpand(model.getId()).toUri();
        JSONObject json = new JSONObject();
        json.put("Salvo",model);
        json.put("Message","Você possui " + teste(busca.get().getQtdSaques()) +
                " saques gratuitos, após zerar saques gratuitos será cobrada uma taxa " +
                "adicional de: "+ busca.get().getTipo().getTaxa());
        KafkaProducerSaque ks = new KafkaProducerSaque();
        ks.EnviarDadosClienteSaque(model.getNumeroConta());
        return ResponseEntity.created(uri).body(json);
    }
}
