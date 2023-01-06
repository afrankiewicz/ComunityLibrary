package com.agular.hello.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "books")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "ISNB must be provided")
    @NonNull
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @NotBlank(message = "Book title must be provided")
    @NonNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "Book author must be provided")
    @NonNull
    @Column(name = "author", nullable = false)
    private String author;

    @NotBlank(message = "Book language must be provided")
    @NonNull
    @Column(name = "language", nullable = false)
    private String language;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "return_date")
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "borrower_id", referencedColumnName = "id")
    private User borrower;

}
