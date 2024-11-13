package com.facens.booklist.dto.response;

import com.facens.booklist.entity.Livro;
import org.springframework.web.multipart.MultipartFile;

public record MostrarLivroResponse(
        String id,
        String titulo,
        String capa,
        String autor,
        String sinopse,
        String userName,
        Boolean lido) {

    public MostrarLivroResponse(Livro livro) {
        this(livro.getId(), livro.getTitulo(), livro.getCapa(), livro.getAutor(), livro.getSinopse(), livro.getUserName(), livro.getLido());
    }
}
