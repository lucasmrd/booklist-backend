package com.facens.booklist.controller;

import com.facens.booklist.dto.request.CriarUserRequest;
import com.facens.booklist.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity criar(@RequestBody @Valid CriarUserRequest dto) {
        service.criar(dto);
        return ResponseEntity.ok("a");
    }
}
