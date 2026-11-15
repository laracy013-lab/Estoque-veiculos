package br.com.unifecaf.estoque.service;

import br.com.unifecaf.estoque.dto.MarcaRequest;
import br.com.unifecaf.estoque.dto.MarcaResponse;
import br.com.unifecaf.estoque.exception.RecursoNaoEncontradoException;
import br.com.unifecaf.estoque.exception.RegraNegocioException;
import br.com.unifecaf.estoque.model.Marca;
import br.com.unifecaf.estoque.repository.MarcaRepository;
import br.com.unifecaf.estoque.repository.ModeloRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Regras de negócio das marcas. */
@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;

    public MarcaService(MarcaRepository marcaRepository, ModeloRepository modeloRepository) {
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
    }

    @Transactional(readOnly = true)
    public List<MarcaResponse> listar() {
        return marcaRepository.findAll(Sort.by("nome")).stream().map(this::paraResponse).toList();
    }

    @Transactional(readOnly = true)
    public MarcaResponse buscar(Long id) {
        return paraResponse(buscarEntidade(id));
    }

    @Transactional
    public MarcaResponse criar(MarcaRequest request) {
        String nome = request.nome().trim();
        if (marcaRepository.existsByNomeIgnoreCase(nome)) {
            throw new RegraNegocioException("Já existe uma marca com o nome '" + nome + "'");
        }
        return paraResponse(marcaRepository.save(new Marca(nome)));
    }

    @Transactional
    public MarcaResponse atualizar(Long id, MarcaRequest request) {
        Marca marca = buscarEntidade(id);
        String nome = request.nome().trim();
        if (!marca.getNome().equalsIgnoreCase(nome) && marcaRepository.existsByNomeIgnoreCase(nome)) {
            throw new RegraNegocioException("Já existe uma marca com o nome '" + nome + "'");
        }
        marca.setNome(nome);
        return paraResponse(marcaRepository.save(marca));
    }

    @Transactional
    public void remover(Long id) {
        Marca marca = buscarEntidade(id);
        if (modeloRepository.existsByMarcaId(id)) {
            throw new RegraNegocioException("Não é possível remover a marca: existem modelos cadastrados para ela");
        }
        marcaRepository.delete(marca);
    }

    private Marca buscarEntidade(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Marca não encontrada (id " + id + ")"));
    }

    private MarcaResponse paraResponse(Marca marca) {
        return new MarcaResponse(marca.getId(), marca.getNome());
    }
}
