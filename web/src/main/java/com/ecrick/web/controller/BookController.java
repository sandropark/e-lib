package com.ecrick.web.controller;

import com.ecrick.domain.EcrickConst;
import com.ecrick.domain.dto.BookListDto;
import com.ecrick.domain.dto.Search;
import com.ecrick.domain.service.BookService;
import com.ecrick.domain.service.PaginationService;
import com.ecrick.domain.service.SearchTarget;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/books")
@Controller
public class BookController {
    private final BookService bookService;
    private final PaginationService paginationService;

    @GetMapping
    public String bookList(
            @PageableDefault(size = EcrickConst.PAGE_SIZE, sort = {"publicDate"}, direction = Sort.Direction.DESC) Pageable pageable,
            @ModelAttribute Search search,
            Model model,
            HttpServletRequest request
    ) {
        model.addAttribute("searchTargets", SearchTarget.values());

        Page<BookListDto> books = bookService.searchPage(search, pageable);
        model.addAttribute("books", books);

        // TODO : 모바일 컨트롤러 분리 고민해보기

        String userAgent = request.getHeader("user-agent");

        if (userAgent.contains("Mobile")) {
            model.addAttribute("pagination",
                    paginationService.getMobilePagination(books.getNumber(), books.getTotalPages()));
            return "mobile/list";
        }

        model.addAttribute("pagination",
                paginationService.getDesktopPagination(books.getNumber(), books.getTotalPages()));
        return "desktop/list";
    }

    @GetMapping("/{bookId}")    // TODO : 도서관 / 서비스 보여줄 때 테이블로 보여주기
    public String bookDetail(@PathVariable Long bookId, @ModelAttribute Search search, Model model) {
        model.addAttribute("searchTargets", SearchTarget.values());
        model.addAttribute("book", bookService.getBookDetail(bookId));
        return "book-detail";
    }
}
