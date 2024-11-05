package com.facens.booklist.entity;

import com.facens.booklist.dto.request.AtualizarUserRequest;
import com.facens.booklist.dto.request.CriarUserRequest;
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
public class User {

    @Id
    private String id;

    private String email;

    private String senha;

    public User(CriarUserRequest dto) {
        this.id = "user::" + UUID.randomUUID();
        this.email = dto.email();
        this.senha = dto.senha();
    }

    public void atualizarUser(AtualizarUserRequest request) {
        if (!request.email().isBlank()) {
            this.email = request.email();
        }

        if (!request.senha().isBlank()) {
            this.senha = request.senha();
        }
    }
}
