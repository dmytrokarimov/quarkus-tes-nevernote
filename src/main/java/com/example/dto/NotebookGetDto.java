package com.example.dto;

import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
public class NotebookGetDto {

	private Long id;

	private String title;

	private List<NoteMetadataDto> notes;
}
