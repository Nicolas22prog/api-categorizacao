/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado.controller;

import com.mycompany.categorias.mercado.entity.Categorias;
import com.mycompany.categorias.mercado.entity.Produtos;
import com.mycompany.categorias.mercado.service.ProdutosService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Recurso REST para manipulação dos produtos.
 */
@Path("/produtos")
public class ProdutosResource {

    // Gerenciador de entidades (JPA) para interações com o banco de dados
    @PersistenceContext
    private EntityManager em;

    // Injeção do serviço responsável pelas regras de negócio de produtos
    @Inject
    private ProdutosService ps;

    /**
     * Endpoint GET que retorna todos os produtos cadastrados no banco,
     * incluindo o relacionamento com a categoria principal (main_category).
     *
     * @return Lista de produtos em formato JSON
     */
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<Produtos> listar() {
        return em.createQuery(
            "SELECT p FROM Produtos p JOIN FETCH p.main_category",
            Produtos.class
        ).getResultList();
    }

    /**
     * Endpoint POST que importa produtos para cada categoria cadastrada.
     * Para cada categoria do banco de dados:
     *  - Chama o método importarProdutos passando o ID da categoria;
     *  - Verifica se o produto já existe no banco (pelo ID);
     *  - Se não existir, o produto é persistido.
     *
     * @return Resposta de sucesso após a importação
     */
    @POST
    @Path("/importar")
    @Transactional
    public Response importarProdutos() {

        // Busca todas as categorias cadastradas no banco
        List<Categorias> categorias = em.createQuery("SELECT c FROM Categorias c", Categorias.class).getResultList();

        // Para cada categoria, importa os produtos da API do Mercado Livre
        for (Categorias categoria : categorias) {

            // Chamada ao método que importa os produtos da API para a categoria atual
            List<Produtos> produtos = ps.importarProdutos(categoria.getId()); // <-- Aqui o método recebe os IDs das categorias

            for (Produtos produto : produtos) {
                // Verifica se o produto já existe no banco (evita duplicações)
                Produtos existente = em.find(Produtos.class, produto.getId());

                // Se não existir, persiste o novo produto
                if (existente == null) {
                    em.persist(produto);
                }
            }
        }

        // Retorna uma resposta de sucesso
        return Response.ok("Importação concluída com sucesso").build();
    }
}
