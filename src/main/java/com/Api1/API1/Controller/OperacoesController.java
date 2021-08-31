package com.Api1.API1.Controller;

import com.Api1.API1.Dto.OperacoesDto;
import com.Api1.API1.Interface.impl.TaxaImpl;
import com.Api1.API1.Kafka.KafkaProducerSaque;
import com.Api1.API1.Model.ContaEnum;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.OperacoesModel;
import com.Api1.API1.Model.TipoOperacaoEnum;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.OperacoesRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping(path = "/api/contas")
public class OperacoesController extends TaxaImpl {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private OperacoesRepository operacoesRepository;

    @GetMapping("/extrato/")
    public ResponseEntity<?> buscarExtrato(String numeroConta) {
        List<OperacoesModel> busca = operacoesRepository.findAllByNumeroConta(String.valueOf(numeroConta));
        if (busca.isEmpty()) {
            JSONObject json = new JSONObject();
            json.put("erro", "Essa conta " + numeroConta + " não possui extrato ou não existe!");
            json.put("campo","numeroConta");
            return ResponseEntity.badRequest().body(json);
        }
        return ResponseEntity.ok().body(operacoesRepository.findAllByNumeroConta(String.valueOf(numeroConta)));
    }

    @PostMapping("/deposito")
    public ResponseEntity<?> salvarTransacaoDeposito(@RequestBody @Valid OperacoesModel model,
                                                                  UriComponentsBuilder uriBuilder) {
        Optional<ContaModel> busca = contaRepository.findBynconta(model.getNumeroConta());
        if (busca.isPresent()) {
            if (model.getTipoOperacao().equals(TipoOperacaoEnum.Deposito)) {
                operacoesRepository.save(model);
                depositarConta(model);
                URI uri = uriBuilder.path("/contas/{id}").buildAndExpand(model.getId()).toUri();
                return ResponseEntity.created(uri).body(model);
            }
        }
        JSONObject json = new JSONObject();
        json.put("Campo", model.getNumeroConta());
        json.put("Mensagem:", "O numero de conta nao existe");
        return ResponseEntity.badRequest().body(json);
    }

    @PutMapping("/operacoes/transferir")
    public ResponseEntity<?> transferencias(@RequestBody @Valid OperacoesDto model,
                                                         UriComponentsBuilder uriBuilder) {
        Optional<ContaModel> conta1 = contaRepository.findBynconta(model.getNumeroContaEntrada());
        Optional<ContaModel> conta2 = contaRepository.findBynconta(model.getNumeroContaSaida());
        if(conta1.isPresent() && conta2.isPresent()) {
            OperacoesModel transacaoEntrada = new OperacoesModel(model.getId(), model.getNumeroContaEntrada(),
                    model.getValor(), TipoOperacaoEnum.TransferenciaEntrada, LocalDateTime.now());
            OperacoesModel transacaoSaida = new OperacoesModel(model.getId(), model.getNumeroContaSaida(),
                    model.getValor(), TipoOperacaoEnum.TransferenciaSaida, LocalDateTime.now());
            transferirContas(transacaoEntrada);
            transferirContasSaida(transacaoSaida);
            operacoesRepository.save(transacaoEntrada);
            operacoesRepository.save(transacaoSaida);
            URI uri = uriBuilder.path("/contas/{id}").buildAndExpand(transacaoSaida.getId()).toUri();
            return ResponseEntity.created(uri).body(transacaoSaida);
        }
        JSONObject json = new JSONObject();
        json.put("Campo:", model.getNumeroContaSaida() + ", " + model.getNumeroContaSaida());
        json.put("Mensagem:", "O numero de conta nao existe");

        return ResponseEntity.badRequest().body(json);
    }

    @PostMapping("/saque")
    public ResponseEntity<?> salvarTransacaoSaque(@RequestBody @Valid OperacoesModel model,
                                                               UriComponentsBuilder uriBuilder)
            throws ExecutionException, InterruptedException, ExecutionException {
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
