package com.facens.booklist.service;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.core.error.UserNotFoundException;
import com.facens.booklist.dto.request.AtualizarLivroLidoRequest;
import com.facens.booklist.dto.request.AtualizarLivroRequest;
import com.facens.booklist.dto.request.CriarLivroRequest;
import com.facens.booklist.dto.response.MostrarLivroResponse;
import com.facens.booklist.entity.Livro;
import com.facens.booklist.repository.LivroRepository;
import com.facens.booklist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity criarLivro(CriarLivroRequest dto,
                                     String token,
                                     UriComponentsBuilder uriBuilder) {

        String userEmail = tokenService.getSubject(token.replace("Bearer ", ""));
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado", ""));

        var livro = new Livro(dto, user.getNome());
        user.adicionarLivro(livro);
        userRepository.deleteById(user.getId());
        userRepository.save(user);

        livroRepository.save(livro);




        var response = new MostrarLivroResponse(livro);

        var uri = uriBuilder.path("/api/user/{id}").buildAndExpand(livro.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    public ResponseEntity mostrarLivroPorId(String id) {
        var livro = livroRepository.
                findById(id).orElseThrow(() -> new UserNotFoundException("Livro não encontrado", ""));
        var response = new MostrarLivroResponse(livro);

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<Page<MostrarLivroResponse>> mostrarTodosOsLivros(Pageable pageable, String token) throws DocumentNotFoundException {
        String userEmail = tokenService.getSubject(token.replace("Bearer ", ""));
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado", ""));

        var livrosDoUserIds = user.getLivros().stream()
                .map(Livro::getId)
                .collect(Collectors.toSet());

        var page = livroRepository.findAll(pageable)
                .map(MostrarLivroResponse::new);

        var livrosDeOutrosUsuarios = page.getContent().stream()
                .filter(livro -> !livrosDoUserIds.contains(livro.id()))
                .collect(Collectors.toList());

        var filteredPage = new PageImpl<>(livrosDeOutrosUsuarios, pageable, livrosDeOutrosUsuarios.size());

        return ResponseEntity.ok(filteredPage);
    }

    public ResponseEntity<Page<MostrarLivroResponse>> mostrarTodosOsLivrosDoUser(Pageable pageable, String token) {
        String userEmail = tokenService.getSubject(token.replace("Bearer ", ""));
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado", ""));

        List<MostrarLivroResponse> livrosResponse = user.getLivros().stream()
                .map(MostrarLivroResponse::new)
                .toList();

        Page<MostrarLivroResponse> page = new PageImpl<>(livrosResponse, pageable, livrosResponse.size());

        return ResponseEntity.ok(page);
    }

    public ResponseEntity<Page<MostrarLivroResponse>> buscarLivrosPorTitulo(Pageable pageable, String titulo) {
        titulo.toLowerCase();

        //Page<Livro> livros = repository.findByTituloContaining(titulo, pageable);

        //Page<MostrarLivroResponse> livrosResponse = livros.map(MostrarLivroResponse::new);

        //return ResponseEntity.ok(livrosResponse);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity atualizarLivroPorId(AtualizarLivroRequest request, String id, String token) {
        String userEmail = tokenService.getSubject(token.replace("Bearer ", ""));
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado", ""));

        var livro = livroRepository.
                findById(id).orElseThrow(() -> new UserNotFoundException("Livro não encontrado", ""));

        user.removerLivro(livro);
        livro.atualizarLivro(request);
        user.adicionarLivro(livro);



        userRepository.deleteById(user.getId());
        userRepository.save(user);

        livroRepository.deleteById(id);
        livroRepository.save(livro);

        return ResponseEntity.ok(new MostrarLivroResponse(livro));
    }

    public ResponseEntity atualizarLivroLido(AtualizarLivroLidoRequest request, String id) {
        var livro = livroRepository.
                findById(id).orElseThrow(() -> new UserNotFoundException("Livro não encontrado", ""));

        if (request.lido()) {
            livro.setLido(true);
        }

        if (!request.lido()) {
            livro.setLido(false);
        }

        livroRepository.deleteById(id);
        livroRepository.save(livro);

        return ResponseEntity.ok(new MostrarLivroResponse(livro));
    }

    public ResponseEntity deletarLivroPorID(String id, String token) {
        String userEmail = tokenService.getSubject(token.replace("Bearer ", ""));
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado", ""));

        var livro = livroRepository.
                findById(id).orElseThrow(() -> new UserNotFoundException("Livro não encontrado", ""));

        user.removerLivro(livro);
        userRepository.deleteById(user.getId());
        userRepository.save(user);

        livroRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
