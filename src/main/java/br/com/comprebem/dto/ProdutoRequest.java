package br.com.comprebem.dto;

import br.com.comprebem.entity.Produto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank @Size(max = 150) String nome,
        @Size(max = 500) String descricao,
        @NotNull @DecimalMin(value = "0.00", inclusive = false) @Digits(integer = 8, fraction = 2) BigDecimal preco,
        @Size(max = 100) String categoria,
        Boolean ativo
) {
    public Produto paraEntidade() {
        return Produto.builder()
                .nome(nome)
                .descricao(descricao)
                .preco(preco)
                .categoria(categoria)
                .ativo(ativo == null || ativo)
                .build();
    }
}
