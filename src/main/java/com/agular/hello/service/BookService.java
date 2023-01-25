package com.agular.hello.service;

import com.agular.hello.DTO.AvailableBook;
import com.agular.hello.DTO.BookDto;
import com.agular.hello.DTO.UserDto;
import com.agular.hello.entity.BookModel;
import com.agular.hello.exceptions.BadRequestException;
import com.agular.hello.repositiry.BookRepository;
import com.agular.hello.repositiry.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    BookRepository bookRepository;

    UserRepository userRepository;

    UserService userService;

    public BookService(BookRepository bookRepository, UserRepository userRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public BookDto registerBook(BookDto book, String email) {
        book.setOwner(userService.getUserByEmail(email));
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BadRequestException(String.format("Book with ISBN: '%s' already exists in library.", book.getIsbn()));
        }
        return bookRepository.save(book.toModel()).toDto();
    }

    public List<BookDto> getOwned(String email) {
        return bookRepository.getBooksByOwnerEmail(email)
                .stream()
                .map(BookModel::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> getBorrowed(String email) {
        return bookRepository.getBooksByBorrowerEmail(email)
                .stream()
                .map(BookModel::toDto)
                .collect(Collectors.toList());
    }

    public List<AvailableBook> getAllAvailable(String email) {
        UserDto borrower = userService.getUserByEmail(email);
        List<BookModel> books = bookRepository.getAllAvailable(borrower.getId());

        return books
                .stream()
                .map(book -> {
                    UserDto owner = book.getOwner().toDto();
                    double distance = distance(borrower.getCityLatitude(), owner.getCityLatitude(),
                            borrower.getCityLongitude(), owner.getCityLongitude());
                    return new AvailableBook(book.toDto(), distance);
                })
                .collect(Collectors.toList());
    }

    private double distance(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000;

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    private BookDto getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BadRequestException("Book does not exist.")).toDto();
    }

    public BookDto borrowBook(Long bookId, String email) {
        BookDto book = getBookById(bookId);
        if (book.getOwner() == null) {
            throw new IllegalStateException();
        } else if (book.getBorrower() != null) {
            throw new BadRequestException("Book is already borrowed.");
        } else if (book.getOwner().getEmail().equals(email)) {
            throw new BadRequestException("Book belongs to you.");
        }
        book.setBorrower(userService.getUserByEmail(email));
        book.setReturnDate(LocalDate.now().plusMonths(1));
        return bookRepository.save(book.toModel()).toDto();
    }

    public BookDto returnBook(Long bookId, String email) {
        BookDto book = getBookById(bookId);
        if (book.getBorrower() == null || !book.getBorrower().getEmail().equals(email)) {
            throw new BadRequestException("Book is not borrowed by you.");
        }
        book.setBorrower(null);
        book.setReturnDate(null);
        return bookRepository.save(book.toModel()).toDto();
    }
}