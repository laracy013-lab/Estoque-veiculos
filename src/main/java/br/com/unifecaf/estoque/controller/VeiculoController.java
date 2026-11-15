package br.com.unifecaf.estoque.controller;

import br.com.unifecaf.estoque.dto.AtualizacaoVeiculoRequest;
import br.com.unifecaf.estoque.dto.VeiculoRequest;
import br.com.unifecaf.estoque.dto.VeiculoResponse;
import br.com.unifecaf.estoque.model.StatusVeiculo;
import br.com.unifecaf.estoque.model.TipoVeiculo;
import br.com.unifecaf.estoque.service.VeiculoService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Camada de controle (o "C" do MVC): recebe as requisições HTTP em /api/veiculos,
 * delega o trabalho ao Service e devolve a resposta em JSON.
 */
@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    // READ: lista todos ou filtra, ex.: /api/veiculos?marca=toyota&precoMax=100000&status=DISPONIVEL
    @GetMapping
    public List<VeiculoResponse> listar(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) TipoVeiculo tipo,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) StatusVeiculo status) {
        return service.filtrar(marca, modelo, tipo, precoMin, precoMax, ano, status);
    }

    @GetMapping("/{id}")
    public VeiculoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoResponse criar(@Valid @RequestBody VeiculoRequest request) {
        return service.criar(request);
    }

    // UPDATE (completo)
    @PutMapping("/{id}")
    public VeiculoResponse atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoRequest request) {
        return service.atualizar(id, request);
    }

    // UPDATE (parcial): preço, quilometragem e/ou status
    @PatchMapping("/{id}")
    public VeiculoResponse atualizarParcial(@PathVariable Long id,
                                            @Valid @RequestBody AtualizacaoVeiculoRequest request) {
        return service.atualizarParcial(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
