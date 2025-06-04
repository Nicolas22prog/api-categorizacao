package com.mycompany.categorias.mercado.service;

import jakarta.enterprise.context.RequestScoped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.categorias.mercado.entity.Categorias;
import com.mycompany.categorias.mercado.entity.Produtos;
import com.mycompany.categorias.mercado.dto.ProdutosDTO;
import com.mycompany.categorias.mercado.dto.ProdutosWrapperDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@RequestScoped
public class ProdutosService {
    
    
    private static final String URL_BASE = "https://api.mercadolibre.com/sites/MLB/search?";
    
    
    private ObjectMapper mapper = new ObjectMapper();
    
    
    public List<Produtos> importarProdutos(String categoriaId /* ID da categoria, recebido da interface, referente às categorias cadastradas no banco de dados
*/){
        // Token de acesso da API do Mercado Livre
        String token = carregarToken();
        
        
        
        try(Client client = ClientBuilder.newClient()){
                
        WebTarget target = client.target(URL_BASE)
                .queryParam("category", categoriaId)
                .queryParam("limit", 50)
                .queryParam("sort","sold_quantity_desc");
        
        try(Response response = target
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .get()){
        
        if(response.getStatus() !=200){
            throw new RuntimeException("Erro ao buscar produtos da categoria, status: " + response);
        }
        //Lê o Json retornado pela api 
        String json = response.readEntity(String.class);
            System.out.println("Importando json" + json);            
        try{
            // Converte os dados do JSON em objetos DTO e os armazena em uma lista

            ProdutosWrapperDTO wrapper = mapper.readValue(json,ProdutosWrapperDTO.class);
            
            List<Produtos> produtos = new ArrayList<>();
            // Converte cada DTO em um objeto Produtos usando o método converterParaProduto

                for(ProdutosDTO dto: wrapper.getResults()){
                    Produtos produto = converterParaProduto(dto);
                produtos.add(produto);
                }
                       
            return produtos;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao ler json de produto", e);
        }
    }
    }
    }
    
    @PersistenceContext
    private EntityManager em;
    
    // Método que converte um DTO em um objeto Produtos, associando à categoria correspondente
    private Produtos converterParaProduto(ProdutosDTO dto) {
        Produtos p = new Produtos();
        p.setId(dto.getId());
        p.setTitle(dto.getTitle());
        p.setPermalink(dto.getPermaLink());
        p.setPrice(dto.getPrice());
        p.setOrder_backend(dto.getOrder_backend());
        p.setThumbnail(dto.getThumbnail());
        Categorias categoria = em.find(Categorias.class,dto.getMain_category());
        p.setMain_category(categoria);
        return p;
    }
    
    
   
    public String carregarToken(){
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")){
            Properties props = new Properties();
            props.load(input);
            return props.getProperty("mercadolivre.token");
        }catch (Exception e) {
        throw new RuntimeException("Erro ao carregar token da configuração", e);
    }
    
     
    }}
