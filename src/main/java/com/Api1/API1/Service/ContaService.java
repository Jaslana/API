package com.Api1.API1.Service;


import com.Api1.API1.Dto.ContaModelDto;
import com.Api1.API1.Exception.ContaJaCadastrada;
import com.Api1.API1.Exception.ContaNaoEncontradaNconta;
import com.Api1.API1.Model.ContaModel;
import com.Api1.API1.Model.UsuarioModel;
import com.Api1.API1.Repository.ContaRepository;
import com.Api1.API1.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContaService {

    //Todos estao retornando com o campo CPF no erro

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ContaService(ContaRepository contaRepository) {
    }


    public ResponseEntity<ContaModel> salvar(ContaModel contaModel, UsuarioModel usuarioModel, String nconta) {
        ContaModel conta = contaRepository.findBynconta(contaModel.getNconta()).map(busca->{
//            UsuarioModel usuario = usuarioRepository.findById(usuarioModel.getId().)
            if(busca.getCodigo() >= 1){
                throw new ContaJaCadastrada("Conta ja existe", nconta);

            }

            return busca;
        }).orElseGet(() ->

                contaRepository.save(contaModel));
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
        //campo erro tratar a mensagem de retorno
        //Retornar o corpo json do usuario que ja existe
        //Retornar o campo usuario
        //retornar a qt de saque....posso reutilizar?:
        //contaModel.setQtdSaques(0);
        //contaModel.setUsuario(busca.get());
    }


    public ResponseEntity<ContaModel> consutarNConta(String nconta) {

        ContaModel conta = contaRepository.findBynconta(nconta).orElseThrow(() ->
                new ContaNaoEncontradaNconta("Conta nao encontrado", nconta));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(conta);
    }


    public List<ContaModel> consultarTodos() {
        return contaRepository.findAll();

        //retornar que nao existe nenhuma conta
    }

    @Transactional
    public ResponseEntity<ContaModel> atualizar(String nConta, ContaModelDto contaModelDto) {

        ContaModel conta = contaRepository.findBynconta(nConta).orElseThrow(() ->
                new ContaNaoEncontradaNconta("Conta nao encontrado", nConta));

        contaModelDto.atualizar(nConta, contaRepository);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(conta);
        //Retornar mensagem que o usuario foi atualizado com sucesso
    }

    public ResponseEntity<ContaModel> deletarConta(String nconta) {

        ContaModel conta = contaRepository.findBynconta(nconta).orElseThrow(() ->
                new ContaNaoEncontradaNconta("Conta nao encontrado", nconta));

            contaRepository.delete(conta);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(conta);
        //retornar mensagem que o usuario foi exluido com sucesso
    }
}






