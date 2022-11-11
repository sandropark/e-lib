package sandro.elib.elib.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"title", "author", "publisher"}
        )
})
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "book_id")
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDateTime publicDate;
    private String imageUrl;
    @OneToMany(mappedBy = "book")
    private final List<Relation> relations = new ArrayList<>();

    private Book(String title, String author, String publisher, LocalDateTime publicDate, String imageUrl) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicDate = publicDate;
        this.imageUrl = imageUrl;
    }

    public static Book of(String title, String author, String publisher, LocalDateTime publicDate, String imageUrl) {
        return new Book(title, author, publisher, publicDate, imageUrl);
    }

    public void updatePublicDate(LocalDateTime publicDate) {
        this.publicDate = publicDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getPublisher(), book.getPublisher());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getAuthor(), getPublisher());
    }
}
