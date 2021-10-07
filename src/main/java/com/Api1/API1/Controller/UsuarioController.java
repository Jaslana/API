package com.Api1.API1.Controller;

import com.Api1.API1.Dto.RequestDTO.UsuarioRequestDTO;
import com.Api1.API1.Dto.ResponseDTO.UsuarioContasResponse;
import com.Api1.API1.Dto.ResponseDTO.UsuarioResponseDTO;
import com.Api1.API1.Dto.ResponseDTO.UsuarioResponseDTODelete;
import com.Api1.API1.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    @PostMapping(path = "/api/usuarios/salvar")
    @Transactional
    public UsuarioResponseDTO salvarUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.salvarUsuario(usuarioRequestDTO);

    }

    @GetMapping(path = "/api/usuarios")
    public UsuarioContasResponse consultarCpf(@RequestParam String cpf) {
        return usuarioService.consultarCpf(cpf);

    }

    @GetMapping(path = "/api/usuarios/consultar")
    public List<UsuarioResponseDTO> consultarTodos() {
        return usuarioService.consultarTodos();
    }

    @PutMapping("/api/usuarios/alterar")
    public UsuarioResponseDTO atualizarUsuario(@RequestParam String cpf, @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.atualizarUsuario(cpf, usuarioRequestDTO);
    }

    @DeleteMapping(value = "api/usuarios/delete")
    public UsuarioResponseDTODelete delete(@RequestParam String cpf){
        return usuarioService.delete(cpf);
    }
}
