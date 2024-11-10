package com.facens.booklist.repository;

import com.facens.booklist.entity.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CouchbaseRepository<User, String> {
    Optional<User> findByEmail(String email);
}
