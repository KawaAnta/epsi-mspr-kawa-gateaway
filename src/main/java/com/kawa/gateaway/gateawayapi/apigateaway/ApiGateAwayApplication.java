package com.kawa.gateaway.gateawayapi.apigateaway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Classe principale de l'API GateAway.
 */
// CHECKSTYLE:OFF
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGateAwayApplication {

    /**
     * Méthode principale de lancement du service.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(ApiGateAwayApplication.class, args);
    }

}
// CHECKSTYLE:ON
