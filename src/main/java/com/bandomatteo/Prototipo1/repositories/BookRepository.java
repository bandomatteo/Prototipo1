package com.bandomatteo.Prototipo1.repositories;

import com.bandomatteo.Prototipo1.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String> {
}
