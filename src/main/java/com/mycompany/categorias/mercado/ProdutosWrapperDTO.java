/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Classe DTO usada para mapear o JSON retornado pela API do Mercado Livre.
 * A resposta da API contém uma lista de produtos dentro do campo "results".
 */
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora qualquer campo do JSON que não esteja mapeado nesta classe
public class ProdutosWrapperDTO {

    // Lista de objetos ProdutosDTO recebidos no campo "results" do JSON da API
    private List<ProdutosDTO> results;

    // Getter da lista de resultados
    public List<ProdutosDTO> getResults() {
        return results;
    }

    // Setter da lista de resultados
    public void setResults(List<ProdutosDTO> results) {
        this.results = results;
    }
}
