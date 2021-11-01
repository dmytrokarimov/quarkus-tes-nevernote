package com.example.mapper;

import java.util.Collections;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.data.Notebook;
import com.example.dto.NotebookDto;
import com.example.dto.NotebookGetDto;
import com.example.dto.NotebookModel;

@Mapper(componentModel = "cdi", uses = NoteMapper.class)
public interface NotebookMapper {

	Notebook fromModel(NotebookModel dto);

	@Mapping(target = "countOfNotes", expression = "java(entity.notes.size())")
	NotebookDto toDto(Notebook entity);

	NotebookGetDto toGetDto(Notebook entity);

	@BeforeMapping
	default void handleNotNullNoteList(final NotebookModel model) {
		if (model.getNotes() == null) {
			model.setNotes(Collections.emptyList());
		}
	}
}
