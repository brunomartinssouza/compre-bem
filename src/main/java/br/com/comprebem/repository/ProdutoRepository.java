package br.com.comprebem.repository;

import br.com.comprebem.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    java.util.List<Produto> findByAtivoTrue();
}
