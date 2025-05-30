/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado;


import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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

@ApplicationScoped
public class ProdutosService {
    @Inject
    private CategoriasResource cr;
    
    
    private static final String URL_BASE = "https://api.mercadolibre.com/sites/MLB/search?";
    
    private Client client = ClientBuilder.newClient();
    private ObjectMapper mapper = new ObjectMapper();
   
    
    public List<Produtos> importarProdutos(String categoriaId){
        String token = "APP_USR-8021611602487823-053011-0f6980901ee114c4b4bab916876ce354-445066511";
        
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
}
