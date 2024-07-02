package com.kawa.gateaway.gateawayapi.apigateaway.exception;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Gestionnaire d'erreurs global pour traiter les exceptions web dans un environnement réactif.
 */
@Configuration
@Order(-2)
public class GlobalErrorWebExceptionHandler extends ResponseStatusExceptionHandler {

    /**
     * Gère les exceptions et génère une réponse personnalisée pour les erreurs 404 Not Found.
     *
     * @param exchange L'instance ServerWebExchange
     * @param ex       L'instance Throwable
     * @return Un Mono<Void> indiquant quand la gestion de l'exception est terminée
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        if (ex instanceof ResponseStatusException exception) {
            if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                response.setStatusCode(HttpStatus.NOT_FOUND);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                String body = "{\"success\": false, \"data\": \"Not Found\", \"message\": \"The requested URL was not found.\"}";
                byte[] bytes = body.getBytes(StandardCharsets.UTF_8);

                return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
            }
        }

        return super.handle(exchange, ex);
    }
}