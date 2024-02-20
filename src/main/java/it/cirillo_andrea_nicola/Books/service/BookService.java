package it.cirillo_andrea_nicola.Books.service;

import it.cirillo_andrea_nicola.Books.entity.Book;

import java.util.List;

public interface BookService {
    Book findByIsbn(Long isbn);
    List<Book> findByAuthor(String author);
    List<Book> findAll();
    Book saveBook(Book book);
    Book updateBook(Long isbn, Book book);
    void deleteByIsbn(Long isbn);
}
