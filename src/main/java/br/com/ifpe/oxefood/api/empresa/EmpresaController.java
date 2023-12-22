package br.com.ifpe.oxefood.api.empresa;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import br.com.ifpe.oxefood.modelo.empresa.Empresa;
import br.com.ifpe.oxefood.modelo.empresa.EmpresaService;

@RestController
@RequestMapping("/api/empresa")
@CrossOrigin
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public List<Empresa> findAll() {

        return empresaService.findAll();
    }

    @PostMapping
    public ResponseEntity<Empresa> save(@RequestBody @Valid EmpresaRequest request) {

        Empresa empresa = request.buildEmpresa();
// Verifica se o perfil não é nulo e não é uma string vazia
        if (request.getPerfil() != null && !"".equals(request.getPerfil())) {
             // Verifica o valor do perfil
            if (request.getPerfil().equals("Usuario")) {
                 // Se o perfil for "Usuario", adiciona o papel ROLE_EMPRESA_USER ao usuário associado à empresa
                empresa.getUsuario().getRoles().add(Usuario.ROLE_EMPRESA_USER);
            } else if (request.getPerfil().equals("Admin")) {
                 // Se o perfil for "Admin", adiciona o papel ROLE_EMPRESA ao usuário associado à empresa
                empresa.getUsuario().getRoles().add(Usuario.ROLE_EMPRESA);
            }
        }

        Empresa empresaCriada = empresaService.save(empresa);
        return new ResponseEntity<Empresa>(empresaCriada, HttpStatus.CREATED);
    }

}
