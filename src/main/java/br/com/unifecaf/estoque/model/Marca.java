package br.com.unifecaf.estoque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/** Marca de veículos (ex.: Toyota, Honda). Vira a tabela "marcas". */
@Entity
@Table(name = "marcas")
public class Marca extends EntidadeBase {

    @Column(nullable = false, unique = true, length = 60)
    private String nome;

    protected Marca() {
        // construtor vazio exigido pelo JPA
    }

    public Marca(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
