# ClienteApp 🚀

Sistema de gerenciamento de clientes e endereços desenvolvido em Java com CRUD completo, integração com banco de dados MySQL e consumo da API ViaCEP.

---

## 📋 Objetivo do Projeto

Projeto desenvolvido para prática de CRUD completo em Java, abrangendo os seguintes tópicos:
* Programação Orientada a Objetos (POO)
* Persistência e Integração com Banco de Dados MySQL via JDBC
* Consumo de APIs Web externas (ViaCEP)
* Tratamento de exceções e controle de fluxo
* Versionamento com Git
* Gerenciamento de dependências com Maven

---

## 🚀 Tecnologias Utilizadas

* **Java 17** (Linguagem principal)
* **Maven** (Gerenciamento de dependências e build)
* **MySQL 8** (Banco de dados relacional)
* **JDBC** (Conectividade Java Database)
* **API ViaCEP** (Consulta de CEP externa)
* **dotenv-java** (Configurações de variáveis de ambiente com arquivo `.env`)

---

## 📌 Funcionalidades

### 👥 Gerenciamento de Clientes
* **Cadastrar Cliente**: Insere nome, e-mail e telefone no banco de dados.
* **Listar Clientes**: Exibe todos os clientes cadastrados.
* **Buscar Cliente**: Localiza um cliente específico informando o ID.
* **Atualizar Cliente**: Permite alterar dados (nome, e-mail, telefone) de um cliente existente.
* **Deletar Cliente**: Remove o cliente e, automaticamente, apaga todos os endereços vinculados a ele (*Cascade Delete*).

### 🏠 Gerenciamento de Endereços
* **Adicionar Endereço**: Associa um endereço a um cliente. A inserção do CEP consulta a API externa ViaCEP para preencher os dados de logradouro, bairro, cidade e estado de forma automática.
* **Listar Endereços**: Exibe todos os endereços vinculados a um determinado cliente.
* **Deletar Endereço**: Remove um endereço específico pelo ID.

---

## 🔗 Relacionamento das Entidades

* Um **Cliente** pode possuir um ou mais **Endereços** (`1:N`).
* Cada **Endereço** pertence a apenas um **Cliente**.
* A integridade referencial é mantida através de chave estrangeira (`cliente_id`) com a regra `ON DELETE CASCADE`.

---

## ⚙️ Pré-requisitos

Antes de executar o projeto, você precisará ter instalado:
* **Java JDK 17** ou superior
* **Maven 3.9** ou superior
* **MySQL Server 8** ou superior
* **Git** (opcional)

---

## 🛠️ Como Executar o Projeto

### 1. Clonar o Repositório
```bash
git clone https://github.com/seu-usuario/clienteapp.git
cd clienteapp
```

### 2. Criar e Configurar o Banco de Dados
Você pode executar o script SQL completo que já está na raiz do projeto em `database.sql`, ou rodar os seguintes comandos diretamente no console do seu MySQL:
```sql
CREATE DATABASE IF NOT EXISTS clienteapp;
USE clienteapp;

CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS enderecos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cep VARCHAR(20) NOT NULL,
    logradouro VARCHAR(150),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(2),
    cliente_id INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);
```

### 3. Configurar as Variáveis de Ambiente
Crie um arquivo chamado `.env` na raiz do projeto (mesma pasta onde está o `pom.xml`) com as credenciais do seu banco de dados MySQL:
```env
DB_URL=jdbc:mysql://localhost:3306/clienteapp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USER=seu_usuario_mysql
DB_PASSWORD=sua_senha_mysql
```
> 💡 *Nota: Existe um modelo de exemplo chamado `.env.example` na raiz para referência.*

### 4. Compilar e Empacotar o Projeto
Utilize o Maven para baixar as dependências e compilar o código executável:
```bash
mvn clean package
```

### 5. Executar a Aplicação
Você pode rodar diretamente com o Maven:
```bash
mvn exec:java "-Dexec.mainClass=com.clienteapp.Main"
```
Ou executar o arquivo `.jar` gerado na pasta `/target` contendo as dependências embutidas:
```bash
java -jar target/clienteapp-1.0-SNAPSHOT-jar-with-dependencies.jar
```

---

## 🌐 API Externa Utilizada

O projeto consome a API do **[ViaCEP](https://viacep.com.br/)** para consulta de endereço automática.
* **URL de Exemplo**: `https://viacep.com.br/ws/01001000/json/`

---

## 📁 Estrutura do Projeto

```text
clienteapp/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── clienteapp/
│                   ├── config/      # Conexão DB
│                   ├── controller/  # Menu e entrada de dados
│                   ├── dao/         # Comunicação com Banco (CRUD)
│                   ├── model/       # Entidades
│                   ├── service/     # Regras de Negócio e API ViaCEP
│                   └── Main.java    # Entrada da Aplicação
├── database.sql                     # Script SQL do banco de dados
├── pom.xml                          # Dependências Maven
├── .env                             # Credenciais do Banco (Não versionar!)
├── .env.example                     # Exemplo de configuração do banco
└── README.md                        # Documentação do projeto
```

---

## 👨‍💻 Autor

Projeto desenvolvido para fins acadêmicos e práticas de banco de dados / desenvolvimento em Java.