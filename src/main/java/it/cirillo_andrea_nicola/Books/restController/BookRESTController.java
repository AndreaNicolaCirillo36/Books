package it.cirillo_andrea_nicola.Books.restController;

import it.cirillo_andrea_nicola.Books.entity.Book;
import it.cirillo_andrea_nicola.Books.exception.BookNotFoundException;
import it.cirillo_andrea_nicola.Books.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRESTController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBook() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(books);

    }

    @GetMapping("{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable Long isbn) {
        Book book = bookService.findByIsbn(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(book);

    }

    @PostMapping("/newBook")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/updateBook/{isbn}")
    public ResponseEntity<Book> updateBook(@PathVariable Long isbn, @Valid @RequestBody Book book) {
        Book updateBook = bookService.updateBook(isbn, book);
        return ResponseEntity.status(HttpStatus.OK).body(updateBook);

    }

    @DeleteMapping("deleteBook/{isbn}")
    public ResponseEntity<String> deleteByIsbn(@PathVariable Long isbn) {
        try {
            bookService.deleteByIsbn(isbn);
            return ResponseEntity.ok("Libro con ISBN: " + isbn + " eliminato con successo");
        }catch (BookNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFoundException(BookNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno del server");
    }
}
