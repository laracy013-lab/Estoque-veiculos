package br.com.unifecaf.estoque.config;

import br.com.unifecaf.estoque.model.Marca;
import br.com.unifecaf.estoque.model.Modelo;
import br.com.unifecaf.estoque.model.StatusVeiculo;
import br.com.unifecaf.estoque.model.TipoVeiculo;
import br.com.unifecaf.estoque.model.Veiculo;
import br.com.unifecaf.estoque.repository.MarcaRepository;
import br.com.unifecaf.estoque.repository.ModeloRepository;
import br.com.unifecaf.estoque.repository.VeiculoRepository;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Insere alguns dados de exemplo na primeira execução (só se o banco estiver vazio),
 * para o sistema já abrir com veículos para consultar e filtrar.
 */
@Component
public class CarregadorDeDados implements CommandLineRunner {

    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;
    private final VeiculoRepository veiculoRepository;

    public CarregadorDeDados(MarcaRepository marcaRepository, ModeloRepository modeloRepository,
                             VeiculoRepository veiculoRepository) {
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
        this.veiculoRepository = veiculoRepository;
    }

    @Override
    public void run(String... args) {
        if (marcaRepository.count() > 0) {
            return;
        }

        Marca toyota = marcaRepository.save(new Marca("Toyota"));
        Marca honda = marcaRepository.save(new Marca("Honda"));
        Marca volkswagen = marcaRepository.save(new Marca("Volkswagen"));
        Marca chevrolet = marcaRepository.save(new Marca("Chevrolet"));
        Marca fiat = marcaRepository.save(new Marca("Fiat"));

        Modelo corolla = modeloRepository.save(new Modelo("Corolla", TipoVeiculo.SEDAN, toyota));
        Modelo hilux = modeloRepository.save(new Modelo("Hilux", TipoVeiculo.PICAPE, toyota));
        Modelo civic = modeloRepository.save(new Modelo("Civic", TipoVeiculo.SEDAN, honda));
        Modelo cg160 = modeloRepository.save(new Modelo("CG 160", TipoVeiculo.MOTO, honda));
        Modelo polo = modeloRepository.save(new Modelo("Polo", TipoVeiculo.HATCH, volkswagen));
        Modelo tcross = modeloRepository.save(new Modelo("T-Cross", TipoVeiculo.SUV, volkswagen));
        Modelo onix = modeloRepository.save(new Modelo("Onix", TipoVeiculo.HATCH, chevrolet));
        Modelo toro = modeloRepository.save(new Modelo("Toro", TipoVeiculo.PICAPE, fiat));

        veiculoRepository.save(new Veiculo(corolla, 2022, "Prata", new BigDecimal("135900.00"), 28000, StatusVeiculo.DISPONIVEL));
        veiculoRepository.save(new Veiculo(hilux, 2021, "Branco", new BigDecimal("232000.00"), 45000, StatusVeiculo.DISPONIVEL));
        veiculoRepository.save(new Veiculo(civic, 2020, "Preto", new BigDecimal("112500.00"), 52000, StatusVeiculo.RESERVADO));
        veiculoRepository.save(new Veiculo(cg160, 2023, "Vermelho", new BigDecimal("16800.00"), 6000, StatusVeiculo.DISPONIVEL));
        veiculoRepository.save(new Veiculo(polo, 2023, "Azul", new BigDecimal("98700.00"), 15000, StatusVeiculo.DISPONIVEL));
        veiculoRepository.save(new Veiculo(tcross, 2022, "Cinza", new BigDecimal("124300.00"), 31000, StatusVeiculo.VENDIDO));
        veiculoRepository.save(new Veiculo(onix, 2019, "Branco", new BigDecimal("62900.00"), 74000, StatusVeiculo.DISPONIVEL));
        veiculoRepository.save(new Veiculo(toro, 2021, "Prata", new BigDecimal("118000.00"), 39000, StatusVeiculo.DESCONTINUADO));
    }
}
