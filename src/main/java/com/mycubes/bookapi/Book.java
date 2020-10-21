package com.mycubes.bookapi;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDate;

@RedisHash
@Data
public class Book implements Serializable {
	@Id
	private int id;
	private String name;
	private String author;
	private LocalDate publishDate;
}
