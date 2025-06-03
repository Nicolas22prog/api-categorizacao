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

/**
 * Bean JSF responsável por controlar a visualização dos produtos por categoria na interface web.
 */
@Named // Torna o bean acessível na página JSF com o nome "produtosBean"
@ViewScoped // O bean vive enquanto o usuário estiver na mesma página (view)
public class ProdutosBean implements Serializable {

// Injeta o serviço que contém a lógica de acesso a produtos e categorias
    @Inject
    private ProdutosBeanServices pbs;
    
    private String categoriaSelecionada; // Categoria escolhida pelo usuário na interface
    private List<Categorias> categorias; // Lista de categorias disponíveis para seleção
    private List<Produtos> produtosFiltrados; // Produtos filtrados com base na categoria selecionada

    /**
     * Método chamado automaticamente após a construção do bean.
     * Inicializa a lista de categorias disponíveis no sistema.
     */
    @PostConstruct
    public void init() {
        categorias = pbs.listarCategorias(); // Carrega todas as categorias do banco
    }

    /**
     * Método chamado ao acionar o botão de filtro na interface.
     * Filtra os produtos com base na categoria selecionada.
     */
    public void filtrar() {
        if (categoriaSelecionada != null && !categoriaSelecionada.isEmpty()) {
            // Se uma categoria foi selecionada, filtra os produtos por essa categoria
            produtosFiltrados = pbs.busarPorCategoria(categoriaSelecionada);
        } else {
            // Se nenhuma categoria foi selecionada, limpa a lista de produtos filtrados
            produtosFiltrados = null;
        }
    }

    // Getters e setters

    public String getCategoriaSelecionada() {
        return categoriaSelecionada;
    }

    public void setCategoriaSelecionada(String categoriaSelecionada) {
        this.categoriaSelecionada = categoriaSelecionada;
    }

    public List<Categorias> getCategorias() {
        return categorias;
    }

    public List<Produtos> getProdutosFiltrados() {
        return produtosFiltrados;
    }
}
