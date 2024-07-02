package com.kawa.gateaway.gateawayapi.apigateaway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Un wrapper générique de réponse API qui inclut un indicateur de succès, un message et des données.
 *
 * @param <T> le type des données contenues dans la réponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
}
