package br.com.unifecaf.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Dados que o cliente envia para criar/atualizar uma marca. */
public record MarcaRequest(
        @NotBlank(message = "O nome da marca é obrigatório")
        @Size(max = 60, message = "O nome da marca deve ter no máximo 60 caracteres")
        String nome) {
}
