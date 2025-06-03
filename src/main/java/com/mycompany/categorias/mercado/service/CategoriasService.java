/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.categorias.mercado.dto.CategoriaDTO;
import com.mycompany.categorias.mercado.entity.Categorias;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class CategoriasService {
    private static final String URL_BASE = "https://api.mercadolibre.com/sites/MLB/categories";
 
    
    
    public List<Categorias> importarCategorias(){
        
        
        // Token de acesso da API do Mercado Livre

        String token = "APP_USR-8021611602487823-060307-662d87ba6d0fc8f86e5023a5667e85c1-445066511";       
        
     /**
 * Importa categorias do Mercado Livre via API.
 * @return Lista de categorias convertidas do JSON da API
 */

        
        try(Client client = ClientBuilder.newClient()){
        WebTarget target = client.target(URL_BASE);
        try(Response response = target
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .get()){
        if (response.getStatus() != 200) {
        throw new RuntimeException("Erro ao buscar categorias do Mercado Livre, status: " + response.getStatus());
        }
        
// Lê o JSON com as categorias retornado pela API do Mercado Livre e o converte em uma lista de DTOs

            String json = response.readEntity(String.class);
            System.out.println("Resposta Json recebida" + json);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<CategoriaDTO>>(){}.getType();
            List<CategoriaDTO> dtos = gson.fromJson(json, listType);
                List<Categorias> categorias = new ArrayList<>();
                for (CategoriaDTO dto : dtos) {
                    Categorias categoria = converterParaCategoria(dto);
                    categorias.add(categoria);
                }
                return categorias;
                    }catch(Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("Erro ao converter JSON para lista de categorias", e);
                    }
        }
        } 
   
// Converte o DTO em um objeto Categorias para persistência no banco de dados

    public Categorias converterParaCategoria(CategoriaDTO dto) {
        Categorias c = new Categorias();
        c.setId(dto.getId());
        c.setName(dto.getName());
        
        return c;
    }
    
    @PersistenceContext
    private EntityManager em;
   
// Salva a lista de categorias no banco de dados

    @Transactional
    public void salvarCategorias(List<Categorias> categorias){
        for(Categorias c: categorias) {            
            em.persist(c);
        }
    }       
    }
