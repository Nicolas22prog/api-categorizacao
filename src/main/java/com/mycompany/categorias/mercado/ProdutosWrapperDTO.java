/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutosWrapperDTO {
    private List<ProdutosDTO> results;
    
    public List<ProdutosDTO> getResults(){return results;}
    public void setResults(List<ProdutosDTO> results){this.results=results;}
}
