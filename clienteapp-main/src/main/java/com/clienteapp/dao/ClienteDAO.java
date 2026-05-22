package com.clienteapp.dao;

import com.clienteapp.config.ConexaoDB;
import com.clienteapp.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAO {

    
    public void salvar(Cliente cliente) throws SQLException {

        
        String sql = "INSERT INTO clientes (nome, email, telefone) VALUES (?, ?, ?)";

        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());

            
            stmt.executeUpdate();

            
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                cliente.setId(keys.getInt(1));
            }

            System.out.println(" Cliente salvo com ID: " + cliente.getId());
        }
    }


    public List<Cliente> listarTodos() throws SQLException {

        String sql = "SELECT * FROM clientes ORDER BY nome";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("telefone")
                );
                clientes.add(c);
            }
        }
        return clientes;
    }

    
    public Optional<Cliente> buscarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM clientes WHERE id = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                
                return Optional.of(new Cliente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("telefone")
                ));
            }
        }
        
        return Optional.empty();
    }

    
    public void atualizar(Cliente cliente) throws SQLException {

        String sql = "UPDATE clientes SET nome = ?, email = ?, telefone = ? WHERE id = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setInt(4, cliente.getId());

           
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println(" Cliente atualizado com sucesso!");
            } else {
                System.out.println("  Nenhum cliente encontrado com esse ID.");
            }
        }
    }

    
    public void deletar(int id) throws SQLException {

        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println(" Cliente deletado com sucesso!");
            } else {
                System.out.println("  Nenhum cliente encontrado com esse ID.");
            }
        }
    }
}