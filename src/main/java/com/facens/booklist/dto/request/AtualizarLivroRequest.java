package com.facens.booklist.dto.request;

public record AtualizarLivroRequest(
        String titulo,
        String capa,
        String autor,
        String sinopse) {
}
