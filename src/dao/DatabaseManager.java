package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

public static void createAllTables() {
    try (Connection conn = ConnectionFactory.getConnection();
         Statement stmt = conn.createStatement()) {

        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS CategoriaFinanceira (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                descricao TEXT
            )
            """);

        stmt.executeUpdate("""
             CREATE TABLE IF NOT EXISTS ParceiroNegocio (
                id INT AUTO_INCREMENT PRIMARY KEY,
                tipo VARCHAR(50) NOT NULL,
                identificador_fiscal VARCHAR(20) NOT NULL,
                nome VARCHAR(50) NOT NULL
            )
            """);

        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS Usuario (
                id INT AUTO_INCREMENT PRIMARY KEY,
                email VARCHAR(100) NOT NULL UNIQUE,
                senha VARCHAR(100) NOT NULL,
                nome VARCHAR(20) NOT NULL
            )
            """);

        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS Despesa (
                id INT AUTO_INCREMENT PRIMARY KEY,
                valor DECIMAL(10,2) NOT NULL,
                data DATE NOT NULL,
                descricao TEXT,
                categoria_id INT,
                parceiro_id INT,
                status VARCHAR(20),
                paga BOOLEAN NOT NULL,
                FOREIGN KEY (categoria_id) REFERENCES CategoriaFinanceira(id),
                FOREIGN KEY (parceiro_id) REFERENCES ParceiroNegocio(id)
            )
            """);

        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS Receita (
                id INT AUTO_INCREMENT PRIMARY KEY,
                valor DECIMAL(10,2) NOT NULL,
                data DATE NOT NULL,
                descricao TEXT,
                categoria_id INT,
                parceiro_id INT,
                status VARCHAR(20),
                FOREIGN KEY (categoria_id) REFERENCES CategoriaFinanceira(id),
                FOREIGN KEY (parceiro_id) REFERENCES ParceiroNegocio(id)
            )
            """);

        System.out.println("Todas as tabelas foram criadas com sucesso!");

    } catch (SQLException e) {
        System.out.println("Erro ao criar tabelas: " + e.getMessage());
    }
}
}