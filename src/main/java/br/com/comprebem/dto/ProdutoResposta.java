package br.com.comprebem.dto;

import br.com.comprebem.entity.Produto;

import java.math.BigDecimal;

public record ProdutoResposta(Long id, String nome, String descricao, BigDecimal preco, String categoria, Boolean ativo) {
    public static ProdutoResposta de(Produto produto) {
        return new ProdutoResposta(produto.getId(), produto.getNome(), produto.getDescricao(),
                produto.getPreco(), produto.getCategoria(), produto.getAtivo());
    }
}
