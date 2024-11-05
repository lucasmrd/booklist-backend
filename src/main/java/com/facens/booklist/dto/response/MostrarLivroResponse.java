package com.facens.booklist.dto.response;

import com.facens.booklist.entity.Livro;

public record MostrarLivroResponse(
        String id,
        String titulo,
        String capa,
        String autor,
        String sinopse) {

    public MostrarLivroResponse(Livro livro) {
        this(livro.getId(), livro.getTitulo(), livro.getCapa(), livro.getAutor(), livro.getSinopse());
    }
}
