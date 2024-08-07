package com.bandomatteo.Prototipo1.controllers;

import com.bandomatteo.Prototipo1.domain.dto.BookDto;
import com.bandomatteo.Prototipo1.domain.entities.BookEntity;
import com.bandomatteo.Prototipo1.mappers.Mapper;
import com.bandomatteo.Prototipo1.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private Mapper<BookEntity, BookDto> bookMapper;
    private BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @PathVariable ("isbn") String isbn,
            @RequestBody BookDto bookDto){

        BookEntity bookEntity =  bookMapper.mapfrom(bookDto);

        BookEntity savedBookEntity = bookService.createBook(isbn,bookEntity);

        BookDto savedBookDto = bookMapper.mapto(savedBookEntity);

        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks(){
        List<BookEntity> books = bookService.findAll();

        return books.stream()
                .map(bookMapper::mapto)
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBookbyIsbn(@PathVariable ("isbn") String isbn){

        Optional<BookEntity> foundBook = bookService.findOne(isbn);

        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapto(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
