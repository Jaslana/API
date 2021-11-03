package com.Api1.API1.Service;

import com.Api1.API1.Dto.RequestDTO.OperacoesRequestDTO;
import com.Api1.API1.Dto.ResponseDTO.OperacoesResponseDTO;
import com.Api1.API1.Exception.ContaNaoEncontradaException;
import com.Api1.API1.Interface.impl.TaxaImpl;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.OperacoesModel;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.OperacoesRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/contas")
public class OperacoesService extends TaxaImpl {

    private final ContaRepository contaRepository;
    private final OperacoesRepository operacoesRepository;
    private final ModelMapper modelMapper;

    public OperacoesResponseDTO salvarTransacaoDeposito(OperacoesRequestDTO operacoesRequestDTO) {
        OperacoesModel model = modelMapper.map(operacoesRequestDTO, OperacoesModel.class);
        OperacoesResponseDTO operacoesResponse = modelMapper.map(model, OperacoesResponseDTO.class);

        OperacoesModel operacoesModel = operacoesRepository.findByNumeroConta(model.getNumeroConta()).orElseThrow(() ->
                new ContaNaoEncontradaException("Conta nao cadastrada no sistema"));
        operacoesRepository.save(operacoesModel);
        depositarConta(operacoesModel);

        return operacoesResponse;
    }

//    public OperacoesResponseDTO transferencias(OperacoesRequestDTO operacoesRequestDTO) {
//        OperacoesModel model = modelMapper.map(operacoesRequestDTO, OperacoesModel.class);
//        OperacoesResponseDTO operacoesResponse = modelMapper.map(model, OperacoesResponseDTO.class);
//
//        ContaModel conta1 = contaRepository.findByNumConta(operacoesRequestDTO.getNumeroContaEntrada()).map(record -> {
//           ContaModel conta2 = contaRepository.findByNumConta(operacoesRequestDTO.getNumeroContaSaida()).map(busca -> {
//                    OperacoesModel transacaoEntrada = new OperacoesModel(operacoesRequestDTO.getId(), 0, operacoesRequestDTO.getNumeroContaEntrada(), operacoesRequestDTO.getValor(), TipoOperacaoEnum.TransferenciaEntrada, LocalDateTime.now());
//                    OperacoesModel transacaoSaida = new OperacoesModel(operacoesRequestDTO.getId(), 0, operacoesRequestDTO.getNumeroContaSaida(), operacoesRequestDTO.getValor(), TipoOperacaoEnum.TransferenciaSaida, LocalDateTime.now());
//                    transferirContas(transacaoEntrada);
//                    transferirContasSaida(transacaoSaida);
//                    operacoesRepository.save(transacaoEntrada);
//                    operacoesRepository.save(transacaoSaida);
//                    return ResponseEntity.status(HttpStatus.CREATED).body(transacaoSaida);
//                });
//            return conta2;
//        });
//        if (conta1.isPresent()){
//            new ExceptionDefault("As isso não existe");
//        }
//    return conta1;
//    //responder com oj diferente
//        //Revisar esse metodo
//    }

    public ResponseEntity<OperacoesResponseDTO> buscarExtrato(OperacoesRequestDTO operacoesRequestDTO) {

        OperacoesModel reqDtoModel = new OperacoesModel();
        reqDtoModel.setNumeroConta(operacoesRequestDTO.getNumeroConta());
        reqDtoModel.setValor(operacoesRequestDTO.getValor());
        reqDtoModel.setTaxa(operacoesRequestDTO.getTaxa());
        reqDtoModel.setTipoOperacao(operacoesRequestDTO.getTipoOperacao());

        OperacoesModel operacoesModel = operacoesRepository.findByNumeroConta(reqDtoModel.getNumeroConta()).orElseThrow(() ->
                new ContaNaoEncontradaException("Conta nao cadastrada no sistema" ));

        OperacoesResponseDTO resDtoModel = new OperacoesResponseDTO();
        resDtoModel.setNumeroConta(operacoesModel.getNumeroConta());
        resDtoModel.setValor(operacoesModel.getValor());
        resDtoModel.setTaxa(operacoesModel.getTaxa());
        resDtoModel.setTipoOperacao(operacoesModel.getTipoOperacao());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resDtoModel);
    }


//
//
//    public ResponseEntity<OperacoesResponseDTO> salvarTransacaoSaque(OperacoesRequestDTO)
//            throws  InterruptedException, ExecutionException {
//        Optional<ContaModel> busca = contaRepository.findBynconta(model.getNumeroConta());
//        if (busca.isPresent()) {
//            if (model.getTipoOperacao().equals(TipoOperacaoEnum.Saque)) {
//                return validacaodesaque(model, uriBuilder);
//            }
//        }
//        JSONObject json = new JSONObject();
//        json.put("Campo" , model.getNumeroConta());
//        json.put("Mensagem:", "O numero de conta nao existe");
//        return ResponseEntity.badRequest().body(json);
//        //Revisar esse metodo
//    }
//
//    public int teste(Integer qtdsaques){
//        switch (qtdsaques) {
//            case 0:
//               return 5 ;
//            case 1:
//                return 4 ;
//            case 2:
//                return 3 ;
//            case 3:
//                return 2 ;
//            case 4:
//                return 1 ;
//            default:  return 0 ;
//        }
//
//    }
//
//    public ResponseEntity<?> validacaodesaque(@RequestBody @Valid OperacoesModel model,
//                                                           UriComponentsBuilder uriBuilder)
//            throws ExecutionException, InterruptedException {
//        Optional<ContaModel> busca = contaRepository.findBynconta(model.getNumeroConta());
//        if (busca.get().getSaldo() <= model.getValor()) {
//            return ResponseEntity.badRequest().build();
//        }
//        sacarConta(model);
//        URI uri = uriBuilder.path("/contas/{id}").buildAndExpand(model.getId()).toUri();
//        JSONObject json = new JSONObject();
//        json.put("Salvo",model);
//        json.put("Message","Você possui " + teste(busca.get().getQtdSaques()) +
//                " saques gratuitos, após zerar saques gratuitos será cobrada uma taxa " +
//                "adicional de: "+ busca.get().getTipo().getTaxa());
//        KafkaProducerSaque ks = new KafkaProducerSaque();
//        ks.EnviarDadosClienteSaque(model.getNumeroConta());
//        return ResponseEntity.created(uri).body(json);
//    //revisar esse metodo
//    }
}
