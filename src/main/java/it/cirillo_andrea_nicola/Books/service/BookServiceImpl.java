package it.cirillo_andrea_nicola.Books.service;

import it.cirillo_andrea_nicola.Books.entity.Book;
import it.cirillo_andrea_nicola.Books.exception.BookNotFoundException;
import it.cirillo_andrea_nicola.Books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book findByIsbn(Long isbn) {
        Book theBook = bookRepository.findByIsbn(isbn);
        if (theBook == null){
            throw new BookNotFoundException("Libro con ISBN " + isbn + " non trovato");
        }
        return theBook;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        if (books.isEmpty()){
            throw new BookNotFoundException("Nessun libro con autore " + author);
        }
        return books;
    }


    @Override
    public List<Book> findAll() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long isbn, Book book) {
        Book existingBook = bookRepository.findByIsbn(isbn);
        if (existingBook == null){
            throw new BookNotFoundException("Non esiste alcun libro con isbn: " + isbn);
        }
        existingBook.setName(book.getName());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPublisher(book.getPublisher());
        if (book.getIsbn() != existingBook.getIsbn()) {
            existingBook.setIsbn(book.getIsbn());
        }

        return bookRepository.save(existingBook);
    }


    @Transactional
    @Override
    public void deleteByIsbn(Long isbn) {
        Book tempBook = bookRepository.findByIsbn(isbn);
        if (tempBook == null) {
            throw new BookNotFoundException("Libro non trovato");
        } else {
            bookRepository.deleteByIsbn(isbn);
        }
    }


}
