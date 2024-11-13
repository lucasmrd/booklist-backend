package com.facens.booklist.entity;

import com.facens.booklist.dto.request.AtualizarLivroRequest;
import com.facens.booklist.dto.request.CriarLivroRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.UUID;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Livro {

    @Id
    private String id;
    private String titulo;
    private String capa;
    private String autor;
    private String sinopse;
    private String userName;
    private Boolean lido;


    public Livro(CriarLivroRequest dto, String userName) {
        this.id = "livro::" + UUID.randomUUID();
        this.titulo = dto.titulo();
        this.capa = dto.capa();
        this.autor = dto.autor();
        this.sinopse = dto.sinopse();
        this.userName = userName;
        this.lido = false;
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
