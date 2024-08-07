package com.bandomatteo.Prototipo1.controllers;

import com.bandomatteo.Prototipo1.domain.dto.AuthorDto;
import com.bandomatteo.Prototipo1.domain.entities.AuthorEntity;
import com.bandomatteo.Prototipo1.mappers.Mapper;
import com.bandomatteo.Prototipo1.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorServices, Mapper<AuthorEntity, AuthorDto> authorMapper) {

        this.authorService = authorServices;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {

        AuthorEntity authorEntity = authorMapper.mapfrom(author);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);

        return new ResponseEntity<>(authorMapper.mapto(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path="/authors")
    public List<AuthorDto> listAuthors(){
        List<AuthorEntity> authors = authorService.findAll();

        return authors.stream()
                .map(authorMapper::mapto)
                .collect(Collectors.toList());


    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {

        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

        return foundAuthor.map(authorEntity-> {
            AuthorDto authorDto = authorMapper.mapto( authorEntity );
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }
}
