package com.example.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
public class NoteMetadataDto {

	private long id;

	private String title;

	private List<String> tags;

	private LocalDateTime created;

	private LocalDateTime lastModified;
}
