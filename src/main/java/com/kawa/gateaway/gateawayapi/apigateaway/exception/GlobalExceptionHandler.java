package com.kawa.gateaway.gateawayapi.apigateaway.exception;

import com.kawa.gateaway.gateawayapi.apigateaway.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * Gestionnaire global des exceptions pour gérer les exceptions dans un environnement réactif.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Gère l'exception ResourceNotFoundException.
     *
     * @param ex L'instance de ResourceNotFoundException
     * @return Un Mono contenant ResponseEntity avec un message d'erreur et un statut HTTP 404
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<ApiResponse<String>>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        logger.error("ResourceNotFoundException: {}", ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null);
        return Mono.just(new ResponseEntity<>(response, HttpStatus.NOT_FOUND));
    }

    /**
     * Gère l'exception IllegalArgumentException.
     *
     * @param ex L'instance de IllegalArgumentException
     * @return Un Mono contenant ResponseEntity avec un message d'erreur et un statut HTTP 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ApiResponse<String>>> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException: {}", ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null);
        return Mono.just(new ResponseEntity<>(response, HttpStatus.BAD_REQUEST));
    }

    /**
     * Gère toutes les autres exceptions.
     *
     * @param ex L'instance de l'Exception
     * @return Un Mono contenant ResponseEntity avec un message d'erreur et un statut HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ApiResponse<String>>> handleGlobalException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage(), ex);
        ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null);
        return Mono.just(new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
