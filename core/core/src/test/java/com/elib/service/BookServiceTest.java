package com.elib.service;

import com.elib.domain.Book;
import com.elib.domain.EbookService;
import com.elib.domain.Library;
import com.elib.domain.Relation;
import com.elib.dto.BookDetailDto;
import com.elib.dto.LibraryEbookServiceDto;
import com.elib.repository.BookRepository;
import com.elib.repository.EbookServiceRepository;
import com.elib.repository.LibraryRepository;
import com.elib.repository.RelationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
class BookServiceTest {

    BookService bookService;
    @Autowired BookRepository bookRepository;
    @Autowired LibraryRepository libraryRepository;
    @Autowired EbookServiceRepository ebookServiceRepository;
    @Autowired RelationRepository relationRepository;
    @Autowired EntityManager em;

    Book book;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository);

        book = Book.of("사피엔스");
        bookRepository.save(book);

        Library library = Library.of("산들도서관");
        libraryRepository.save(library);

        EbookService ebookService = EbookService.of("교보");
        ebookServiceRepository.save(ebookService);

        Relation relation = Relation.of(book, library, ebookService);
        relationRepository.save(relation);
    }

    @Test
    void findBookDetail() throws Exception {
        em.clear();

        BookDetailDto bookDetail = bookService.getBookDetail(book.getId());
        List<LibraryEbookServiceDto> location = bookDetail.getLocation();
        for (LibraryEbookServiceDto libraryEbookServiceDto : location) {
            System.out.println("libraryEbookServiceDto.getEbookService() = " + libraryEbookServiceDto.getEbookService());
            System.out.println("libraryEbookServiceDto.getLibrary() = " + libraryEbookServiceDto.getLibrary());
        }
    }

}