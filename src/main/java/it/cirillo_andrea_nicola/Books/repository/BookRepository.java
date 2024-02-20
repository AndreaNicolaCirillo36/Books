package it.cirillo_andrea_nicola.Books.repository;


import it.cirillo_andrea_nicola.Books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByIsbn(Long isbn);
    List<Book> findByAuthor(String author);
    void deleteByIsbn(Long isbn);
}