package br.com.comprebem.controller;

import br.com.comprebem.dto.ProdutoRequest;
import br.com.comprebem.dto.ProdutoResposta;
import br.com.comprebem.entity.Produto;
import br.com.comprebem.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public java.util.List<ProdutoResposta> listarProdutos() {
        return produtoService.listarTodos().stream().map(ProdutoResposta::de).toList();
    }

    @GetMapping("/{id}")
    public ProdutoResposta buscarProduto(@PathVariable Long id) {
        return ProdutoResposta.de(produtoService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResposta criarProduto(@Valid @RequestBody ProdutoRequest request) {
        return ProdutoResposta.de(produtoService.salvar(request.paraEntidade()));
    }

    @PutMapping("/{id}")
    public ProdutoResposta atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoRequest request) {
        return ProdutoResposta.de(produtoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirProduto(@PathVariable Long id) {
        produtoService.excluir(id);
    }
}
