package com.example.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class NoteDto {

	private long id;

	private String title;

	private String body;

	private List<String> tags;

	private LocalDateTime created;

	private LocalDateTime lastModified;
}
