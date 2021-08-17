package com.Api1.API1.Repository;


import com.Api1.API1.Model.ContaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<ContaModel, Integer> {

    Optional<ContaModel> findByUsuarioCpf(String cpf);
    Optional<ContaModel> findBynconta(String nconta);
}
