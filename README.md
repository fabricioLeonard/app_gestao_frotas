<div align="center">

# 🚗 Sistema de Gestão de Frota

![Versão](https://img.shields.io/badge/Versão-v1.0.0-blue?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.x-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-3.45-003B57?style=for-the-badge&logo=sqlite&logoColor=white)
![Licença](https://img.shields.io/badge/Licença-MIT-yellow?style=for-the-badge)

**Um sistema console em Java para gerenciamento de frotas de veículos (carros e motos), com persistência em banco de dados SQLite e monitoramento em tempo real via thread dedicada.**

👨‍💻 Desenvolvido por **Fabricio Leonard** · 📅 2025

</div>

---

## 📋 Índice

- [Funcionalidades](#-funcionalidades)
- [Tecnologias Utilizadas](#️-tecnologias-utilizadas)
- [Pré-requisitos e Instalação](#-pré-requisitos-e-instalação)
- [Como Executar](#️-como-executar)
- [Arquitetura do Projeto](#️-arquitetura-do-projeto)
- [Banco de Dados](#️-banco-de-dados)
- [Comandos do Menu](#️-comandos-do-menu)
- [Material de Apoio](#-material-de-apoio)
- [Conceitos Demonstrados](#-conceitos-demonstrados)

---

## ✨ Funcionalidades

- 🚘 **Cadastro de carros** — registra marca, modelo, ano e quantidade de portas com validação de dados
- 🏍️ **Cadastro de motos** — registra marca, modelo, ano e cilindradas com validação de dados
- 📋 **Listagem polimórfica** — exibe toda a frota usando o método sobrescrito `exibirFichaTecnica()` de cada subclasse
- 🛡️ **Validação de regras de negócio** — rejeita anos fora do intervalo 1900–2027 via exceção customizada
- 🔄 **Monitoramento em background** — thread daemon reporta o status do banco e a quantidade de veículos a cada 15 segundos
- 💾 **Persistência automática** — cada veículo é salvo no SQLite imediatamente após o cadastro
- 🔒 **Queries parametrizadas** — uso de `PreparedStatement` para prevenção de SQL injection

---

## 🛠️ Tecnologias Utilizadas

| Camada | Tecnologia | Finalidade |
|--------|-----------|-----------|
| Linguagem | Java 21 | Base do projeto |
| Build | Maven 3.x | Gerenciamento de dependências e build |
| Banco de Dados | SQLite 3.45.1 (JDBC) | Persistência local em arquivo |
| Concorrência | java.lang.Thread | Monitoramento periódico em segundo plano |

---

## ⚙️ Pré-requisitos e Instalação

**Requisitos mínimos:**

- JDK 21 ou superior
- Maven 3.x
- Git

**Passo a passo:**

### 1. Clone o repositório

```bash
git clone https://github.com/fabricioLeonard/app_gestao_frotas.git
cd app_gestao_frotas
```

### 2. Compile o projeto

```bash
mvn clean compile
```

> O banco de dados `frota.db` será criado automaticamente na raiz do projeto na primeira execução.

---

## ▶️ Como Executar

### 1. Executar a aplicação

```bash
mvn exec:java -Dexec.mainClass="br.com.gestaofrota.MainOriginal"
```

Ou gere o JAR e execute diretamente:

```bash
mvn clean package
java -cp target/app_gestao_frotas-1.0-SNAPSHOT.jar br.com.gestaofrota.MainOriginal
```

### 2. Exemplo de saída

```
==========================================
  SISTEMA DE GESTÃO DE FROTA - AULA 01
==========================================

--- MENU PRINCIPAL ---
1. Cadastrar Carro
2. Cadastrar Moto
3. Listar Frota Completa (Polimorfismo)
0. Sair
> Escolha uma opção: 3

=== FROTA CADASTRADA ===
[CARRO] Toyota Corolla - Ano: 2022 | Portas: 4
[MOTO] Honda CB500 - Ano: 2023 | Cilindradas: 500cc

[BACKGROUND-THREAD] Sync status: Banco ativo | Total de veículos cadastrados: 2
```

---

## 🏗️ Arquitetura do Projeto

```
app_gestao_frotas/
│
├── docs/
│   ├── app_aula_01.pdf                    # 📖 Apresentação da aula
│   └── lista de exercicios.pdf            # 📝 Lista de exercícios
│
├── src/main/java/br/com/gestaofrota/
│   ├── Main.java                          # 🚀 Ponto de entrada e menu interativo
│   ├── database/
│   │   └── ConexaoBanco.java              # 🗄️ Camada de acesso a dados (DAO)
│   ├── model/
│   │   ├── Veiculo.java                   # 🔷 Classe base (id, marca, modelo, ano)
│   │   ├── Carro.java                     # 🚘 Subclasse com quantidadePortas
│   │   └── Moto.java                      # 🏍️ Subclasse com cilindradas
│   ├── service/
│   │   └── MonitorStatusThread.java       # 🔄 Thread de monitoramento periódico
│   └── excecoes/
│       └── DadosVeiculoInvalidosException.java  # 🛡️ Exceção de regra de negócio
│
├── pom.xml                                # ⚙️ Configuração Maven
├── frota.db                               # 💾 Banco SQLite (gerado na execução)
└── README.md                              # 📄 Esta documentação
```

### Fluxo de dados

```
Main.java (menu interativo)
  └─► cadastrarCarro() / cadastrarMoto()
        └─► ConexaoBanco.salvarVeiculo()        [database]
              ├─► Validação: ano ∈ [1900, 2027]
              │     └─► DadosVeiculoInvalidosException  [excecoes]
              └─► INSERT via PreparedStatement
                    └─► tabela `veiculos` (frota.db)

  └─► listarFrota()
        └─► ConexaoBanco.listarVeiculos()       [database]
              └─► SELECT * → List<Veiculo>
                    └─► exibirFichaTecnica()     [polimorfismo]

MonitorStatusThread (daemon, a cada 15s)
  └─► ConexaoBanco.listarVeiculos().size()
        └─► Exibe status no terminal
```

---

## 🗃️ Banco de Dados

Utiliza SQLite com uma única tabela seguindo o padrão **Single Table Inheritance**:

```sql
CREATE TABLE veiculos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tipo TEXT NOT NULL,                -- 'CARRO' ou 'MOTO' (discriminador)
    marca TEXT NOT NULL,
    modelo TEXT NOT NULL,
    ano INTEGER NOT NULL,
    detalhe_especifico INTEGER NOT NULL -- portas (carro) ou cilindradas (moto)
);
```

| Coluna | Tipo | Descrição |
|--------|------|-----------|
| `id` | INTEGER | Chave primária auto-incrementada |
| `tipo` | TEXT | Discriminador de subclasse (`CARRO` / `MOTO`) |
| `marca` | TEXT | Marca do veículo |
| `modelo` | TEXT | Modelo do veículo |
| `ano` | INTEGER | Ano de fabricação |
| `detalhe_especifico` | INTEGER | Portas (carro) ou cilindradas (moto) |

---

## 🕹️ Comandos do Menu

| Opção | Ação |
|-------|------|
| `1` | Cadastrar um novo carro |
| `2` | Cadastrar uma nova moto |
| `3` | Listar toda a frota (exibição polimórfica) |
| `0` | Encerrar a aplicação |

---

## 📚 Material de Apoio

Na pasta [`docs/`](docs/) você encontra os materiais complementares da aula:

| Arquivo | Descrição |
|---------|-----------|
| [app_aula_01.pdf](docs/app_aula_01.pdf) | Apresentação da aula |
| [lista de exercicios.pdf](docs/lista%20de%20exercicios.pdf) | Lista de exercícios |

---

## 🎓 Conceitos Demonstrados

| Conceito | Onde é aplicado |
|----------|----------------|
| Herança | `Carro` e `Moto` estendem `Veiculo` |
| Polimorfismo | `exibirFichaTecnica()` sobrescrito em cada subclasse |
| Exceções customizadas | `DadosVeiculoInvalidosException` para validação de negócio |
| Multithreading | `MonitorStatusThread` como daemon thread |
| JDBC com PreparedStatement | `ConexaoBanco` com queries parametrizadas |
| Pattern Matching (Java 21) | `instanceof Carro c` no DAO |
| Switch Expressions (Java 21) | Menu principal com `case ->` |
| Text Blocks (Java 21) | SQL de criação de tabela |

---

## 📄 Licença

Este projeto é disponibilizado sob a [Licença MIT](LICENSE) exclusivamente para fins educacionais e de aprendizado.

---

<div align="center">

Desenvolvido com ☕ Java · 🗄️ SQLite · 🧵 Threads

</div>
