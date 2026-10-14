package br.com.comprebem.dto;

import br.com.comprebem.entity.Cliente;

public record ClienteResposta(Long id, String nome, String email, String telefone, Boolean ativo) {
    public static ClienteResposta de(Cliente cliente) {
        return new ClienteResposta(cliente.getId(), cliente.getNome(), cliente.getEmail(),
                cliente.getTelefone(), cliente.getAtivo());
    }
}
