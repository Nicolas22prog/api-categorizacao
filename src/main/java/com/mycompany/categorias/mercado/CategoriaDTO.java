/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO (Data Transfer Object) usado para representar os dados das categorias
 * recebidos da API do Mercado Livre.
 */
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos desconhecidos no JSON recebido
public class CategoriaDTO {

    // Identificador da categoria
    private String id;

    // Nome da categoria
    private String name;

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
