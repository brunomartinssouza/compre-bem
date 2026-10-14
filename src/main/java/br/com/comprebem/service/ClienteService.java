package br.com.comprebem.service;

import br.com.comprebem.dto.ClienteRequest;
import br.com.comprebem.entity.Cliente;
import br.com.comprebem.exception.ConflitoException;
import br.com.comprebem.exception.RecursoNaoEncontradoException;
import br.com.comprebem.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findByAtivoTrue();
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
    }

    @Transactional
    public Cliente criar(ClienteRequest request) {
        String email = normalizarEmail(request.email());
        if (clienteRepository.existsByEmail(email)) {
            throw new ConflitoException("Já existe um cliente cadastrado com este email");
        }
        Cliente cliente = Cliente.builder()
                .nome(request.nome())
                .email(email)
                .telefone(request.telefone())
                .ativo(request.ativo() == null || request.ativo())
                .build();
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente atualizar(Long id, ClienteRequest request) {
        Cliente cliente = buscarPorId(id);
        String email = normalizarEmail(request.email());
        if (clienteRepository.existsByEmailAndIdNot(email, id)) {
            throw new ConflitoException("Já existe um cliente cadastrado com este email");
        }
        cliente.setNome(request.nome());
        cliente.setEmail(email);
        cliente.setTelefone(request.telefone());
        if (request.ativo() != null) {
            cliente.setAtivo(request.ativo());
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void excluir(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
