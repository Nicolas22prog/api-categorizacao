/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade JPA que representa uma categoria vinda do Mercado Livre.
 */
@Entity // Indica que esta classe é uma entidade persistente
@Table(name = "categorias") // Nome da tabela no banco de dados
public class Categorias {

    @Id // Define o campo 'id' como chave primária da tabela
    private String id;

    private String name; // Nome da categoria

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
