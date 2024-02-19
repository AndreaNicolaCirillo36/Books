package it.cirillo_andrea_nicola.Books.mvcController;

import it.cirillo_andrea_nicola.Books.entity.Book;
import it.cirillo_andrea_nicola.Books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web")
@RequiredArgsConstructor
public class BookMVCController {

    private final BookService bookService;


    @GetMapping("/home")
    public String listBooks(Model theModel){

        List<Book> theBooks = bookService.findAll();

        theModel.addAttribute("books", theBooks);

        return "books/home";
    }

    @GetMapping("/search")
    public String searchByIsbn(@RequestParam(value = "isbn", required = false) Long isbn, Model model) {
        if (isbn == null) {
            return "redirect:/web/home";
        }

        Book books = bookService.findByIsbn(isbn);

        model.addAttribute("books", books);
        return "books/home";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){

        Book theBook = new Book();

        theModel.addAttribute("book", theBook);

        return "books/add-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("isbn") Long isbn, Model theModel){
        Book book = bookService.findByIsbn(isbn);

        theModel.addAttribute("book", book);

        return "books/update-form";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") Book theBook){

        bookService.saveBook(theBook);

        return "redirect:/web/home";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("isbn") Long isbn){

        bookService.deleteByIsbn(isbn);

        return "redirect:/web/home";

    }
}
