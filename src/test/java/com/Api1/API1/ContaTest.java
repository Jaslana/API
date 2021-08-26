package com.Api1.API1;

import com.Api1.API1.Controller.UsuarioController;
import com.Api1.API1.Model.ContaEnum;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.UsuarioRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ContaTest {
    @MockBean
    UsuarioController usuario;
    @MockBean
    UsuarioRepository repository;

    @BeforeEach
    public void setUp() {
        this.usuario = new UsuarioController(repository);
    }

//    @Test
//    @DisplayName("CREATE")
//    public void save_SalvarCliente() {
//        UsuarioModel cliente = clientePadrao();
//        UsuarioModel clienteMock = clientePadrao();
//        Mockito.when(repository.save(cliente)).thenReturn(clienteMock);
//        UriComponentsBuilder uribuilder = null;
//        URI uri = uribuilder.path("usuario/{id}").buildAndExpand(cliente.getId()).toUri();
//        ResponseEntity<UsuarioModel> clienteSalvo = usuario.salvar(clienteMock,uribuilder);
//        Assertions.assertNotNull(clienteSalvo);
//        Assertions.assertNotNull(clienteSalvo.getBody().getId());
//        Assertions.assertEquals(cliente.getCpf(), clienteSalvo.getBody().getCpf());
//    }
//
//    public UsuarioModel clientePadrao() {
//        return new UsuarioModel(1,"Jose Alves Santos", "46860077808", "Rua Niteroi 95, Centro", "(43)98787-0456", ContaEnum.FISICA);
//
//    }
}