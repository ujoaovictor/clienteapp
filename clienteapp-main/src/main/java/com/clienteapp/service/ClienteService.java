package com.clienteapp.service;

import com.clienteapp.dao.ClienteDAO;
import com.clienteapp.dao.EnderecoDAO;
import com.clienteapp.model.Cliente;
import com.clienteapp.model.Endereco;
import com.clienteapp.model.ViaCepResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClienteService {

    
    private final ClienteDAO clienteDAO   = new ClienteDAO();
    private final EnderecoDAO enderecoDAO = new EnderecoDAO();
    private final ViaCepService viaCepService = new ViaCepService();

    

    public void cadastrarCliente(String nome, String email, String telefone) {
        try {
            Cliente cliente = new Cliente(nome, email, telefone);
            clienteDAO.salvar(cliente);
        } catch (SQLException e) {
            System.out.println(" Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public List<Cliente> listarClientes() {
        try {
            return clienteDAO.listarTodos();
        } catch (SQLException e) {
            System.out.println(" Erro ao listar clientes: " + e.getMessage());
            return List.of(); 
        }
    }

    public Optional<Cliente> buscarCliente(int id) {
        try {
            return clienteDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.out.println(" Erro ao buscar cliente: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void atualizarCliente(int id, String nome, String email, String telefone) {
        try {
            
            Optional<Cliente> encontrado = clienteDAO.buscarPorId(id);
            if (encontrado.isEmpty()) {
                System.out.println("  Cliente não encontrado.");
                return;
            }
            Cliente cliente = new Cliente(id, nome, email, telefone);
            clienteDAO.atualizar(cliente);
        } catch (SQLException e) {
            System.out.println(" Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void deletarCliente(int id) {
        try {
            clienteDAO.deletar(id);
        } catch (SQLException e) {
            System.out.println(" Erro ao deletar cliente: " + e.getMessage());
        }
    }



    public void adicionarEndereco(int clienteId, String cep) {

        
        try {
            Optional<Cliente> cliente = clienteDAO.buscarPorId(clienteId);
            if (cliente.isEmpty()) {
                System.out.println("  Cliente não encontrado.");
                return;
            }
        } catch (SQLException e) {
            System.out.println(" Erro ao buscar cliente: " + e.getMessage());
            return;
        }

        
        Optional<ViaCepResponse> viaCep = viaCepService.buscarCep(cep);
        if (viaCep.isEmpty()) {
            return; 
        }

        
        ViaCepResponse dados = viaCep.get();
        Endereco endereco = new Endereco(
            dados.getCep(),
            dados.getLogradouro(),
            dados.getBairro(),
            dados.getLocalidade(), 
            dados.getUf(),         
            clienteId
        );


        try {
            enderecoDAO.salvar(endereco);
            System.out.println(" Endereço encontrado e salvo:");
            System.out.println(endereco);
        } catch (SQLException e) {
            System.out.println(" Erro ao salvar endereço: " + e.getMessage());
        }
    }

    public List<Endereco> listarEnderecos(int clienteId) {
        try {
            return enderecoDAO.listarPorCliente(clienteId);
        } catch (SQLException e) {
            System.out.println(" Erro ao listar endereços: " + e.getMessage());
            return List.of();
        }
    }

    public void deletarEndereco(int id) {
        try {
            enderecoDAO.deletar(id);
        } catch (SQLException e) {
            System.out.println(" Erro ao deletar endereço: " + e.getMessage());
        }
    }
}