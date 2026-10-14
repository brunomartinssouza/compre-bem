package br.com.comprebem.dto;

import br.com.comprebem.entity.ItemPedido;

import java.math.BigDecimal;

public record ItemPedidoResposta(
        Long id,
        Long produtoId,
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
    public static ItemPedidoResposta de(ItemPedido item) {
        return new ItemPedidoResposta(item.getId(), item.getProduto().getId(),
                item.getProduto().getNome(), item.getQuantidade(),
                item.getPrecoUnitario(), item.getSubtotal());
    }
}
