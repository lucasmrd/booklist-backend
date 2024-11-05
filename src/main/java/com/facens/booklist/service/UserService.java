package com.facens.booklist.service;

import com.facens.booklist.dto.request.CriarUserRequest;
import com.facens.booklist.entity.User;
import com.facens.booklist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public ResponseEntity criar(CriarUserRequest dto) {
        var user = new User(dto);
        repository.save(user);
        return ResponseEntity.ok("a");
    }
}
