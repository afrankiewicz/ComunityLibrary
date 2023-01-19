package com.agular.hello;

import com.agular.hello.DTO.BookDto;
import com.agular.hello.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest extends CommunityLibraryApplicationTests {

    @Autowired
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = registeredUserEmail)
    public void shouldReturnOwnedBooks() throws Exception {
        createRegisteredUser(registeredUserEmail);
        BookDto book = bookService.registerBook(createBook(), registeredUserEmail);

        mockMvc.perform(get("/books/owned"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$[0].owner.email").value(registeredUserEmail));
    }

    @Test
    @WithMockUser(username = registeredUserEmail)
    public void shouldReturnBorrowedBooks() throws Exception {
        BookDto book = bookService.registerBook(createBook(), createRegisteredUser().getEmail());
        createRegisteredUser(registeredUserEmail);
        BookDto borrowedBook = bookService.borrowBook(book.getId(), registeredUserEmail);

        mockMvc.perform(get("/books/borrowed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].isbn").value(borrowedBook.getIsbn()));
    }

    @Test
    @WithMockUser(username = registeredUserEmail)
    public void shouldReturnAllAvailableBooks() throws Exception {
        createRegisteredUser(registeredUserEmail);
        for (int i = 0; i < 5; i++) {
            bookService.registerBook(createBook(), createRegisteredUser().getEmail());
        }
        BookDto book = bookService.registerBook(createBook(), createRegisteredUser().getEmail());
        bookService.borrowBook(book.getId(), createRegisteredUser().getEmail());

        mockMvc.perform(get("/books/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @WithMockUser(username = registeredUserEmail)
    public void shouldRegisterBook() throws Exception {
        createRegisteredUser(registeredUserEmail);
        BookDto book = createBook();

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.owner.email").value(registeredUserEmail));
    }

    @Test
    @WithMockUser(username = registeredUserEmail)
    public void shouldNotRegisterBookWhenNullValue() throws Exception {
        createRegisteredUser(registeredUserEmail);
        BookDto book = new BookDto("", RandomString.make(), RandomString.make(), RandomString.make());

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = registeredUserEmail)
    public void shouldBorrowBook() throws Exception {
        createRegisteredUser(registeredUserEmail);
        BookDto registeredBook = createRegisteredBook();

        mockMvc.perform(put("/books/{bookId}/borrow", registeredBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registeredBook)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = registeredUserEmail)
    public void shouldReturnBook() throws Exception {
        createRegisteredUser(registeredUserEmail);
        BookDto registeredBook = createRegisteredBook();
        BookDto borrowedBook = bookService.borrowBook(registeredBook.getId(), registeredUserEmail);

        mockMvc.perform(put("/books/{bookId}/return", registeredBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowedBook)))
                .andExpect(status().isOk());
    }
}
