package br.com.comprebem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequest(
        @NotNull @Positive Long produtoId,
        @NotNull @Positive Integer quantidade
) {
}
