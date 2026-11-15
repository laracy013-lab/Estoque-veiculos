-- =====================================================================
-- Modelo físico (MySQL) - Sistema Automotivo: Gestão de Estoque de Veículos
-- Observação: com spring.jpa.hibernate.ddl-auto=update o próprio sistema cria
-- estas tabelas automaticamente. Este script serve como documentação do
-- modelo físico e pode ser executado manualmente no MySQL Workbench.
-- =====================================================================

CREATE DATABASE IF NOT EXISTS estoque_veiculos
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE estoque_veiculos;

CREATE TABLE IF NOT EXISTS marcas (
  id    BIGINT       NOT NULL AUTO_INCREMENT,
  nome  VARCHAR(60)  NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_marcas_nome (nome)
);

CREATE TABLE IF NOT EXISTS modelos (
  id        BIGINT       NOT NULL AUTO_INCREMENT,
  nome      VARCHAR(80)  NOT NULL,
  tipo      VARCHAR(20)  NOT NULL,  -- HATCH, SEDAN, SUV, PICAPE, MOTO, UTILITARIO
  marca_id  BIGINT       NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_modelos_marca FOREIGN KEY (marca_id) REFERENCES marcas (id)
);

CREATE TABLE IF NOT EXISTS veiculos (
  id             BIGINT         NOT NULL AUTO_INCREMENT,
  modelo_id      BIGINT         NOT NULL,
  ano            INT            NOT NULL,
  cor            VARCHAR(30)    NOT NULL,
  preco          DECIMAL(12,2)  NOT NULL,
  quilometragem  INT            NOT NULL,
  status         VARCHAR(20)    NOT NULL,  -- DISPONIVEL, RESERVADO, VENDIDO, DESCONTINUADO
  PRIMARY KEY (id),
  CONSTRAINT fk_veiculos_modelo FOREIGN KEY (modelo_id) REFERENCES modelos (id)
);
