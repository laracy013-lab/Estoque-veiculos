package br.com.unifecaf.estoque.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Classe-mãe de todas as entidades (HERANÇA).
 * Guarda o que é comum a todas: o identificador (id).
 * Marca, Modelo e Veiculo herdam daqui com "extends".
 */
@MappedSuperclass
public abstract class EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // privado: ENCAPSULAMENTO (só acessível pelo getter)

    public Long getId() {
        return id;
    }
}
