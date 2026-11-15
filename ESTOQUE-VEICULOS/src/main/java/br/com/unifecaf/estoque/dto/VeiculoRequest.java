package br.com.unifecaf.estoque.dto;

import br.com.unifecaf.estoque.model.StatusVeiculo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/** Dados enviados pelo cliente para cadastrar/atualizar um veículo (com validações). */
public record VeiculoRequest(
        @NotNull(message = "O modelo é obrigatório")
        Long modeloId,

        @NotNull(message = "O ano de fabricação é obrigatório")
        @Min(value = 1900, message = "O ano deve ser maior ou igual a 1900")
        @Max(value = 2100, message = "O ano deve ser menor ou igual a 2100")
        Integer ano,

        @NotBlank(message = "A cor é obrigatória")
        @Size(max = 30, message = "A cor deve ter no máximo 30 caracteres")
        String cor,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "A quilometragem é obrigatória")
        @PositiveOrZero(message = "A quilometragem não pode ser negativa")
        Integer quilometragem,

        @NotNull(message = "O status é obrigatório")
        StatusVeiculo status) {
}
