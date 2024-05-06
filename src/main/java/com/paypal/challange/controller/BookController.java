package com.paypal.challange.controller;


import com.paypal.challange.entity.Book;
import com.paypal.challange.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping(value= "")
    @ResponseBody
    public Book createBook(@RequestBody Book bookInstance) {
        return this.bookService.createBook(bookInstance);
    }

    @GetMapping(value= "/{id}")
    @ResponseBody
    public ResponseEntity<?> getBook(@PathVariable String id) {
        Book book =  this.bookService.getBook(Long.parseLong(id));
        if (book == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(book);
        }
    }

    @DeleteMapping(value= "/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteBook(@PathVariable String id) {
        if (this.bookService.deleteBook(Long.parseLong(id))) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value= "/{id}")
    @ResponseBody
    public ResponseEntity<?> updateBook(@RequestBody Book bookInstance, @PathVariable String id) {
        try {
            Book book = this.bookService.updateBook(bookInstance, Long.parseLong(id));
            return ResponseEntity.ok(book);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
