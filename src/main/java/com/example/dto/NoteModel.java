package com.example.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class NoteModel {

	public String title;

	public String body;

	@Singular
	public List<String> tags;

}
