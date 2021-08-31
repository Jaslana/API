package com.Api1.API1.TestControllers;

import com.Api1.API1.Controller.UsuarioController;
import com.Api1.API1.Model.ContaEnum;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UsuarioTest {
    @MockBean
    UsuarioController usuarioController;
    @MockBean
    UsuarioRepository usuarioRepository;
    @BeforeEach
    public void setUp() {
        this.usuarioController = new UsuarioController(usuarioRepository);
    }


    @Test
     void save_SalvarUsuario(){

        UsuarioModel usuarioModel  = clientePadrao();

        UsuarioModel usuarioMock = clientePadrao();
        usuarioMock.setId(1);

        Mockito.when(usuarioRepository.save(usuarioModel)).thenReturn(usuarioMock);
//           ResponseEntity<UsuarioModel> usuarioSalvo = (ResponseEntity<UsuarioModel>) this.usuarioController.salvarUsuario(usuario);
        Assertions.assertNotNull(usuarioModel);
        Assertions.assertNotNull(usuarioMock.getId());
        Assertions.assertEquals(usuarioModel.getCpf(), usuarioMock.getCpf());
    }
    private UsuarioModel clientePadrao(){
        return new UsuarioModel(1,"Lana Carriel", "46860077808", "Rua Major Mariano, Vila Ferrieira, 1512", "14981030177", ContaEnum.FISICA);
    }

}
