package com.facens.booklist.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LogarUserRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @JsonAlias("password")
        String senha) {
}
