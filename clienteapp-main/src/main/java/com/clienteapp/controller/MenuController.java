package com.clienteapp.controller;

import com.clienteapp.model.Cliente;
import com.clienteapp.model.Endereco;
import com.clienteapp.service.ClienteService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuController {

    
    private final Scanner scanner       = new Scanner(System.in);
    private final ClienteService service = new ClienteService();



    public void iniciar() {
        int opcao = -1;

    
        while (opcao != 0) {
            exibirMenu();
            opcao = lerInt("Escolha uma opção");

            switch (opcao) {
                case 1  -> cadastrarCliente();
                case 2  -> listarClientes();
                case 3  -> buscarCliente();
                case 4  -> atualizarCliente();
                case 5  -> deletarCliente();
                case 6  -> adicionarEndereco();
                case 7  -> listarEnderecos();
                case 8  -> deletarEndereco();
                case 0  -> System.out.println("\n Até logo!");
                default -> System.out.println("\n  Opção inválida. Tente novamente.");
            }
        }
    }

    
    private void exibirMenu() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║        CLIENTEAPP  v1.0          ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║  --- CLIENTES ---                ║");
        System.out.println("║  1. Cadastrar cliente            ║");
        System.out.println("║  2. Listar todos os clientes     ║");
        System.out.println("║  3. Buscar cliente por ID        ║");
        System.out.println("║  4. Atualizar cliente            ║");
        System.out.println("║  5. Deletar cliente              ║");
        System.out.println("║  --- ENDEREÇOS ---               ║");
        System.out.println("║  6. Adicionar endereço (ViaCEP)  ║");
        System.out.println("║  7. Listar endereços do cliente  ║");
        System.out.println("║  8. Deletar endereço             ║");
        System.out.println("║  ---                             ║");
        System.out.println("║  0. Sair                         ║");
        System.out.println("╚══════════════════════════════════╝");
    }



    private void cadastrarCliente() {
        System.out.println("\n── Cadastrar Cliente ──────────────");

        String nome     = lerTexto("Nome");
        String email    = lerTexto("Email");
        String telefone = lerTexto("Telefone");

        service.cadastrarCliente(nome, email, telefone);
    }

    private void listarClientes() {
        System.out.println("\n── Lista de Clientes ──────────────");

        List<Cliente> clientes = service.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("  Nenhum cliente cadastrado.");
            return;
        }

        
        clientes.forEach(System.out::println);
        System.out.println("Total: " + clientes.size() + " cliente(s).");
    }

    private void buscarCliente() {
        System.out.println("\n── Buscar Cliente ─────────────────");

        int id = lerInt("ID do cliente");
        Optional<Cliente> cliente = service.buscarCliente(id);


        cliente.ifPresentOrElse(
            System.out::println,
            () -> System.out.println("  Cliente não encontrado.")
        );
    }

    private void atualizarCliente() {
        System.out.println("\n── Atualizar Cliente ──────────────");

        int id = lerInt("ID do cliente a atualizar");

    
        Optional<Cliente> atual = service.buscarCliente(id);
        if (atual.isEmpty()) {
            System.out.println("  Cliente não encontrado.");
            return;
        }
        System.out.println("Dados atuais:");
        System.out.println(atual.get());

        System.out.println("\nDigite os novos dados:");
        String nome     = lerTexto("Novo nome");
        String email    = lerTexto("Novo email");
        String telefone = lerTexto("Novo telefone");

        service.atualizarCliente(id, nome, email, telefone);
    }

    private void deletarCliente() {
        System.out.println("\n── Deletar Cliente ────────────────");

        int id = lerInt("ID do cliente a deletar");


        Optional<Cliente> cliente = service.buscarCliente(id);
        if (cliente.isEmpty()) {
            System.out.println("  Cliente não encontrado.");
            return;
        }

        System.out.println("Você está prestes a deletar:");
        System.out.println(cliente.get());
        System.out.println("  Isso também deletará todos os endereços deste cliente!");
        String confirmacao = lerTexto("Confirma? (s/n)");

        if (confirmacao.equalsIgnoreCase("s")) {
            service.deletarCliente(id);
        } else {
            System.out.println(" Operação cancelada.");
        }
    }

    

    private void adicionarEndereco() {
        System.out.println("\n── Adicionar Endereço (ViaCEP) ────");

        int clienteId = lerInt("ID do cliente");
        String cep    = lerTexto("CEP (apenas números ou com traço)");

        
        service.adicionarEndereco(clienteId, cep);
    }

    private void listarEnderecos() {
        System.out.println("\n── Endereços do Cliente ───────────");

        int clienteId = lerInt("ID do cliente");
        List<Endereco> enderecos = service.listarEnderecos(clienteId);

        if (enderecos.isEmpty()) {
            System.out.println("  Nenhum endereço cadastrado para este cliente.");
            return;
        }

        enderecos.forEach(System.out::println);
        System.out.println("Total: " + enderecos.size() + " endereço(s).");
    }

    private void deletarEndereco() {
        System.out.println("\n── Deletar Endereço ───────────────");

        int id = lerInt("ID do endereço a deletar");
        service.deletarEndereco(id);
    }


    

    
    private String lerTexto(String campo) {
        System.out.print(campo + ": ");
        return scanner.nextLine().trim();
    }

   
    private int lerInt(String campo) {
        while (true) {
            try {
                System.out.print(campo + ": ");
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("  Digite apenas números inteiros.");
            }
        }
    }
}