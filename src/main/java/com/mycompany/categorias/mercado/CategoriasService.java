/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CategoriasService {
    private static final String URL_BASE = "https://api.mercadolibre.com/sites/MLB/categories";
    
    private Client client = ClientBuilder.newClient();
    private ObjectMapper mapper = new ObjectMapper();
    
    public List<Categorias> importarCategorias(){
        String token = "APP_USR-8021611602487823-053011-0f6980901ee114c4b4bab916876ce354-445066511";
        WebTarget target = client.target(URL_BASE);
        
        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .get();
        if(response.getStatus()!=200){
            throw new RuntimeException("Erro ao buscar categorias do  Mercado Livre, status: " + response.getStatus());
        }
            String json = response.readEntity(String.class);
            
            try {
                List<CategoriaDTO> dtos = mapper.readValue(json,new TypeReference<List<CategoriaDTO>>(){});
                List<Categorias> categorias = new ArrayList<>();
                for (CategoriaDTO dto : dtos){
                    Categorias categoria = converterParaCategoria(dto);
                    categorias.add(categoria);
                }
                return categorias;
                
            }catch(Exception e) {
                throw new RuntimeException("Erro ao converter JSON para lista de categorias", e);
            }
        }
    
    public Categorias converterParaCategoria(CategoriaDTO dto) {
        Categorias c = new Categorias();
        c.setId(dto.getId());
        c.setName(dto.getName());
        
        return c;
    }
    
    @PersistenceContext
    private EntityManager em;
    
    @Transactional
    public void salvarCategorias(List<Categorias> categorias){
        for(Categorias c: categorias) {
            em.merge(c);
        }
    }
        
    }

