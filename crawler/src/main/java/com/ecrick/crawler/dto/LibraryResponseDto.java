package com.ecrick.crawler.dto;

import com.ecrick.core.domain.ContentType;
import com.ecrick.core.dto.LibraryDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LibraryResponseDto {
    private Long id;
    private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    private ContentType contentType;
    private String vendor;
    private Integer size;

    public static LibraryResponseDto from(LibraryDto dto) {
        return new LibraryResponseDto(
                dto.getId(),
                dto.getName(),
                dto.getUrl(),
                dto.getParam(),
                dto.getTotalBooks(),
                dto.getSavedBooks(),
                dto.getContentType(),
                dto.getVendor().getName(),
                dto.getSize()
        );
    }
}
