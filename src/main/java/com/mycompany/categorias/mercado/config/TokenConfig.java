/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.categorias.mercado.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TokenConfig {
    private static final String PROPERTIES_FILE = "config.properties";
    private static final Properties props = new Properties();

    static {
        try (InputStream input = TokenConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input != null) {
                props.load(input);
            } else {
                throw new RuntimeException("Arquivo config.properties não encontrado no classpath");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar config.properties", e);
        }
    }

    public static String getToken() {
        return props.getProperty("mercadolivre.token");
    }
}
