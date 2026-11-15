package br.com.unifecaf.estoque.dto;

import br.com.unifecaf.estoque.model.StatusVeiculo;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/** Atualização parcial (PATCH): só preço, quilometragem e/ou status. Campos nulos são ignorados. */
public record AtualizacaoVeiculoRequest(
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal preco,

        @PositiveOrZero(message = "A quilometragem não pode ser negativa")
        Integer quilometragem,

        StatusVeiculo status) {
}
