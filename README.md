# 🛒 API de Categorização de Produtos - Mercado Livre

Este projeto Java EE expõe uma API REST que integra com o Mercado Livre para importar e armazenar **categorias** e **produtos**, permitindo consultas otimizadas e organizadas por categoria.

---

## 📦 Tecnologias Utilizadas

- Java 17
- Jakarta EE (CDI, JPA, JAX-RS)
- Hibernate / JPA
- JSF (interface opcional)
- Gson & Jackson (JSON parsing)
- H2 / MySQL (ajustável)
- Maven

---

## 🚀 Funcionalidades

- 🔄 Importação de categorias diretamente da API do Mercado Livre
- 🔄 Importação de produtos por categoria
- ✅ Armazenamento em banco de dados com validação para evitar duplicatas
- 📋 Listagem de produtos com filtro por categoria
- 📁 Integração com interface JSF (ViewScoped Bean)

---

## 🔧 Configuração do Ambiente

### 1. Clone o repositório

```bash
git clone https://github.com/Nicolas22prog/api-categorizacao.git
cd api-categorizacao
```

### 2. Configuração de ambiente

Crie o arquivo `config.properties` em `src/main/resources`:

```
mercadolivre.token=SEU_TOKEN_AQUI
```

> 🔒 Este arquivo está no `.gitignore` por segurança.

---

### 3. Configurar banco de dados (opcional)

Por padrão, você pode usar H2 em memória para testes.  
Para produção, altere o `persistence.xml` para usar MySQL ou outro banco de sua escolha.

---

## ▶️ Executando o Projeto

Você pode executar a aplicação em um servidor compatível com Jakarta EE:

### Ex: Payara, Wildfly, GlassFish

1. Empacote com Maven:

```bash
mvn clean package
```

2. Faça o deploy do `.war` no seu servidor de aplicação.

---

## 📡 Endpoints da API REST

### Listar produtos

```
GET /produtos
```

> Lista todos os produtos importados com suas categorias

---

### Importar produtos de todas as categorias

```
POST /produtos/importar
```

> Para cada categoria no banco, importa os produtos da API do Mercado Livre

---

## 🧠 Estrutura do Projeto

```
com.mycompany.categorias.mercado
├── controller     # Endpoints REST
├── service        # Regras de negócio
├── dto            # DTOs para comunicação com APIs externas
├── entity         # Entidades JPA
├── view           # JSF Managed Beans (UI)
├── config         # Configurações como TokenConfig
├── util           # Utilitários e auxiliares
```

---

## 🧪 Testes

Em breve serão adicionados testes de integração com JUnit e H2.

---

## ✅ Próximas melhorias

- Swagger/OpenAPI para documentação automática da API
- Scheduler para importação periódica de produtos
- Sistema de cache para evitar requisições desnecessárias
- Autenticação com JWT

---

## 🤝 Contribuindo

1. Fork este repositório
2. Crie sua branch: `git checkout -b feature/minha-feature`
3. Commit suas alterações: `git commit -m 'Minha feature'`
4. Push para sua branch: `git push origin feature/minha-feature`
5. Abra um Pull Request 🚀

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 🙋 Autor

Desenvolvido por **Nicolas22prog**  
GitHub: [@Nicolas22prog](https://github.com/Nicolas22prog)
