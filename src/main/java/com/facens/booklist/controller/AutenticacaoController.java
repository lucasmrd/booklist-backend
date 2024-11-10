package com.facens.booklist.controller;

import com.facens.booklist.dto.request.CriarUserRequest;
import com.facens.booklist.dto.response.DadosJWTResponse;
import com.facens.booklist.entity.User;
import com.facens.booklist.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid CriarUserRequest dto) {
        var token = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var authentication = manager.authenticate(token);

        var jwt = tokenService.gerarToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosJWTResponse(jwt));
    }
}
