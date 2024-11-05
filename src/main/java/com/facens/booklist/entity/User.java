package com.facens.booklist.entity;

import com.facens.booklist.dto.request.CriarUserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

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
        this.email = dto.email();
        this.senha = dto.senha();
    }
}
