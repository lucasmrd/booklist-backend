package com.facens.booklist.repository;

import com.facens.booklist.entity.Livro;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends CouchbaseRepository<Livro, String> {
}
