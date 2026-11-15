package br.com.unifecaf.estoque.service;

import br.com.unifecaf.estoque.dto.ModeloRequest;
import br.com.unifecaf.estoque.dto.ModeloResponse;
import br.com.unifecaf.estoque.exception.RecursoNaoEncontradoException;
import br.com.unifecaf.estoque.exception.RegraNegocioException;
import br.com.unifecaf.estoque.model.Marca;
import br.com.unifecaf.estoque.model.Modelo;
import br.com.unifecaf.estoque.repository.MarcaRepository;
import br.com.unifecaf.estoque.repository.ModeloRepository;
import br.com.unifecaf.estoque.repository.VeiculoRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Regras de negócio dos modelos (cada modelo pertence a uma marca). */
@Service
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final MarcaRepository marcaRepository;
    private final VeiculoRepository veiculoRepository;

    public ModeloService(ModeloRepository modeloRepository, MarcaRepository marcaRepository,
                         VeiculoRepository veiculoRepository) {
        this.modeloRepository = modeloRepository;
        this.marcaRepository = marcaRepository;
        this.veiculoRepository = veiculoRepository;
    }

    @Transactional(readOnly = true)
    public List<ModeloResponse> listar(Long marcaId) {
        List<Modelo> modelos = (marcaId == null)
                ? modeloRepository.findAll(Sort.by("nome"))
                : modeloRepository.findByMarcaIdOrderByNomeAsc(marcaId);
        return modelos.stream().map(this::paraResponse).toList();
    }

    @Transactional(readOnly = true)
    public ModeloResponse buscar(Long id) {
        return paraResponse(buscarEntidade(id));
    }

    @Transactional
    public ModeloResponse criar(ModeloRequest request) {
        Marca marca = buscarMarca(request.marcaId());
        Modelo modelo = new Modelo(request.nome().trim(), request.tipo(), marca);
        return paraResponse(modeloRepository.save(modelo));
    }

    @Transactional
    public ModeloResponse atualizar(Long id, ModeloRequest request) {
        Modelo modelo = buscarEntidade(id);
        modelo.setNome(request.nome().trim());
        modelo.setTipo(request.tipo());
        modelo.setMarca(buscarMarca(request.marcaId()));
        return paraResponse(modeloRepository.save(modelo));
    }

    @Transactional
    public void remover(Long id) {
        Modelo modelo = buscarEntidade(id);
        if (veiculoRepository.existsByModeloId(id)) {
            throw new RegraNegocioException("Não é possível remover o modelo: existem veículos cadastrados para ele");
        }
        modeloRepository.delete(modelo);
    }

    private Modelo buscarEntidade(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo não encontrado (id " + id + ")"));
    }

    private Marca buscarMarca(Long marcaId) {
        return marcaRepository.findById(marcaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Marca não encontrada (id " + marcaId + ")"));
    }

    private ModeloResponse paraResponse(Modelo modelo) {
        return new ModeloResponse(modelo.getId(), modelo.getNome(), modelo.getTipo(),
                modelo.getMarca().getId(), modelo.getMarca().getNome());
    }
}
