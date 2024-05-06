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

        @Test
        void testBookUpdate_withAllValidParameters_shouldUpdate() {
            // Arrange
            Book book = new Book();
            book.setId(1L);
            book.setAuthor("Author");
            book.setTitle("Title");
            book.setAvailableCopies(10);
            book.setPublicationYear(2021);
            Mockito.when(bookRepository.findById(Mockito.eq(1L))).thenReturn(java.util.Optional.of(book));

            Book updatedBook = new Book();
            updatedBook.setAuthor("Author2");
            updatedBook.setTitle("Title2");
            updatedBook.setAvailableCopies(20);
            updatedBook.setPublicationYear(2022);
            Mockito.when(bookRepository.save(Mockito.any())).thenReturn(updatedBook);

            // Act
            Book updated = bookService.updateBook(updatedBook, 1L);

            // Assert
            Assertions.assertNotNull(updated);
            Assertions.assertEquals(updatedBook.getAuthor(), updated.getAuthor());
            Assertions.assertEquals(updatedBook.getTitle(), updated.getTitle());
            Assertions.assertEquals(updatedBook.getAvailableCopies(), updated.getAvailableCopies());
            Assertions.assertEquals(updatedBook.getPublicationYear(), updated.getPublicationYear());

        }

        @Test

        void getBook_withExistingBook_shouldReturnBook() {
            // Arrange
            Book book = new Book();
            book.setId(1L);
            book.setAuthor("Author");
            book.setTitle("Title");
            book.setAvailableCopies(10);
            book.setPublicationYear(2021);
            Mockito.when(bookRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(book));
            // Act
            Book foundBook = this.bookService.getBook(1L);
            // Assert
            Assertions.assertNotNull(foundBook);
        }
}
