package com.Api1.API1.Repository;



import com.Api1.API1.Model.OperacoesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository

public interface OperacoesRepository extends JpaRepository<OperacoesModel, Integer> {

    List<OperacoesModel> findAllByCpf(String cpf);

    Optional<OperacoesModel> findByCpf(String cpf);

    static List<OperacoesModel> findByDataBetweenAndNConta
            (LocalDateTime dataInicioMes, LocalDateTime dataFimMes, String numeroConta) {
        return null;
    }

}
