package br.com.unifecaf.estoque.service;

import br.com.unifecaf.estoque.dto.AtualizacaoVeiculoRequest;
import br.com.unifecaf.estoque.dto.VeiculoRequest;
import br.com.unifecaf.estoque.dto.VeiculoResponse;
import br.com.unifecaf.estoque.exception.RecursoNaoEncontradoException;
import br.com.unifecaf.estoque.model.Marca;
import br.com.unifecaf.estoque.model.Modelo;
import br.com.unifecaf.estoque.model.StatusVeiculo;
import br.com.unifecaf.estoque.model.TipoVeiculo;
import br.com.unifecaf.estoque.model.Veiculo;
import br.com.unifecaf.estoque.repository.ModeloRepository;
import br.com.unifecaf.estoque.repository.VeiculoRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Regras de negócio dos veículos: cadastro, consulta com filtros, atualização e remoção. */
@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ModeloRepository modeloRepository;

    public VeiculoService(VeiculoRepository veiculoRepository, ModeloRepository modeloRepository) {
        this.veiculoRepository = veiculoRepository;
        this.modeloRepository = modeloRepository;
    }

    /** Consulta com filtros opcionais. Parâmetros nulos/vazios são ignorados. */
    @Transactional(readOnly = true)
    public List<VeiculoResponse> filtrar(String marca, String modelo, TipoVeiculo tipo,
                                         BigDecimal precoMin, BigDecimal precoMax,
                                         Integer ano, StatusVeiculo status) {
        Specification<Veiculo> filtros = (root, query, cb) -> {
            List<Predicate> condicoes = new ArrayList<>();
            Join<Veiculo, Modelo> joinModelo = root.join("modelo");
            Join<Modelo, Marca> joinMarca = joinModelo.join("marca");

            if (marca != null && !marca.isBlank()) {
                condicoes.add(cb.like(cb.lower(joinMarca.get("nome")), "%" + marca.trim().toLowerCase() + "%"));
            }
            if (modelo != null && !modelo.isBlank()) {
                condicoes.add(cb.like(cb.lower(joinModelo.get("nome")), "%" + modelo.trim().toLowerCase() + "%"));
            }
            if (tipo != null) {
                condicoes.add(cb.equal(joinModelo.get("tipo"), tipo));
            }
            if (precoMin != null) {
                condicoes.add(cb.greaterThanOrEqualTo(root.get("preco"), precoMin));
            }
            if (precoMax != null) {
                condicoes.add(cb.lessThanOrEqualTo(root.get("preco"), precoMax));
            }
            if (ano != null) {
                condicoes.add(cb.equal(root.get("ano"), ano));
            }
            if (status != null) {
                condicoes.add(cb.equal(root.get("status"), status));
            }
            return cb.and(condicoes.toArray(new Predicate[0]));
        };
        return veiculoRepository.findAll(filtros, Sort.by("id")).stream().map(this::paraResponse).toList();
    }

    @Transactional(readOnly = true)
    public VeiculoResponse buscar(Long id) {
        return paraResponse(buscarEntidade(id));
    }

    @Transactional
    public VeiculoResponse criar(VeiculoRequest request) {
        Veiculo veiculo = new Veiculo(buscarModelo(request.modeloId()), request.ano(), request.cor().trim(),
                request.preco(), request.quilometragem(), request.status());
        return paraResponse(veiculoRepository.save(veiculo));
    }

    /** Atualização completa (PUT). */
    @Transactional
    public VeiculoResponse atualizar(Long id, VeiculoRequest request) {
        Veiculo veiculo = buscarEntidade(id);
        veiculo.setModelo(buscarModelo(request.modeloId()));
        veiculo.setAno(request.ano());
        veiculo.setCor(request.cor().trim());
        veiculo.setPreco(request.preco());
        veiculo.setQuilometragem(request.quilometragem());
        veiculo.setStatus(request.status());
        return paraResponse(veiculoRepository.save(veiculo));
    }

    /** Atualização parcial (PATCH): preço, quilometragem e/ou status. */
    @Transactional
    public VeiculoResponse atualizarParcial(Long id, AtualizacaoVeiculoRequest request) {
        Veiculo veiculo = buscarEntidade(id);
        if (request.preco() != null) {
            veiculo.setPreco(request.preco());
        }
        if (request.quilometragem() != null) {
            veiculo.setQuilometragem(request.quilometragem());
        }
        if (request.status() != null) {
            veiculo.setStatus(request.status());
        }
        return paraResponse(veiculoRepository.save(veiculo));
    }

    @Transactional
    public void remover(Long id) {
        veiculoRepository.delete(buscarEntidade(id));
    }

    private Veiculo buscarEntidade(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado (id " + id + ")"));
    }

    private Modelo buscarModelo(Long modeloId) {
        return modeloRepository.findById(modeloId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo não encontrado (id " + modeloId + ")"));
    }

    private VeiculoResponse paraResponse(Veiculo v) {
        Modelo modelo = v.getModelo();
        Marca marca = modelo.getMarca();
        return new VeiculoResponse(v.getId(), modelo.getId(), modelo.getNome(), marca.getId(), marca.getNome(),
                modelo.getTipo(), v.getAno(), v.getCor(), v.getPreco(), v.getQuilometragem(), v.getStatus());
    }
}
