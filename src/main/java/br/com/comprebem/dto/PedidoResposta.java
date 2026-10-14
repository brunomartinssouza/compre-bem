package br.com.comprebem.dto;

import br.com.comprebem.entity.Pedido;
import br.com.comprebem.entity.PedidoStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResposta(
        Long id,
        Long clienteId,
        String nomeCliente,
        LocalDateTime dataCriacao,
        PedidoStatus status,
        BigDecimal valorTotal,
        List<ItemPedidoResposta> itens
) {
    public static PedidoResposta de(Pedido pedido) {
        return new PedidoResposta(pedido.getId(), pedido.getCliente().getId(),
                pedido.getCliente().getNome(), pedido.getDataCriacao(), pedido.getStatus(),
                pedido.getValorTotal(), pedido.getItens().stream().map(ItemPedidoResposta::de).toList());
    }
}
