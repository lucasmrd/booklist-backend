package com.facens.booklist.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CriarUserRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String senha) {
}
