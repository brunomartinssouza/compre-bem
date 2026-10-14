package br.com.comprebem.controller;

import br.com.comprebem.dto.AtualizarStatusPedidoRequest;
import br.com.comprebem.dto.CriarPedidoRequest;
import br.com.comprebem.dto.PedidoResposta;
import br.com.comprebem.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResposta criarPedido(@Valid @RequestBody CriarPedidoRequest request) {
        return pedidoService.criar(request);
    }

    @GetMapping
    public List<PedidoResposta> listarPedidos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public PedidoResposta buscarPedido(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @PutMapping("/{id}/status")
    public PedidoResposta atualizarStatus(@PathVariable Long id,
                                          @Valid @RequestBody AtualizarStatusPedidoRequest request) {
        return pedidoService.atualizarStatus(id, request.status());
    }
}
