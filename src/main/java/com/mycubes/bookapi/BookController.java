package com.mycubes.bookapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
public class BookController {

	@Autowired
	BookRepository bookRepository;

	@PostMapping()
	public void createBook(Book book) {
		bookRepository.save(book);
	}

	@PatchMapping()
	public void updateBook(Book book) {

	}
}
