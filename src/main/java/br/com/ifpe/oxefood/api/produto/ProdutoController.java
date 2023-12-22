package br.com.ifpe.oxefood.api.produto;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.produto.CategoriaProdutoService;
import br.com.ifpe.oxefood.modelo.produto.Produto;
import br.com.ifpe.oxefood.modelo.produto.ProdutoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    @ApiOperation(value = "Serviço responsável por salvar um produto no sistema.")
    @PostMapping
    public ResponseEntity<Produto> save(@RequestBody @Valid ProdutoRequest request) {

// Cria um novo produto a partir do ProdutoRequest e o salva no serviço de produto
        Produto produtoNovo = produtoService.save(request.build());
          // Obtém a categoria do produto a partir do serviço de categoria
        produtoNovo.setCategoria(categoriaProdutoService.findById(request.getIdCategoria()));
          // Salva novamente o produto, agora com a categoria associada
        Produto produto = produtoService.save(produtoNovo);
         // Retorna uma resposta indicando sucesso (código 201 - Created) e o produto salvo
        return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Serviço responsável por listar um produto no sistema.")
    @GetMapping
    public List<Produto> findAll() {
    // Obtém e retorna todos os produtos do serviço de produto
        return produtoService.findAll();
    }

    @ApiOperation(value = "Serviço responsável por obter um produto referente ao Id passado na URL.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna  o produto."),
            @ApiResponse(code = 401, message = "Acesso não autorizado."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(code = 404, message = "Não foi encontrado um registro para o Id informado."),
            @ApiResponse(code = 500, message = "Foi gerado um erro no servidor."),
    })
    @GetMapping("/{id}")
    public Produto findById(@PathVariable Long id) {
// Obtém e retorna o produto com o ID fornecido do serviço de produto
        return produtoService.findById(id);
    }

    // Alterar
    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable("id") Long id, @RequestBody ProdutoRequest request) {
  // Cria um objeto Produto a partir do ProdutoRequest
        Produto produto = request.build();
        // Associa a categoria ao produto
        produto.setCategoria(categoriaProdutoService.findById(request.getIdCategoria()));
                  // Atualiza o produto com o ID fornecido

        produtoService.update(id, produto);
// Retorna uma resposta indicando sucesso (código 200 - OK)
        return ResponseEntity.ok().build();
    }

    // Desabilitar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
// Deleta o produto com o ID fornecido
        produtoService.delete(id);
         // Retorna uma resposta indicando sucesso (código 200 - OK)
        return ResponseEntity.ok().build();

    }

    @PostMapping("/filtrar")
    public List<Produto> filtrar(
            @RequestParam(value = "codigo", required = false) String codigo,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "idCategoria", required = false) Long idCategoria) {
 // Filtra os produtos com base nos parâmetros fornecidos e retorna a lista resultante
        return produtoService.filtrar(codigo, titulo, idCategoria);
    }

}
