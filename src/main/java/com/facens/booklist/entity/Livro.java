package com.facens.booklist.entity;

import com.facens.booklist.dto.request.AtualizarLivroRequest;
import com.facens.booklist.dto.request.CriarLivroRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.UUID;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livro {

    @Id
    private String id;
    private String titulo;
    private String capa;
    private String autor;
    private String sinopse;

    public Livro(CriarLivroRequest dto) {
        this.id = "livro::" + UUID.randomUUID();
        this.titulo = dto.titulo();
        this.capa = dto.capa();
        this.autor = dto.autor();
        this.sinopse = dto.sinopse();
    }

    public void atualizarLivro(AtualizarLivroRequest request) {
        if (!request.titulo().isBlank()) {
            this.titulo = request.titulo();
        }

        if (!request.capa().isBlank()) {
            this.capa = request.capa();
        }

        if (!request.autor().isBlank()) {
            this.autor = request.autor();
        }

        if (!request.sinopse().isBlank()) {
            this.sinopse = request.sinopse();
        }
    }
}
