package com.facens.booklist.dto.request;

import jakarta.validation.constraints.Email;

public record AtualizarUserRequest(
        @Email
        String email,
        String senha) {
}
