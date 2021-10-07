package com.Api1.API1.Service;


import com.Api1.API1.Dto.RequestDTO.UsuarioRequestDTO;
import com.Api1.API1.Dto.ResponseDTO.ContaResponseDTO;
import com.Api1.API1.Dto.ResponseDTO.UsuarioContasResponse;
import com.Api1.API1.Dto.ResponseDTO.UsuarioResponseDTO;
import com.Api1.API1.Dto.ResponseDTO.UsuarioResponseDTODelete;
import com.Api1.API1.Exception.UsuarioJaCadastradoException;
import com.Api1.API1.Exception.UsuarioNaoEncontradoException;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ContaRepository contaRepository;
    private final ModelMapper modelMapper;

    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioModel model = modelMapper.map(usuarioRequestDTO, UsuarioModel.class);
        UsuarioResponseDTO usuarioResponse = modelMapper.map(model, UsuarioResponseDTO.class);

        UsuarioModel usuarioExiste = usuarioRepository.getByCpf(model.getCpf());

        if (!Objects.isNull(usuarioExiste)) {
            throw new UsuarioJaCadastradoException("Usuario com o CPF: " + model.getCpf()
                    + " já cadastrado no sistema");
        }

        usuarioRepository.save(model);
        return usuarioResponse;
    }

    public UsuarioContasResponse consultarCpf(String cpf) {
        UsuarioModel model = usuarioRepository.findByCpf(cpf).orElseThrow(() ->
                new UsuarioNaoEncontradoException("Usuario nao encontrado com o CPF: " + cpf));
        List<ContaModel> contas = contaRepository.findAllByUserCpf(cpf);
        List<ContaResponseDTO> contasresponse = Arrays.asList();
        contas.forEach(item ->{
            contasresponse.add(modelMapper.map(item,ContaResponseDTO.class));
        });

        return UsuarioContasResponse.builder()
                .usuario(modelMapper.map(model,UsuarioResponseDTO.class))
                .contas(contasresponse).build();
    }

    public List<UsuarioResponseDTO> consultarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toUsuarioResponseDTO)
                .collect(Collectors.toList());
    }


    public UsuarioResponseDTO atualizarUsuario(String cpf, UsuarioRequestDTO usuarioRequestDTO) {
        usuarioRequestDTO.setCpf(cpf);
        UsuarioModel model = modelMapper.map(usuarioRequestDTO, UsuarioModel.class);

        usuarioRepository.findByCpf(model.getCpf()).map(map -> {
            map.setCpf((cpf));
            map.setNome(model.getNome());
            map.setFone(model.getFone());
            map.setEndereco(model.getEndereco());
            UsuarioModel updated = usuarioRepository.save(map);
            return  updated;
        });
        UsuarioResponseDTO usuarioResponse = modelMapper.map(model, UsuarioResponseDTO.class);

        return usuarioResponse;
    }

    public UsuarioResponseDTODelete delete(String cpf) {

        UsuarioModel model = usuarioRepository.findByCpf(cpf).orElseThrow(() ->
                new UsuarioNaoEncontradoException("Usuario nao encontrado com o CPF: " + cpf));

        usuarioRepository.delete(model);
        UsuarioResponseDTO usuarioResponseDTO = modelMapper.map(model, UsuarioResponseDTO.class);
        return UsuarioResponseDTODelete.builder()
                .mensagem("Conta Deletada com sucesso!")
                .usuarioDeletado(usuarioResponseDTO).
                build();

    }

    public UsuarioResponseDTO toUsuarioResponseDTO(UsuarioModel usuarioModel) {

        var usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setCpf(usuarioModel.getCpf());
        usuarioResponseDTO.setNome(usuarioModel.getNome());
        usuarioResponseDTO.setFone(usuarioModel.getFone());
        usuarioResponseDTO.setEndereco(usuarioModel.getEndereco());
        return usuarioResponseDTO;
    }

}




