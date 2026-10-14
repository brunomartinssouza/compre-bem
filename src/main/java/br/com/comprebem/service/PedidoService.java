package br.com.comprebem.service;

import br.com.comprebem.dto.CriarPedidoRequest;
import br.com.comprebem.dto.ItemPedidoRequest;
import br.com.comprebem.dto.PedidoResposta;
import br.com.comprebem.entity.*;
import br.com.comprebem.exception.RecursoNaoEncontradoException;
import br.com.comprebem.exception.RegraNegocioException;
import br.com.comprebem.repository.ClienteRepository;
import br.com.comprebem.repository.PedidoRepository;
import br.com.comprebem.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
                        ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public PedidoResposta criar(CriarPedidoRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
        if (!Boolean.TRUE.equals(cliente.getAtivo())) {
            throw new RegraNegocioException("Não é possível criar pedido para um cliente inativo");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(PedidoStatus.CRIADO);
        pedido.setValorTotal(BigDecimal.ZERO);

        for (ItemPedidoRequest itemRequest : request.itens()) {
            Produto produto = produtoRepository.findById(itemRequest.produtoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Produto não encontrado: " + itemRequest.produtoId()));
            if (!Boolean.TRUE.equals(produto.getAtivo())) {
                throw new RegraNegocioException("Produto inativo não pode ser adicionado ao pedido: " + produto.getId());
            }
            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(itemRequest.quantidade()));
            pedido.adicionarItem(ItemPedido.builder()
                    .produto(produto)
                    .quantidade(itemRequest.quantidade())
                    .precoUnitario(produto.getPreco())
                    .subtotal(subtotal)
                    .build());
        }
        pedido.recalcularValorTotal();
        return PedidoResposta.de(pedidoRepository.save(pedido));
    }

    @Transactional(readOnly = true)
    public List<PedidoResposta> listarTodos() {
        return pedidoRepository.findAll().stream().map(PedidoResposta::de).toList();
    }

    @Transactional(readOnly = true)
    public PedidoResposta buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
        return PedidoResposta.de(pedido);
    }

    @Transactional
    public PedidoResposta atualizarStatus(Long id, PedidoStatus novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
        PedidoStatus atual = pedido.getStatus();
        if (atual == novoStatus) {
            return PedidoResposta.de(pedido);
        }

        boolean permitido = switch (atual) {
            case CRIADO -> novoStatus == PedidoStatus.PAGO || novoStatus == PedidoStatus.CANCELADO;
            case PAGO -> novoStatus == PedidoStatus.ENVIADO || novoStatus == PedidoStatus.CANCELADO;
            case ENVIADO -> novoStatus == PedidoStatus.ENTREGUE;
            case ENTREGUE, CANCELADO -> false;
        };
        if (!permitido) {
            throw new RegraNegocioException("Transição de status não permitida: " + atual + " -> " + novoStatus);
        }
        pedido.setStatus(novoStatus);
        return PedidoResposta.de(pedidoRepository.save(pedido));
    }
}
