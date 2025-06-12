# Projeto de Automação de Testes – UI e API

Este repositório contém dois projetos independentes de automação de testes:

- **UI (Frontend):** Automação de testes de interface gráfica com Selenium e Cucumber.  
- **API (Backend):** Automação de testes de serviços REST com REST Assured e Cucumber.

---

## 📁 Estrutura Geral do Projeto

```
qaautomation-desafio/
├── qa-automation-frontend/
│   ├── src/
│   ├── pom.xml
│   └── ...
├── qaautomation_api/
│   ├── src/
│   ├── pom.xml
│   └── ...
├── README.md
└── .vscode/
```

O projeto está dividido em dois módulos principais, ambos com builds independentes via Maven:

- `qa-automation-frontend`: Responsável pelos testes automatizados da interface gráfica (UI).
- `qaautomation_api`: Responsável pelos testes automatizados de API.

---

## 🔍 Módulo: `qa-automation-frontend`

### ✅ Finalidade

Este módulo realiza testes end-to-end (E2E) na interface de um sistema web, utilizando Selenium WebDriver, Cucumber (Gherkin) e JUnit.

### 🗂️ Estrutura de Pastas

```
qa-automation-frontend/
├── src/test/java/
│   ├── base/       # Classe base com configurações compartilhadas
│   ├── hooks/      # Execuções antes/depois dos testes (ex: @Before, @After)
│   ├── runners/    # Runner do Cucumber
│   ├── steps/      # Implementação dos passos dos cenários
│   └── utils/      # Classes utilitárias (ex: JavaFaker)
│
├── src/test/resources/
│   ├── features/   # Cenários escritos em Gherkin
│   └── upload.txt  # Arquivo de apoio para testes de upload
│
├── target/         # Saída do build (relatórios, classes compiladas)
├── pom.xml         # Gerenciador de dependências Maven
```

---

## 🔍 Módulo: `qaautomation_api`

### ✅ Finalidade

Este módulo realiza testes automatizados de APIs REST, simulando requisições HTTP e validando respostas como status code e corpo da resposta.

### 🗂️ Estrutura de Pastas

```
qaautomation_api/
├── src/test/java/
│   ├── runners/    # Runner do Cucumber
│   └── steps/      # Implementação dos passos de teste de API
│
├── src/test/resources/
│   └── features/   # Cenários escritos em Gherkin
│
├── target/         # Saída do build (relatórios e resultados)
├── pom.xml         # Arquivo Maven com dependências e plugins
```

---

## 🧪 Tecnologias Utilizadas

### ✅ UI – `qa-automation-frontend`

- Java 8  
- Cucumber 7.14.0  
- Selenium WebDriver 4.21.0  
- JUnit 4.13.2  
- JavaFaker 1.0.2  
- WebDriverManager 5.8.0  
- Picocontainer (para injeção de dependência no Cucumber)

### ✅ API – `qaautomation_api`

- Java 17  
- Cucumber 7.11.2  
- REST Assured 5.3.0  
- Jackson Databind 2.13.4.2  
- JUnit 4.13.2

---

## 🚀 Como Executar os Testes

### 🔧 Pré-requisitos

- Java 8 (para UI) e Java 17 (para API) instalados  
- Maven instalado  
- Navegador Google Chrome instalado (para os testes de UI)
- Git Bash
  - Após a instalação, clone o repositório com o comando:
    ```git clone https://github.com/taytark/qa-automation-desafio.git```

---

### ▶️ Executar testes de UI

```bash
cd qa-automation-frontend
mvn test
```

> O WebDriverManager gerenciará automaticamente o driver do navegador.

---

### ▶️ Executar testes de API

```bash
cd qaautomation_api
mvn test
```

> Os testes simulam requisições HTTP e validam as respostas utilizando REST Assured.

---

## 🧾 Estrutura BDD (Cucumber)

Os cenários de teste são escritos em linguagem Gherkin (`.feature`) e ficam localizados em:

```
src/test/resources/features/
```

---

## 📌 Observações

- Os desafios bônus de Web Tables e Sortable não foram implementados.

- O módulo de UI usa Java 8 por questões de compatibilidade com o Selenium.  

- O módulo de API foi desenvolvido com Java 17, visando integração com pipelines modernas de CI/CD.
