
package com.turismo.guiasweb.config;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE = "database-config.properties";
    private static Properties properties = new Properties();
    
    static {
        loadConfig();
    }
    
    public static void loadConfig() {
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try (FileInputStream input = new FileInputStream(configFile)) {
                properties.load(input);
                System.out.println("✅ Configuración cargada desde: " + CONFIG_FILE);
            } catch (IOException e) {
                System.err.println("⚠️ Error cargando configuración: " + e.getMessage());
            }
        } else {
            System.out.println("ℹ️ No se encontró configuración, usando valores por defecto");
            setDefaultConfig();
        }
    }
    
    private static void setDefaultConfig() {
        properties.setProperty("db.host", "localhost");
        properties.setProperty("db.port", "3306");
        properties.setProperty("db.name", "turismo_db");
        properties.setProperty("db.user", "root");
        properties.setProperty("db.password", "");
    }
    
    public static void saveConfig(String host, String port, String dbName, String user, String password) {
        properties.setProperty("db.host", host.trim());
        properties.setProperty("db.port", port.trim());
        properties.setProperty("db.name", dbName.trim());
        properties.setProperty("db.user", user.trim());
        properties.setProperty("db.password", password);
        
        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, "Configuración de base de datos - Generado automáticamente");
            System.out.println("✅ Configuración guardada en: " + CONFIG_FILE);
        } catch (IOException e) {
            System.err.println("❌ Error guardando configuración: " + e.getMessage());
        }
    }
    
    public static String getConnectionUrl() {
        String host = properties.getProperty("db.host", "localhost");
        String port = properties.getProperty("db.port", "3306");
        String dbName = properties.getProperty("db.name", "turismo_db");
        return String.format("jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", 
                           host, port, dbName);
    }
    
    public static String getUsername() {
        return properties.getProperty("db.user", "root");
    }
    
    public static String getPassword() {
        return properties.getProperty("db.password", "");
    }
    
    public static boolean configExists() {
        return new File(CONFIG_FILE).exists();
    }
    
    public static String getConfigInfo() {
        return String.format("""
            📊 Información de Conexión:
            ─────────────────────────
            Host: %s
            Puerto: %s
            Base de datos: %s
            Usuario: %s
            ─────────────────────────
            """,
            properties.getProperty("db.host", "localhost"),
            properties.getProperty("db.port", "3306"),
            properties.getProperty("db.name", "turismo_db"),
            properties.getProperty("db.user", "root")
        );
    }
}