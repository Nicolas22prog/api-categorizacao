/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado.beans;

import com.mycompany.categorias.mercado.entity.Categorias;
import com.mycompany.categorias.mercado.entity.Produtos;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProdutosBeanServices implements Serializable {
    
    @PersistenceContext
    private EntityManager em;
    
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
