package br.com.unifecaf.estoque.exception;

import java.time.LocalDateTime;
import java.util.Map;

/** Formato padrão das respostas de erro da API. */
public record ErroResposta(int status, String mensagem, Map<String, String> campos, LocalDateTime momento) {
}
