package com.Api1.API1.Controller;

import com.Api1.API1.Dto.RequestDTO.ContaRequestDTO;
import com.Api1.API1.Dto.ResponseDTO.ContaDeleteDTO;
import com.Api1.API1.Dto.ResponseDTO.ContaResponseDTO;
import com.Api1.API1.Dto.ResponseDTO.ContaUsuarioResponse;
import com.Api1.API1.Service.ContaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContaController {

    private final ModelMapper modelMapper;
    private final ContaService contaService;

    @PostMapping(path = "/api/contas/salvar")
    public ContaResponseDTO salvarConta(@RequestBody @Valid ContaRequestDTO contaRequestDTO) {
        return contaService.salvarConta(contaRequestDTO);

    }

    @GetMapping(path = "/api/contas/consultar")
    public ContaUsuarioResponse consutarNConta(@RequestParam String numConta) {
        return contaService.consutarNConta(numConta);
    }

    @GetMapping( path = "api/contas")
    public List<ContaResponseDTO> consultarTodos() {
        return contaService.consultarTodos();
    }

    @PutMapping("/api/contas/alterar/")
    public ContaResponseDTO atualizarConta(@RequestParam String numConta, @RequestBody ContaRequestDTO contaRequestDTO) {
        return contaService.atualizarConta(numConta, contaRequestDTO);
    }
    @DeleteMapping(value = "api/contas/delete/")
    public ContaDeleteDTO deletarConta (@RequestParam String numConta) {
        return contaService.deletarConta(numConta);
    }
}
