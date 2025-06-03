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
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class ProdutosService {
    
    
    private static final String URL_BASE = "https://api.mercadolibre.com/sites/MLB/search?";
    
    
    private ObjectMapper mapper = new ObjectMapper();
   
    
    public List<Produtos> importarProdutos(String categoriaId /* ID da categoria, recebido da interface, referente às categorias cadastradas no banco de dados
*/){
        // Token de acesso da API do Mercado Livre (exemplo; ideal usar de forma segura)

        String token = "APP_USR-8021611602487823-060307-662d87ba6d0fc8f86e5023a5667e85c1-445066511";
        
        
        
        try(Client client = ClientBuilder.newClient()){
                
        WebTarget target = client.target(URL_BASE)
                .queryParam("category", categoriaId)
                .queryParam("limit", 20)
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
        Categorias categoria = em.find(Categorias.class,dto.getMain_category());
        
        p.setMain_category(categoria);
        return p;
    }
    
    
    // Busca produtos filtrando pela categoria selecionada na interface do usuário (usado em ProdutosBean.java)

    public List<Produtos> busarPorCategoria(String categoriaId) {
        return em.createQuery(
        "SELECT p FROM Produtos p " +
                "JOIN FETCH p.main_category c " +
                "WHERE c.id = :categoriaId " +
                "ORDER BY p.order_backend ASC", Produtos.class).setParameter("categoriaId", categoriaId)
                .getResultList();
    }
    
    
    // Retorna a lista de categorias cadastradas, usada para preencher a interface do usuário (em ProdutosBean.java)

    public List<Categorias> listarCategorias(){
        return em.createQuery("SELECT c FROM Categorias c ORDER BY c.name", Categorias.class).getResultList();

    }
}
