package com.prouvetech.prouvetech.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    @jakarta.annotation.PostConstruct
    public void init() {
        // Charge le fichier .env
        Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();

        // Affiche les valeurs des propriétés
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

        // Vérifie si les variables sont correctement chargées
        System.out.println("DB URL: " + System.getProperty("SPRING_DATASOURCE_URL"));
        System.out.println("DB User: " + System.getProperty("SPRING_DATASOURCE_USERNAME"));
        System.out.println("DB Password: " + System.getProperty("SPRING_DATASOURCE_PASSWORD"));
        System.out.println("JWT Secret: " + System.getProperty("JWT_SECRET_KEY"));
    }
}
