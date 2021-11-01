package com.example.mapper;

import java.util.Collections;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.data.Note;
import com.example.data.Tag;
import com.example.dto.NoteDto;
import com.example.dto.NoteMetadataDto;
import com.example.dto.NoteModel;

@Mapper(componentModel = "cdi")
public interface NoteMapper {

	Note update(@MappingTarget Note notebook, NoteModel dto);

	Note fromModel(NoteModel model);

	NoteDto toDto(Note entity);

	@BeforeMapping
	default void handleNotNullNoteModelList(final NoteModel noteModel) {
		if (noteModel.getTags() == null) {
			noteModel.setTags(Collections.emptyList());
		}
	}

	default String tagToString(Tag tag) {
		return tag.getTag();
	}

	default Tag tagFromString(String tag) {
		return new Tag(tag);
	}
}
