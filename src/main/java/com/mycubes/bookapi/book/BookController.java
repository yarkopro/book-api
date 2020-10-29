package com.mycubes.bookapi.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("book")
public class BookController {

	@Autowired
	BookRepository bookRepository;

	@PostMapping("/{id}")
	public ResponseEntity createBook(@RequestBody Book book, @PathVariable Integer id) {
		try {
			book.setId(id);
			bookRepository.save(book);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			System.err.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity updateBook(@RequestBody Book modifiedBookPartialUpdate, @PathVariable Integer id) {
		if (bookRepository.existsById(id)) {
			Book book = bookRepository.findById(id).get();
			if (modifiedBookPartialUpdate.getName() != null) {
				book.setName(modifiedBookPartialUpdate.getName());
			}
			if (modifiedBookPartialUpdate.getAuthor() != null) {
				book.setAuthor(modifiedBookPartialUpdate.getAuthor());
			}
			if (modifiedBookPartialUpdate.getPublishDate() != null) {
				book.setPublishDate(modifiedBookPartialUpdate.getPublishDate());
			}
			bookRepository.save(book);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(params = "search")
	public ResponseEntity<List<Book>> searchBook(@RequestParam String search) {
		Pattern queryPattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);

		List<Book> searchResult = StreamSupport.stream(bookRepository.findAll().spliterator(), false)
			.filter((Book book) -> queryPattern.matcher(book.getAuthor() + " " + book.getName()).find())
			.collect(Collectors.toList());
		if (searchResult.size() > 0) {
			return ResponseEntity.ok(searchResult);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
		if (bookRepository.existsById(id)) {
			bookRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}
