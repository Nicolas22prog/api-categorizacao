/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class ProdutosBean implements Serializable{
    
    @Inject
    private ProdutosService ps;
    
    private String categoriaSelecionada;
    private List<Categorias> categorias;
    private List<Produtos> produtosFiltrados;
    
    @PostConstruct
    public void init(){
        categorias= ps.listarCategorias();
    }
    
    
    public void filtrar(){
        if(categoriaSelecionada != null && !categoriaSelecionada.isEmpty()){
            produtosFiltrados = ps.busarPorCategoria(categoriaSelecionada);
            
        } else {
            produtosFiltrados = null;
        }
    }
    
    
  public String getCategoriaSelecionada(){return categoriaSelecionada;}
  public void setCategoriaSelecionada(String categoriaSelecionada) {this.categoriaSelecionada=categoriaSelecionada;}
  
  public List<Categorias> getCategorias(){return categorias;}
  public List<Produtos> getProdutosFiltrados(){return produtosFiltrados;}
}
