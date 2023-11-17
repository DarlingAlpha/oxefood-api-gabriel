package br.com.ifpe.oxefood.modelo.cliente;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoClienteService {
    @Autowired
    private EnderecoClienteRepository repository;

    @Transactional
    public EnderecoCliente save(EnderecoCliente enderecoCliente) {

        enderecoCliente.setHabilitado(Boolean.TRUE);
        enderecoCliente.setVersao(1L);
        enderecoCliente.setDataCriacao(LocalDate.now());
        return repository.save(enderecoCliente);
    }

    public List<EnderecoCliente> findAll() {

        return repository.findAll();
    }

    public EnderecoCliente findById(Long id) {

        return repository.findById(id).get();
    }

    /* Transactional para alteração do banco */
    @Transactional
    public void update(Long id, EnderecoCliente EnderecoClienteAlterado) {

        EnderecoCliente enderecoCliente = repository.findById(id).get();
        enderecoCliente.setDescricao(EnderecoClienteAlterado.getDescricao());
        /* altera a verção começa de um e vai em diante */
        enderecoCliente.setVersao(enderecoCliente.getVersao() + 1);
        repository.save(enderecoCliente);
    }

    @Transactional
    public void delete(Long id) {

        EnderecoCliente enderecoCliente = repository.findById(id).get();
        enderecoCliente.setHabilitado(Boolean.FALSE);
        enderecoCliente.setVersao(enderecoCliente.getVersao() + 1);

        repository.save(enderecoCliente);
    }
}
