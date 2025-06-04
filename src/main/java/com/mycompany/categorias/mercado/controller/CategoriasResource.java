/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado.controller;

import com.mycompany.categorias.mercado.entity.Categorias;
import com.mycompany.categorias.mercado.service.CategoriasService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Recurso REST que expõe endpoints relacionados à entidade Categorias.
 */
@Path("/categorias") // Define o caminho base da URL para os endpoints dessa classe
public class CategoriasResource {

    @PersistenceContext
    private EntityManager em; // Gerencia operações com o banco de dados

    @Inject
    private CategoriasService cs; // Serviço responsável por importar e salvar categorias

    /**
     * Retorna apenas os IDs das categorias cadastradas no banco.
     * Útil para uso interno ou testes.
     */
    @GET
    public List<String> listarIds() {
        return em.createQuery("SELECT c.id FROM Categorias c", String.class)
                 .getResultList();
    }

    /**
     * Endpoint GET para importar categorias do Mercado Livre e salvar no banco.
     * A anotação @Transactional garante que todas as operações sejam feitas em uma transação.
     * URL: /categorias/importar
     */
    
    
    @GET
    @Path("/importar")
    @Transactional
    public Response importarESalvarCategorias() {
        try{
            List<Categorias> categorias = cs.importarCategorias();
            
            for(Categorias categoria : categorias) {
                Categorias existente = em.find(Categorias.class, categoria.getId());
                
                if (existente == null) {
                    Categorias nova = new Categorias();
                    nova.setId(categoria.getId());
                    nova.setName(categoria.getName());
                    em.persist(nova);
                } else {
                    if(!existente.getName().equals(categoria.getName())){
                    existente.setName(categoria.getName());
                    
                    }
                  }
            }
                 return Response.ok("Importação concluida" ).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.serverError().entity("Erro ao importar categorias: " + e.getMessage()).build();
          }
    }
}
