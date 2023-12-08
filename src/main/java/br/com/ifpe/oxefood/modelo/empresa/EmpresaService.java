package br.com.ifpe.oxefood.modelo.empresa;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood.modelo.acesso.UsuarioService;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Empresa> findAll() {

        return repository.findAll();
    }

    @Transactional
    public Empresa save(Empresa empresa) {

        usuarioService.save(empresa.getUsuario());

        return repository.save(empresa);
    }

}
