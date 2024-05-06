package com.paypal.challange;

import com.paypal.challange.entity.Book;
import com.paypal.challange.repository.BookRepository;
import com.paypal.challange.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class BookServiceTest {
        // inject mocks
        @InjectMocks
        private BookService bookService;

        @Mock
        private BookRepository bookRepository;


        @Test
        void testBookDelete_withExistingBook_shouldDeleteFromDatabase() {
            // Arrange
            Book book = new Book();
            book.setId(1L);
            book.setAuthor("Author");
            book.setTitle("Title");
            book.setAvailableCopies(10);
            book.setPublicationYear(2021);
            Mockito.when(bookRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(book));
            // Act
            boolean isDeleted = this.bookService.deleteBook(1L);
            // Assert
            Assertions.assertTrue(isDeleted);
        }

        @Test
        void testBookDelete_withNonExistingBook_shouldThrowException()  {
            // Arrange
            Mockito.when(bookRepository.findById(Mockito.any())).thenReturn(java.util.Optional.empty());
            // Act and Assert
            Assertions.assertThrows(RuntimeException.class, ()-> this.bookService.deleteBook(1L));
        }


        @Test
        void testBookCreation_withAllValidParameters_shouldAddToDatabase() {
            // Arrange
            Book book = new Book();
            book.setAuthor("Author");
            book.setTitle("Title");
            book.setAvailableCopies(10);
            book.setPublicationYear(2021);
            Mockito.when(bookRepository.save(Mockito.any())).thenReturn(book);

            // Act
            Book createdBook = bookService.createBook(book);

            // Assert
            Assertions.assertNotNull(createdBook);
            Assertions.assertEquals(book.getAuthor(), createdBook.getAuthor());
            Assertions.assertEquals(book.getTitle(), createdBook.getTitle());
            Assertions.assertEquals(book.getAvailableCopies(), createdBook.getAvailableCopies());
            Assertions.assertEquals(book.getPublicationYear(), createdBook.getPublicationYear());
        }
}
