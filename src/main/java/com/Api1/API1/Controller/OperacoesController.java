package com.Api1.API1.Controller;

import com.Api1.API1.Dto.OperacoesDto;
import com.Api1.API1.Interface.impl.TaxaImpl;
import com.Api1.API1.Kafka.KafkaProducerSaque;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.OperacoesModel;
import com.Api1.API1.Model.TipoOperacaoEnum;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.OperacoesRepository;
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
@RequestMapping (path = "/api/contas")
public class OperacoesController extends TaxaImpl {
@Autowired
OperacoesRepository repository;

    @Autowired
    ContaRepository Crepository;

    @Autowired
    private OperacoesRepository operacoesRepository;
    public OperacoesController (OperacoesRepository operacoesRepository) {
        this.operacoesRepository = operacoesRepository;
    }

    @GetMapping("/extrato/{cpf}")
    public List<OperacoesModel> ConsultaExtrato(@PathVariable("cpf") String cpf) {
        return repository.findAllByCpf(cpf);
    }

    @PostMapping("/saque")
    public ResponseEntity<OperacoesModel> salvarTransacaoSaque( @RequestBody @Valid OperacoesModel model, UriComponentsBuilder uriBuilder) throws ExecutionException, InterruptedException, ExecutionException {
        if(model.getTipoOperacao().equals(TipoOperacaoEnum.Saque)) {
            sacarConta(model);
            URI uri = uriBuilder.path("/contas/{id}").buildAndExpand(model.getId()).toUri();
            //publicar saque no topico
            //metodo para registrar que o cliente efetuou o saque
            KafkaProducerSaque ks = new KafkaProducerSaque();
            ks.EnviarDadosClienteSaque(model.getCpf());
            return ResponseEntity.created(uri).body(model);
        }
        return ResponseEntity.badRequest().body(model);
    }

    @PostMapping("/deposito")
    public ResponseEntity<OperacoesModel> salvarTransacaoDeposito( @RequestBody @Valid OperacoesModel model, UriComponentsBuilder uriBuilder) {
        if(model.getTipoOperacao().equals(TipoOperacaoEnum.Deposito)) {
            repository.save(model);
            depositarConta(model);
            URI uri = uriBuilder.path("/contas/{id}").buildAndExpand(model.getId()).toUri();
            return ResponseEntity.created(uri).body(model);
        }
        return ResponseEntity.badRequest().body(model);
    }
    @PutMapping("/operacoes/transferir")
    public ResponseEntity<OperacoesModel> transferencias(@RequestBody @Valid OperacoesDto model, UriComponentsBuilder uriBuilder){
        OperacoesModel transacaoEntrada = new OperacoesModel(model.getId(), model.getCpfEntrada(), model.getValor(), TipoOperacaoEnum.TransferenciaEntrada, LocalDateTime.now());
        OperacoesModel transacaoSaida = new OperacoesModel(model.getId(),model.getCpfSaida(), model.getValor(),TipoOperacaoEnum.TransferenciaSaida,LocalDateTime.now());
        transferirContas(transacaoEntrada);
        transferirContasSaida(transacaoSaida);
        repository.save(transacaoEntrada);
        repository.save(transacaoSaida);
        URI uri = uriBuilder.path("/contas/{id}").buildAndExpand(transacaoSaida.getId()).toUri();
        return ResponseEntity.created(uri).body(transacaoSaida);
    }
    public ResponseEntity<OperacoesModel> validacaodesaque(@RequestBody @Valid OperacoesModel model, UriComponentsBuilder uriBuilder) throws ExecutionException, InterruptedException {
        Optional<ContaModel> busca = Crepository.findByUsuarioCpf(model.getCpf());
        if (busca.get().getSaldo() <= model.getValor()) {
            return ResponseEntity.badRequest().build();
        }
        sacarConta(model);
        URI uri = uriBuilder.path("/contas/{id}").buildAndExpand(model.getId()).toUri();
        KafkaProducerSaque ks = new KafkaProducerSaque();
        ks.EnviarDadosClienteSaque(model.getCpf());
        return ResponseEntity.created(uri).body(model);
    }
}
