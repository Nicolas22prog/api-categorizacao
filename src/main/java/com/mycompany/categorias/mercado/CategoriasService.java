/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class CategoriasService {
    private static final String URL_BASE = "https://api.mercadolibre.com/sites/MLB/categories";
 
    
    private ObjectMapper mapper = new ObjectMapper();
    
    public List<Categorias> importarCategorias(){
        String token = "APP_USR-8021611602487823-060211-33cefb8e30c743be10170c5120a0bae3-445066511";
        
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

    JsonArray jsonArray = response.readEntity(JsonArray.class);
    List<CategoriaDTO> dtos = new ArrayList<>();
    for (JsonValue value : jsonArray) {
        JsonObject jsonObject = value.asJsonObject();
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(jsonObject.getString("id"));
        dto.setName(jsonObject.getString("name"));
        dtos.add(dto);
    }

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
        em.clear();
        for(Categorias c: categorias) {
            em.merge(c);
        }
    }
        
    }

