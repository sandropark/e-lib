package com.ecrick.domain.repository;

import com.ecrick.domain.entity.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    @Override
    @EntityGraph(attributePaths = {"cores"})
    Optional<Book> findById(Long bookId);

}
