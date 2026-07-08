/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.turismo.guiasweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        
        // PRIMERO: Intentar variables de entorno (para Docker/Railway)
        String dbUrl = System.getenv("SPRING_DATASOURCE_URL");
        String dbUser = System.getenv("SPRING_DATASOURCE_USERNAME");
        String dbPass = System.getenv("SPRING_DATASOURCE_PASSWORD");
        
        if (dbUrl != null && !dbUrl.isEmpty()) {
            // Modo producción: usar variables de entorno
            System.out.println("🌐 Usando configuración desde variables de entorno");
            dataSource.setUrl(dbUrl);
            dataSource.setUsername(dbUser);
            dataSource.setPassword(dbPass);
        } else {
            // Modo desarrollo: usar archivo de configuración local
            System.out.println("💻 Usando configuración desde archivo local");
            dataSource.setUrl(ConfigManager.getConnectionUrl());
            dataSource.setUsername(ConfigManager.getUsername());
            dataSource.setPassword(ConfigManager.getPassword());
        }
        
        return dataSource;
    }
}