package br.com.comprebem.dto;

import br.com.comprebem.entity.PedidoStatus;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusPedidoRequest(@NotNull PedidoStatus status) {
}
