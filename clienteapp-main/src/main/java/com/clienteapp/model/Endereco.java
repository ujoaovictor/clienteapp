package com.clienteapp.model;


public class Endereco {

   
    private int id;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private int clienteId; 

   
    public Endereco(String cep, String logradouro, String bairro,
                    String cidade, String estado, int clienteId) {
        this.cep        = cep;
        this.logradouro = logradouro;
        this.bairro     = bairro;
        this.cidade     = cidade;
        this.estado     = estado;
        this.clienteId  = clienteId;
    }

    
    public Endereco(int id, String cep, String logradouro, String bairro,
                    String cidade, String estado, int clienteId) {
        this.id         = id;
        this.cep        = cep;
        this.logradouro = logradouro;
        this.bairro     = bairro;
        this.cidade     = cidade;
        this.estado     = estado;
        this.clienteId  = clienteId;
    }

    

    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }

    public String getCep()                   { return cep; }
    public void setCep(String cep)           { this.cep = cep; }

    public String getLogradouro()                      { return logradouro; }
    public void setLogradouro(String logradouro)       { this.logradouro = logradouro; }

    public String getBairro()                { return bairro; }
    public void setBairro(String bairro)     { this.bairro = bairro; }

    public String getCidade()                { return cidade; }
    public void setCidade(String cidade)     { this.cidade = cidade; }

    public String getEstado()                { return estado; }
    public void setEstado(String estado)     { this.estado = estado; }

    public int getClienteId()                    { return clienteId; }
    public void setClienteId(int clienteId)      { this.clienteId = clienteId; }

    // ─── toString ────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format(
            "┌─ Endereço #%d (Cliente #%d) ────\n" +
            "│  CEP:        %s\n"                   +
            "│  Logradouro: %s\n"                   +
            "│  Bairro:     %s\n"                   +
            "│  Cidade:     %s - %s\n"              +
            "└─────────────────────────────────",
            id, clienteId, cep, logradouro, bairro, cidade, estado
        );
    }
}