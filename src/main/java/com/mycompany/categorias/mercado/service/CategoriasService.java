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
import jakarta.inject.Inject;
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
    
    @Inject
    private ProdutosService ps;
    
    
    public List<Categorias> importarCategorias(){
        
        
        // Token de acesso da API do Mercado Livre

        String token = ps.carregarToken();       
        
     /*
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
                    // Converte o DTO em um objeto Categorias para persistência no banco de dados
                    Categorias c = new Categorias();
                    c.setId(dto.getId());
                    c.setName(dto.getName());
                    categorias.add(c);
                }
                return categorias;
                    }catch(Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("Erro ao converter JSON para lista de categorias", e);
                    }
        }
        } 
   
     
    }       