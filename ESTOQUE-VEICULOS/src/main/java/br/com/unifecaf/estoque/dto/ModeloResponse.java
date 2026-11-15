package br.com.unifecaf.estoque.dto;

import br.com.unifecaf.estoque.model.TipoVeiculo;

public record ModeloResponse(Long id, String nome, TipoVeiculo tipo, Long marcaId, String marca) {
}
