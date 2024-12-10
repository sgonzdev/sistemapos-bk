package com.SistemPos.sistemapos.dto.request;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "jwt", "success"})
public record AuthRequest(
    String username,
    String message,
    String jwt,
    boolean success
) {
}
