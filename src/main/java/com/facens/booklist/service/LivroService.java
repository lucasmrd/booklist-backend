package com.facens.booklist.service;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.core.error.UserNotFoundException;
import com.couchbase.client.core.error.context.ErrorContext;
import com.facens.booklist.dto.request.AtualizarLivroRequest;
import com.facens.booklist.dto.request.CriarLivroRequest;
import com.facens.booklist.dto.response.MostrarLivroResponse;
import com.facens.booklist.entity.Livro;
import com.facens.booklist.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    @Transactional
    public ResponseEntity criarLivro(CriarLivroRequest dto,
                                     UriComponentsBuilder uriBuilder) {
        var livro = new Livro(dto);
        repository.save(livro);
        var response = new MostrarLivroResponse(livro);

        var uri = uriBuilder.path("/api/user/{id}").buildAndExpand(livro.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    public ResponseEntity mostrarLivroPorId(String id) {
        var livro = repository.
                findById(id).orElseThrow(() -> new UserNotFoundException("Livro não encontrado", ""));
        var response = new MostrarLivroResponse(livro);

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<Page<MostrarLivroResponse>> MostrarTodosOsLivros(Pageable pageable) throws DocumentNotFoundException {
        var page = repository.findAll(pageable).map(MostrarLivroResponse::new);

        return ResponseEntity.ok(page);
    }

    public ResponseEntity atualizarLivroPorId(AtualizarLivroRequest request, String id) {
        var livro = repository.
                findById(id).orElseThrow(() -> new UserNotFoundException("Livro não encontrado", ""));
        livro.atualizarLivro(request);

        repository.deleteById(id);
        repository.save(livro);

        return ResponseEntity.ok(new MostrarLivroResponse(livro));
    }

    public ResponseEntity deletarLivroPorID(String id) {
        var livro = repository.
                findById(id).orElseThrow(() -> new UserNotFoundException("Livro não encontrado", ""));

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
