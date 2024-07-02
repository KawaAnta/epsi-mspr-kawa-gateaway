package com.kawa.gateaway.gateawayapi.apigateaway.filter;

import com.kawa.gateaway.gateawayapi.apigateaway.util.ErrorResponseUtil;
import com.kawa.gateaway.gateawayapi.apigateaway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Un filtre pour authentifier les requêtes en validant les jetons JWT.
 * Ce filtre intercepte les requêtes et vérifie la présence d'en-têtes d'autorisation.
 * Si l'en-tête est présent, il valide le jeton JWT.
 * Si la validation échoue ou si l'en-tête est manquant, il renvoie une réponse d'erreur non autorisée.
 */
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final JwtUtil jwtUtil;
    private final RouteValidator validator;

    /**
     * Construit un filtre d'authentification avec JwtUtil et RouteValidator spécifiés.
     *
     * @param jwtUtil    l'utilitaire pour les opérations JWT
     * @param validator  le validateur pour vérifier si une route est sécurisée
     */
    public AuthenticationFilter(JwtUtil jwtUtil, RouteValidator validator) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Applique la logique du filtre.
     * Vérifie si la requête concerne une route sécurisée, valide le jeton JWT et gère les erreurs.
     *
     * @param config l'objet de configuration
     * @return le filtre de passerelle
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return onError(exchange, "Missing authorization header", HttpStatus.UNAUTHORIZED);
                }

                String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }
                try {
                    jwtUtil.validateToken(token);
                } catch (Exception e) {
                    LOGGER.error("Error in parsing token", e);
                    return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        };
    }

    /**
     * Gère les réponses d'erreur en générant une réponse d'erreur avec le message et le statut spécifiés.
     *
     * @param exchange l'échange web serveur
     * @param message  le message d'erreur
     * @param status   le statut HTTP
     * @return un Mono<Void> indiquant quand la réponse d'erreur est complète
     */
    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        return ErrorResponseUtil.generateErrorResponse(exchange.getResponse(), status, message);
    }

    /**
     * Classe de configuration pour AuthenticationFilter.
     */
    public static class Config { }
}
