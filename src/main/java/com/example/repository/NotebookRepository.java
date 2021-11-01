package com.example.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.example.data.Notebook;
import com.example.dto.NoteMetadataDto;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class NotebookRepository implements PanacheRepository<Notebook> {

	public List<NoteMetadataDto> findNotesByIdAndTag(Long id, String tagName) {
		return find("select note "
			+ "from Notebook n "
			+ "left join fetch n.note note "
			+ "left join fetch note.tags tag "
			+ "where n.id = ?1 and tag.tag = ?2", id, tagName)
			.project(NoteMetadataDto.class)
			.list();
	}
}
