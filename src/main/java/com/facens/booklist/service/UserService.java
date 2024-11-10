package com.facens.booklist.service;

import com.couchbase.client.core.error.UserNotFoundException;
import com.facens.booklist.dto.request.AtualizarUserRequest;
import com.facens.booklist.dto.request.CriarUserRequest;
import com.facens.booklist.dto.response.MostrarUserResponse;
import com.facens.booklist.entity.User;
import com.facens.booklist.infra.exception.EmailAlreadyUsedException;
import com.facens.booklist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity criar(CriarUserRequest dto,
                                UriComponentsBuilder uriBuilder) {

        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new EmailAlreadyUsedException();
        }

        var user = new User(dto, passwordEncoder);

        repository.save(user);
        var response = new MostrarUserResponse(user);

        var uri = uriBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    public ResponseEntity mostrarUserPorId(String id) {
        var user = repository.
                findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado", ""));
        var response = new MostrarUserResponse(user);

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity atualizarUserPorId(AtualizarUserRequest request, String id) {
        var user = repository.
                findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado", ""));
        user.atualizarUser(request);

        repository.deleteById(id);
        repository.save(user);

        return ResponseEntity.ok(new MostrarUserResponse(user));
    }

    public ResponseEntity deletarUserPorID(String id) {
        var user = repository.
                findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado", ""));

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
