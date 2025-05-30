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

@Entity
@Table(name = "produtos")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Produtos {
    @Id
    private String id;
    private String title;
    private String permalink;
    
    private BigDecimal price;
    private int order_backend;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "main_category")
    private Categorias main_category;
    
    public String getId() {return id;}
    public void setId(String id) {this.id=id;}
    public String getTitle() {return title;}
    public void setTitle(String title){this.title=title;}
    public String getPermaLink(){return permalink;}
    public void setPermalink(String permalink){this.permalink=permalink;}
    public Categorias getMain_category(){return main_category;}
    public void setMain_category(Categorias categoria) {this.main_category=categoria;}
    public BigDecimal getPrice() {return price;}
    public void setPrice(BigDecimal price) {this.price=price;}
    public int getOrder_backend(){return order_backend;}
    public void setOrder_backend(int order_backend){this.order_backend=order_backend;}
    
    
}
