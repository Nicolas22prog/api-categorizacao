/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado;

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

@Path("/produtos")
public class ProdutosResource {
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private ProdutosService ps;
    
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<Produtos> listar(){
        return em.createQuery("SELECT p FROM Produtos p JOIN FETCH p.main_category",Produtos.class).getResultList();
    }
 @POST
 @Path("/importar")
 @Transactional
 public Response importarProdutos() { 
  List<Categorias> categorias = em.createQuery("SELECT c FROM Categorias c", Categorias.class).getResultList();
  
  for(Categorias categoria : categorias){
                                                                        
      List<Produtos> produtos = ps.importarProdutos(categoria.getId()); //<--- aqui é onde o metodo importarProdutos recebe os Ids das categorias cadastradas no banco de dados
      for(Produtos produto : produtos) {
          Produtos existente = em.find(Produtos.class, produto.getId());
          if(existente == null) {
          em.persist(produto);
      }}
  }
      return Response.ok("Importação concluída com sucesso").build();
  }
 }


