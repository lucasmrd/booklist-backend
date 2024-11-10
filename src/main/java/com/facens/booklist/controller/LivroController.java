package com.facens.booklist.controller;

import com.facens.booklist.dto.request.AtualizarLivroRequest;
import com.facens.booklist.dto.request.CriarLivroRequest;
import com.facens.booklist.dto.response.MostrarLivroResponse;
import com.facens.booklist.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/livro")
public class LivroController {

    @Autowired
    private LivroService service;

    @PostMapping
    public ResponseEntity criarLivro(@RequestBody @Valid CriarLivroRequest dto,
                                    @RequestHeader("Authorization") String token,
                                    UriComponentsBuilder uriBuilder) {
        return service.criarLivro(dto, token, uriBuilder);
    }

    @GetMapping("/{id}")
    public ResponseEntity mostrarLivroPorId(@PathVariable String id) {
        return service.mostrarLivroPorId(id);
    }

    @GetMapping
    public ResponseEntity<Page<MostrarLivroResponse>> MostrarTodosOsLivros(@PageableDefault Pageable pageable) {
        return service.MostrarTodosOsLivros(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizarLivroPorId(@RequestBody @Valid AtualizarLivroRequest request,
                                             @PathVariable String id) {
        return service.atualizarLivroPorId(request, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarLivroPorId(@PathVariable String id,
                                            @RequestHeader("Authorization") String token) {
        return service.deletarLivroPorID(id, token);
    }
}
