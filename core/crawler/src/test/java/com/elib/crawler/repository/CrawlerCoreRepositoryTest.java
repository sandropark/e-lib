package com.elib.crawler.repository;

import com.elib.domain.Book;
import com.elib.domain.Core;
import com.elib.repository.BookRepository;
import com.elib.repository.CoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CrawlerCoreRepositoryTest {

    @Autowired CoreRepository coreRepository;
    @Autowired BookRepository bookRepository;
    @Autowired
    CrawlerCoreRepository crawlerCoreRepository;
    @Autowired EntityManager em;

    @DisplayName("제목,저자,출판사 모두 같은 core와 book끼리 연관관계를 맺는다.")
    @Test
    void mapCoreAndBookIfCore_BookIdIsNull() throws Exception {
        // Given
        String title = "토지";
        String author = "박경리";
        String publisher = "김영사";
        Core core = saveCore(title, author, publisher);
        Book book = saveBook(title, author, publisher);

        assertThat(core.getBook()).isNull();

        // When
        crawlerCoreRepository.mapCoreAndBookIfCore_BookIdIsNull();
        em.clear();

        // Then
        Core findCore = coreRepository.findById(core.getId()).get();
        assertThat(findCore.getBook()).isEqualTo(book);
    }

    @DisplayName("제목,저자,출판사 중 하나라도 맞지 않다면 연관관계를 맺지 않는다.")
    @Test
    void mapCoreAndBookIfCore_BookIdIsNull2() throws Exception {
        // Given
        String title = "토지";
        String author = "박경리";
        Core core = saveCore(title, author, "김영사");
        saveBook(title, author, "출판사 김영사");

        assertThat(core.getBook()).isNull();

        // When
        crawlerCoreRepository.mapCoreAndBookIfCore_BookIdIsNull();
        em.clear();

        // Then
        Core findCore = coreRepository.findById(core.getId()).get();
        assertThat(findCore.getBook()).isNull();
    }

    private Book saveBook(String title, String author, String publisher) {
        return bookRepository.saveAndFlush(Book.builder().title(title).author(author).publisher(publisher).build());
    }

    private Core saveCore(String title, String author, String publisher) {
        return coreRepository.saveAndFlush(Core.builder().title(title).author(author).publisher(publisher).build());
    }

}