package com.facens.booklist.controller;

import com.facens.booklist.dto.request.AtualizarUserRequest;
import com.facens.booklist.dto.request.CriarUserRequest;
import com.facens.booklist.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity criarUser(@RequestBody @Valid CriarUserRequest dto,
                                UriComponentsBuilder uriBuilder) {
        return service.criar(dto, uriBuilder);
    }

    @GetMapping("/{id}")
    public ResponseEntity mostrarUserPorId(@PathVariable String id) {
        return service.mostrarUserPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizarUserPorId(@RequestBody @Valid AtualizarUserRequest request,
                                             @PathVariable String id) {
        return service.atualizarUserPorId(request, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarUserPorId(@PathVariable String id) {
        return service.deletarUserPorID(id);
    }
}
