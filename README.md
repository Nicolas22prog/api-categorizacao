# Mercado Livre Categorias e Produtos - API Java

Este projeto é uma aplicação backend em Java para importar, armazenar e exibir categorias e produtos do Mercado Livre utilizando a API pública do Mercado Livre. A aplicação utiliza Jakarta EE, JPA, JAX-RS e JSF para gerenciamento, persistência e interface.

---

## Funcionalidades

- Importação das categorias do Mercado Livre via API REST.
- Importação dos produtos de cada categoria cadastrada no banco.
- Persistência das categorias e produtos em banco de dados relacional via JPA.
- API REST para listagem de categorias e produtos.
- Interface JSF para filtragem e visualização dos produtos por categoria.

---

## Tecnologias Utilizadas

- Java 17+
- Jakarta EE (JPA, CDI, JAX-RS, Transactions)
- Jackson e Gson para JSON parsing
- JPA (EclipseLink/Hibernate) para persistência
- JSF para interface web
- Banco de dados relacional (ex: PostgreSQL, MySQL)
- Maven para gerenciamento de dependências e build

---

## Estrutura do Projeto

- `Categorias` — Entidade JPA para categorias
- `Produtos` — Entidade JPA para produtos
- `CategoriaDTO` e `ProdutosDTO` — Classes DTO para deserialização do JSON da API Mercado Livre
- `CategoriasService` e `ProdutosService` — Serviços para importar dados da API e converter para entidades
- `CategoriasResource` e `ProdutosResource` — Endpoints REST para gerenciamento e importação
- `ProdutosBean` — Managed Bean JSF para filtrar e exibir produtos na interface

---

## Como usar

1. Configure o banco de dados e ajuste o `persistence.xml` para conectar-se ao seu banco.
2. Faça o deploy da aplicação em um servidor Jakarta EE (ex: Payara, WildFly, TomEE).
3. Acesse o endpoint REST para importar categorias:
