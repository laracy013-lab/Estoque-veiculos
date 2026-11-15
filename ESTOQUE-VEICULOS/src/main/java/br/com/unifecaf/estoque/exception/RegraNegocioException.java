package br.com.unifecaf.estoque.exception;

/** Lançada quando uma regra de negócio é violada (ex.: marca duplicada). Vira HTTP 409. */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
