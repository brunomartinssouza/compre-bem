package br.com.comprebem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CriarPedidoRequest(
        @NotNull @Positive Long clienteId,
        @NotEmpty List<@Valid ItemPedidoRequest> itens
) {
}
