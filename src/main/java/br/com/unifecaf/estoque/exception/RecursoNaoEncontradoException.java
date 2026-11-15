package br.com.unifecaf.estoque.exception;

/** Lançada quando um registro (marca, modelo, veículo) não existe. Vira HTTP 404. */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
