/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;

import jakarta.ws.rs.Path;

import java.util.List;

@Path("/categorias")
public class CategoriasResource {
    
    @PersistenceContext
    private EntityManager em;
    
    
    @Inject 
    private CategoriasService cs;
    
    public List<String> listarIds(){
        return em.createQuery("SELECT c.id FROM Categorias c", String.class).getResultList();
    }
    
    @GET
    @Path("/importar")
    @Transactional
    public void importarESalvarCategorias(){
        List<Categorias> categorias = cs.importarCategorias();
        cs.salvarCategorias(categorias);
        
    }
}
