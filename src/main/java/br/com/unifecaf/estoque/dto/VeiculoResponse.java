package br.com.unifecaf.estoque.dto;

import br.com.unifecaf.estoque.model.StatusVeiculo;
import br.com.unifecaf.estoque.model.TipoVeiculo;
import java.math.BigDecimal;

/** Dados devolvidos ao cliente (já com o nome da marca e do modelo). */
public record VeiculoResponse(
        Long id,
        Long modeloId,
        String modelo,
        Long marcaId,
        String marca,
        TipoVeiculo tipo,
        Integer ano,
        String cor,
        BigDecimal preco,
        Integer quilometragem,
        StatusVeiculo status) {
}
