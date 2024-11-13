package com.facens.booklist.dto.response;

import com.facens.booklist.entity.User;

public record MostrarUserResponse(
        String id,
        String email,
        String nome) {

    public MostrarUserResponse(User user) {
        this(user.getId(), user.getEmail(), user.getNome());
    }
}
