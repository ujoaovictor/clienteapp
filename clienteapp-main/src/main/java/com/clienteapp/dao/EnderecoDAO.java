package com.clienteapp.dao;

import com.clienteapp.config.ConexaoDB;
import com.clienteapp.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {


    public void salvar(Endereco endereco) throws SQLException {

        String sql = "INSERT INTO enderecos (cep, logradouro, bairro, cidade, estado, cliente_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getLogradouro());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getEstado());
            stmt.setInt(6, endereco.getClienteId());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                endereco.setId(keys.getInt(1));
            }

            System.out.println("✅ Endereço salvo com ID: " + endereco.getId());
        }
    }

    
    public List<Endereco> listarPorCliente(int clienteId) throws SQLException {

        String sql = "SELECT * FROM enderecos WHERE cliente_id = ?";
        List<Endereco> enderecos = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                enderecos.add(new Endereco(
                    rs.getInt("id"),
                    rs.getString("cep"),
                    rs.getString("logradouro"),
                    rs.getString("bairro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getInt("cliente_id")
                ));
            }
        }
        return enderecos;
    }

    
    public void deletar(int id) throws SQLException {

        String sql = "DELETE FROM enderecos WHERE id = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println(" Endereço deletado com sucesso!");
            } else {
                System.out.println("  Nenhum endereço encontrado com esse ID.");
            }
        }
    }
}

