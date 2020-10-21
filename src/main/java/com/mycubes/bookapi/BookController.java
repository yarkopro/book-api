package com.mycubes.bookapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("book")
public class BookController {

	@Autowired
	BookRepository bookRepository;

	@PostMapping()
	public ResponseEntity createBook(@RequestBody Book book) {
		try {
			bookRepository.save(book);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			System.err.println(e);
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping()
	public void updateBook(Book book) {

	}

	@GetMapping
	public List<Book> getBooks() {
		return StreamSupport.stream(this.bookRepository.findAll().spliterator(), false)
			.collect(Collectors.toList());
	}

	@GetMapping("/search")
	public List<Book> searchBook(String queryString) {
		return null;
	}
}
