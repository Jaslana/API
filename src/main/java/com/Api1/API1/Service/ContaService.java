package com.Api1.API1.Service;


import com.Api1.API1.Dto.RequestDTO.ContaRequestDTO;
import com.Api1.API1.Dto.ResponseDTO.*;
import com.Api1.API1.Exception.ContaJaCadastradaException;
import com.Api1.API1.Exception.ContaNaoEncontradaException;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;


    public ContaResponseDTO salvarConta(ContaRequestDTO contaRequestDTO) {
        ContaModel model = modelMapper.map(contaRequestDTO, ContaModel.class);
        ContaResponseDTO contaResponse = modelMapper.map(model, ContaResponseDTO.class);

        ContaModel contaExistente = contaRepository.getByNumConta(model.getNumConta());

        if (!Objects.isNull(contaExistente)) {
            throw new ContaJaCadastradaException("Numero de conta: " + model.getNumConta() +
                    " ja cadastrada no sistema");
        }

        model.setUserCpf(contaRequestDTO.getUserCpf());
        contaRepository.save(model);
        return contaResponse;
    }

    public ContaUsuarioResponse consutarNConta(String numConta) {
        ContaModel model = contaRepository.findByNumConta(numConta).orElseThrow(() ->
                new ContaNaoEncontradaException("Conta nao encontrado" + numConta));
        UsuarioModel model1 = usuarioRepository.getByCpf(model.getUserCpf());
        return ContaUsuarioResponse
                .builder()
                .conta( modelMapper.map(model, ContaResponseDTO.class))
                .usuario(modelMapper.map(model1, UsuarioResponseDTO.class))
                .build();
    }

    public List<ContaResponseDTO> consultarTodos() {
        return contaRepository.findAll()
                .stream()
                .map(this::toContaResponseDTO)
                .collect(Collectors.toList());

        //retornar que nao existe nenhuma conta
    }

    public ContaResponseDTO atualizarConta(String numConta, ContaRequestDTO contaRequestDTO) {
        contaRequestDTO.setNumConta(numConta);
        ContaModel model = modelMapper.map(contaRequestDTO, ContaModel.class);

        contaRepository.findByNumConta(model.getNumConta()).map(map -> {
            map.setNumConta((numConta));
            map.setAgencia(model.getAgencia());
            map.setDverif(model.getDverif());
            map.setTipo(model.getTipo());
            ContaModel updated = contaRepository.save(map);
            return updated;
        });
        ContaResponseDTO contaResponse = modelMapper.map(model, ContaResponseDTO.class);

        return contaResponse;
    }

    public ContaDeleteDTO deletarConta(String numConta) {

        ContaModel model = contaRepository.findByNumConta(numConta).orElseThrow(() ->
                new ContaNaoEncontradaException("Conta nao encontrado com o numero de conta : " + numConta));

        contaRepository.delete(model);
        ContaResponseDTO contaResponseDTO = modelMapper.map(model, ContaResponseDTO.class);
        return ContaDeleteDTO.builder()
                .mensagem("Conta Deletada com sucesso!")
                .contaDeletada(contaResponseDTO).
                build();
    }

    public ContaResponseDTO toContaResponseDTO(ContaModel contaModel) {

        var contaResponseDTO = new ContaResponseDTO();
        contaResponseDTO.setNumConta(contaModel.getNumConta());
        contaResponseDTO.setAgencia(contaModel.getAgencia());
        contaResponseDTO.setSaldo(contaModel.getSaldo());
        contaResponseDTO.setTipo(contaModel.getTipo());
        contaResponseDTO.setDverif(contaModel.getDverif());
        return contaResponseDTO;
    }

}






