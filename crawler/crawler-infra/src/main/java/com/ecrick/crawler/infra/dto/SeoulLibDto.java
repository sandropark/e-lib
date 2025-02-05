package com.ecrick.crawler.infra.dto;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.domain.dto.CoreDto;
import com.ecrick.domain.entity.Library;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeoulLibDto implements ResponseDto {

    private Integer totalPage;
    private Integer totalCount;
    @JsonProperty("ContentDataList")
    private List<Content> contents;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Content {
        @JsonProperty("title")
        private String title;
        @JsonProperty("author")
        private String author;
        @JsonProperty("publisher")
        private String publisher;
        @JsonProperty("publishDate")
        private String publishDate;
        @JsonProperty("coverUrl")
        private String coverUrl;
        @JsonProperty("contentsInfo")
        private String contentsInfo;
        @JsonProperty("isbn")
        private String isbn;
        @JsonProperty("ownerCode")
        private String vendor;
    }

    private String getCategory(Library library) {
        return library.getName()
                .replaceAll("서울도서관\\(", "")
                .replaceAll("\\)$", "");
    }

    @Override
    public Integer getTotalBooks() {
        return totalCount;
    }

    @Override
    public List<CoreDto> toCoreDtos(Library library) {
        List<CoreDto> dtos = new ArrayList<>();

        for (Content content : contents) {
            dtos.add(CoreDto.builder()
                    .library(library)
                    .title(content.getTitle())
                    .author(content.getAuthor())
                    .publisher(content.getPublisher())
                    .publicDate(content.getPublishDate())
                    .coverUrl(content.getCoverUrl())
                    .category(getCategory(library))
                    .vendor(content.getVendor())
                    .build());
        }

        return dtos;
    }

}
