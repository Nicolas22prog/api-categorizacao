/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * Entidade que representa um produto importado do Mercado Livre.
 */
@Entity // Indica que esta classe é uma entidade JPA
@Table(name = "produtos") // Define o nome da tabela no banco de dados como "produtos"
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos desconhecidos ao desserializar JSON
public class Produtos {

    @Id // Define o campo como chave primária
    private String id; // ID do produto (vem da API do Mercado Livre)

    private String title; // Título ou nome do produto
    private String permalink; // URL do produto no Mercado Livre
    private BigDecimal price; // Preço do produto
    private int order_backend; // Posição do produto com base na ordenação da API

    /**
     * Relacionamento muitos-para-um com a entidade Categorias.
     * Muitos produtos podem pertencer à mesma categoria.
     */
    @ManyToOne(cascade = CascadeType.PERSIST) // Persiste a categoria se ainda não existir
    @JoinColumn(name = "main_category") // Define a coluna de chave estrangeira no banco
    private Categorias main_category;

    // Getters e setters

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

    public Categorias getMain_category() {
        return main_category;
    }

    public void setMain_category(Categorias categoria) {
        this.main_category = categoria;
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
}
