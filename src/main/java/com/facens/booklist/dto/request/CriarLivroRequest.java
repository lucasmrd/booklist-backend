package com.facens.booklist.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CriarLivroRequest(
        @NotBlank
        String titulo,

        @NotBlank
        String capa,

        @NotBlank
        String autor,

        String sinopse) {
}
