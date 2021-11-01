package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import com.example.data.Note;
import com.example.data.Notebook;
import com.example.dto.NoteDto;
import com.example.dto.NoteModel;
import com.example.mapper.NoteMapper;
import com.example.repository.NoteRepository;

@ApplicationScoped
public class NoteService {

	@Inject
	NotebookService notebookService;

	@Inject
	NoteRepository noteRepository;

	@Inject
	NoteMapper noteMapper;

	@Transactional
	public NoteDto addNote(long notebookId, NoteModel model) {
		Notebook notebook = notebookService.findNotebookById(notebookId);
		Note note = noteMapper.fromModel(model);
		notebook.getNotes().add(note);

		noteRepository.persist(note);
		return noteMapper.toDto(note);
	}

	@Transactional
	public void updateNote(long noteId, NoteModel model) {
		Note note = findById(noteId);
		noteMapper.update(note, model);
	}

	@Transactional
	public void deleteNote(long notebookId, long noteId) {
		Notebook notebook = notebookService.findNotebookById(notebookId);
		notebook.getNotes().removeIf(it -> it.getId() == noteId);
	}

	public NoteDto getNote(long id) {
		Note note = findById(id);
		return noteMapper.toDto(note);
	}

	public List<NoteDto> getNotes(long notebookId) {
		return notebookService.findNotebookById(notebookId).getNotes()
			.stream()
			.map(noteMapper::toDto)
			.collect(Collectors.toList());
	}

	private Note findById(long noteId) {
		return Optional.ofNullable(noteRepository.findById(noteId))
			.orElseThrow(() -> new NotFoundException(String.format("Note with id %s doesn't exist", noteId)));
	}
}
