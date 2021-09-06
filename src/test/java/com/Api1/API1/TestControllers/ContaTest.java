package com.Api1.API1.TestControllers;

import com.Api1.API1.Service.ContaService;
import com.Api1.API1.Model.ContaEnum;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.ContaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest

public class ContaTest {

        @MockBean
        ContaService contaController;
        @MockBean
        ContaRepository contaRepository;

        @BeforeEach
        public void setUp() {
            this.contaController = new ContaService(contaRepository);
        }


        @Test
        void save_SalvarConta() {

            ContaModel contaModel = contaPadrao();

            ContaModel contaMock = contaPadrao();
            contaMock.setCodigo(1);

            Mockito.when(contaRepository.save(contaModel)).thenReturn(contaMock);
            Assertions.assertNotNull(contaModel);
            Assertions.assertNotNull(contaMock.getCodigo());
            Assertions.assertEquals(contaMock.getNconta(), contaMock.getNconta());
        }

        private ContaModel contaPadrao() {
            UsuarioModel usuarioModel =  new UsuarioModel(1, "Lana Carriel", "46860077808",
                    "Rua Major Mariano, Vila Ferrieira, 1512", "14981030177", ContaEnum.FISICA);

            return new ContaModel(1, usuarioModel, "89756983", "5765", 0,
                    1200.0, 18, ContaEnum.FISICA);
        }
}