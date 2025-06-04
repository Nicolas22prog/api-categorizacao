/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) que representa um produto retornado pela
 * API do Mercado Livre. Essa classe é usada para mapear os dados recebidos
 * em JSON para objetos Java.
 */
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora qualquer campo desconhecido no JSON
public class ProdutosDTO {

    // ID do produto (ex: MLA123456)
    private String id;

    // Título ou nome do produto
    private String title;

    // Link direto para a página do produto no Mercado Livre
    private String permalink;

    // Preço do produto
    private BigDecimal price;

    // Ordem de exibição no back-end (usado para ordenação personalizada)
    private int order_backend;

    // ID da categoria principal à qual o produto pertence
    private String main_category;

    //link da imagem do produto
    private String thumbnail;
    // Getters e Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPermaLink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getOrder_backend() {
        return order_backend;
    }

    public void setOrder_backend(int order_backend) {
        this.order_backend = order_backend;
    }

    public String getMain_category() {
        return main_category;
    }

    public void setMain_category(String categoria) {
        this.main_category = categoria;
    }
    
    public String getThumbnail(){return thumbnail;}
    public void setThumbnail(String thumbnail){this.thumbnail=thumbnail;}
}
