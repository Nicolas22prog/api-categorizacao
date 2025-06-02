/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado;

import jakarta.enterprise.context.RequestScoped;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class ProdutosService {
    
    
    private static final String URL_BASE = "https://api.mercadolibre.com/sites/MLB/search?";
    
    private Client client = ClientBuilder.newClient();
    private ObjectMapper mapper = new ObjectMapper();
   
    
    public List<Produtos> importarProdutos(String categoriaId){
        String token = "APP_USR-8021611602487823-060211-33cefb8e30c743be10170c5120a0bae3-445066511";
        
        WebTarget target = client.target(URL_BASE)
                .queryParam("category", categoriaId)
                .queryParam("limit", 20)
                .queryParam("sort","sold_quantity_desc");
                
        
        
        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .get();
        
        if(response.getStatus() !=200){
            throw new RuntimeException("Erro ao buscar produtos da categoria, status: " + response);
        }
        
        String json = response.readEntity(String.class);
        
        try{
            ProdutosWrapperDTO wrapper = mapper.readValue(json,ProdutosWrapperDTO.class);
            
            List<Produtos> produtos = new ArrayList<>();
            
                for(ProdutosDTO dto: wrapper.getResults()){
                    Produtos produto = converterParaProduto(dto);
                produtos.add(produto);
                }
                       
            return produtos;
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler json de produto", e);
        }
    }
    
    @PersistenceContext
    private EntityManager em;
    private Produtos converterParaProduto(ProdutosDTO dto) {
        Produtos p = new Produtos();
        p.setId(dto.getId());
        p.setTitle(dto.getTitle());
        p.setPermalink(dto.getPermaLink());
        p.setPrice(dto.getPrice());
        p.setOrder_backend(dto.getOrder_backend());
        Categorias categoria = em.find(Categorias.class,dto.getMain_category());
        
        p.setMain_category(categoria);
        return p;
    }
    

    
 
    
    public List<Produtos> busarPorCategoria(String categoriaId) {
        return em.createQuery(
        "SELECT p FROM Produtos p " +
                "JOIN FETCH p.main_category c " +
                "WHERE c.id = :categoriaId " +
                "ORDER BY p.order_backend ASC", Produtos.class).setParameter("categoriaId", categoriaId)
                .getResultList();
    }
    
    public List<Categorias> listarCategorias(){
        return em.createQuery("SELECT c FROM Categorias c ORDER BY c.name", Categorias.class).getResultList();

    }
}
