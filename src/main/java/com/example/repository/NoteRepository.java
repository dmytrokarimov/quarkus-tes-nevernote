package com.example.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.example.data.Note;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class NoteRepository implements PanacheRepository<Note> {

	public List<Note> findAllByNotebookId(Long notebookId) {
		return find("select note "
			+ "from Notebook n "
			+ "left join fetch n.note note "
			+ "where n.id = ?1", notebookId).list();
	}
}
