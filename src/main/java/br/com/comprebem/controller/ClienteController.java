package br.com.comprebem.controller;

import br.com.comprebem.dto.ClienteRequest;
import br.com.comprebem.dto.ClienteResposta;
import br.com.comprebem.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteResposta> listarClientes() {
        return clienteService.listarTodos().stream().map(ClienteResposta::de).toList();
    }

    @GetMapping("/{id}")
    public ClienteResposta buscarCliente(@PathVariable Long id) {
        return ClienteResposta.de(clienteService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResposta criarCliente(@Valid @RequestBody ClienteRequest request) {
        return ClienteResposta.de(clienteService.criar(request));
    }

    @PutMapping("/{id}")
    public ClienteResposta atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        return ClienteResposta.de(clienteService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirCliente(@PathVariable Long id) {
        clienteService.excluir(id);
    }
}
