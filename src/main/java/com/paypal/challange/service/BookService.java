package com.paypal.challange.service;


import com.paypal.challange.entity.Book;
import com.paypal.challange.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Book createBook(Book bookInstance) {
        return this.bookRepository.save(bookInstance);
    }

    public boolean deleteBook(Long id) {
        Book book = this.bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }
        this.bookRepository.deleteById(id);
        return true;
    }

    public Book updateBook(Book bookInstance, Long id) {
        // find the book by id
        Book book = this.bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }
        // update the book
        if(bookInstance.getAuthor() != null)
            book.setAuthor(bookInstance.getAuthor());
        if(bookInstance.getTitle() != null)
            book.setTitle(bookInstance.getTitle());
        if(bookInstance.getAvailableCopies() != null)
            book.setAvailableCopies(bookInstance.getAvailableCopies());
        if(bookInstance.getPublicationYear() != null)
            book.setPublicationYear(bookInstance.getPublicationYear());
        return this.bookRepository.save(book);
    }

    public Book getBook(Long id) {
        return this.bookRepository.findById(id).orElse(null);
    }

}
