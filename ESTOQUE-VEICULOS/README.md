# Sistema Automotivo – Gestão de Estoque de Veículos

Sistema CRUD orientado a objetos para gerenciar o estoque de uma concessionária:
cadastro de veículos, marcas e modelos, consulta com filtros, atualização e remoção.

**Disciplina:** Object Oriented Programming – UniFECAF

## Tecnologias
- Java 21
- Spring Boot 3.3 (Web, Data JPA, Validation)
- MySQL 8 (acesso via JPA/Hibernate)
- Maven
- Frontend simples em HTML + JavaScript (opcional, servido pelo próprio Spring Boot)

## Arquitetura (MVC + REST)
```
src/main/java/br/com/unifecaf/estoque
├── model        -> entidades (Marca, Modelo, Veiculo) e enums (StatusVeiculo, TipoVeiculo)
├── repository   -> acesso ao banco (Spring Data JPA)
├── service      -> regras de negócio
├── controller   -> endpoints REST (/api/...)
├── dto          -> objetos de entrada e saída da API (com validações)
├── exception    -> tratamento global de erros (@RestControllerAdvice)
└── config       -> carga de dados de exemplo
src/main/resources/static/index.html -> tela para listar, filtrar e cadastrar
database/schema.sql                  -> modelo físico do banco (MySQL)
```

## Como executar
1. Crie o banco no MySQL Workbench:
   ```sql
   CREATE DATABASE IF NOT EXISTS estoque_veiculos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER IF NOT EXISTS 'estoque'@'localhost' IDENTIFIED BY 'estoque123';
   GRANT ALL PRIVILEGES ON estoque_veiculos.* TO 'estoque'@'localhost';
   FLUSH PRIVILEGES;
   ```
2. Confira usuário e senha em `src/main/resources/application.properties`.
3. Abra a pasta do projeto no IntelliJ IDEA e aguarde o Maven baixar as dependências.
4. Execute a classe `EstoqueApplication` (botão verde de play).
5. Acesse **http://localhost:8080** no navegador.

As tabelas são criadas automaticamente e, na primeira execução, são inseridos veículos de exemplo.

## Endpoints da API

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/api/veiculos` | Lista veículos (aceita filtros) |
| GET | `/api/veiculos/{id}` | Busca um veículo |
| POST | `/api/veiculos` | Cadastra veículo |
| PUT | `/api/veiculos/{id}` | Atualiza todos os dados |
| PATCH | `/api/veiculos/{id}` | Atualiza preço, quilometragem e/ou status |
| DELETE | `/api/veiculos/{id}` | Remove veículo |
| GET/POST | `/api/marcas` | Lista / cadastra marcas |
| PUT/DELETE | `/api/marcas/{id}` | Atualiza / remove marca |
| GET/POST | `/api/modelos` | Lista (`?marcaId=`) / cadastra modelos |
| PUT/DELETE | `/api/modelos/{id}` | Atualiza / remove modelo |

**Filtros de veículos** (todos opcionais): `marca`, `modelo`, `tipo`, `precoMin`, `precoMax`, `ano`, `status`.

Exemplo: `http://localhost:8080/api/veiculos?marca=toyota&precoMax=150000&status=DISPONIVEL`

## Conceitos de POO aplicados
- **Classes e objetos:** Marca, Modelo, Veiculo, serviços e controllers.
- **Atributos e métodos:** cada entidade tem atributos e comportamentos (ex.: `marcarComoVendido()`).
- **Encapsulamento:** atributos privados com getters e setters.
- **Herança:** `Marca`, `Modelo` e `Veiculo` estendem `EntidadeBase`.
- **Polimorfismo / abstração:** repositórios são interfaces implementadas pelo Spring em tempo de execução.
