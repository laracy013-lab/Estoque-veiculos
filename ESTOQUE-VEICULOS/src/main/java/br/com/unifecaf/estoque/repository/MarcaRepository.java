package br.com.unifecaf.estoque.repository;

import br.com.unifecaf.estoque.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

/** Acesso ao banco para Marca. O Spring gera as consultas a partir dos nomes dos métodos. */
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    boolean existsByNomeIgnoreCase(String nome);
}
