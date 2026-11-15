package br.com.unifecaf.estoque.repository;

import br.com.unifecaf.estoque.model.Modelo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    boolean existsByMarcaId(Long marcaId);

    List<Modelo> findByMarcaIdOrderByNomeAsc(Long marcaId);
}
