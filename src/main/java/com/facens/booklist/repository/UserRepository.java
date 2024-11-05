package com.facens.booklist.repository;

import com.facens.booklist.entity.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface UserRepository extends CouchbaseRepository<User, String> {
}
