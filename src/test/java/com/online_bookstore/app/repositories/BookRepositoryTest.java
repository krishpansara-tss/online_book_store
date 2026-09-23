package com.online_bookstore.app.repositories;

import com.online_bookstore.app.models.Author;
import com.online_bookstore.app.models.Book;
import com.online_bookstore.app.models.Category;
import com.online_bookstore.app.models.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void findBookDetailsById() {
        Category category = new Category();
        category.setName("Programming");
        category = categoryRepository.save(category);

        Publisher publisher = new Publisher();
        publisher.setName("O'Reilly");
        publisher = publisherRepository.save(publisher);

        Author author1 = new Author();
        author1.setName("Author One");
        author1 = authorRepository.save(author1);

        Author author2 = new Author();
        author2.setName("Author Two");
        author2 = authorRepository.save(author2);

        Book book = new Book();
        book.setTitle("Spring Boot Guide");
        book.setCategory(category);
        book.setPublisher(publisher);
        book.setAuthors(List.of(author1, author2));

        book = bookRepository.save(book);

        Book fetchedBook = bookRepository.findBookDetailsById(book.getBookId());

        assertNotNull(fetchedBook);
        assertEquals("Spring Boot Guide", fetchedBook.getTitle());

        assertNotNull(fetchedBook.getCategory());
        assertEquals("Programming", fetchedBook.getCategory().getName());

        assertNotNull(fetchedBook.getPublisher());
        assertEquals("O'Reilly", fetchedBook.getPublisher().getName());

        assertNotNull(fetchedBook.getAuthors());
        assertEquals(2, fetchedBook.getAuthors().size());
    }

//    @Test
//    void getAllBookBasicInformation() {
//    }
//
//    @Test
//    void getAllActiveBookBasicInformation() {
//    }
}