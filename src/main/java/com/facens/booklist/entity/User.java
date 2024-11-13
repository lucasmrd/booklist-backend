package com.facens.booklist.entity;

import com.facens.booklist.dto.request.AtualizarUserRequest;
import com.facens.booklist.dto.request.CriarUserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    private String id;

    private String email;

    private String nome;

    private String senha;

    private List<Livro> livros = new ArrayList<>();

    public User(CriarUserRequest dto, PasswordEncoder passwordEncoder) {
        this.id = "user::" + UUID.randomUUID();
        this.email = dto.email();
        this.nome = dto.nome();
        this.senha = passwordEncoder.encode(dto.senha());
    }

    public void atualizarUser(AtualizarUserRequest request) {
        if (!request.email().isBlank()) {
            this.email = request.email();
        }

        if (!request.nome().isBlank()) {
            this.nome = request.nome();
        }

        if (!request.senha().isBlank()) {
            this.senha = request.senha();
        }
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public void removerLivro(Livro livro) {
        livros.remove(livro);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
