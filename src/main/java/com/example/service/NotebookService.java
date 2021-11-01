package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import com.example.data.Notebook;
import com.example.dto.NotebookDto;
import com.example.dto.NotebookGetDto;
import com.example.dto.NotebookModel;
import com.example.mapper.NotebookMapper;
import com.example.repository.NotebookRepository;

@ApplicationScoped
public class NotebookService {

	@Inject
	NotebookRepository notebookRepository;

	@Inject
	NotebookMapper notebookMapper;

	@Transactional
	public List<NotebookDto> getAllNotebooks() {
		return notebookRepository.findAll()
			.stream()
			.map(notebookMapper::toDto)
			.collect(Collectors.toList());
	}

	public NotebookGetDto getNotebook(long id, String tag) {
		Notebook notebook = findNotebookById(id);
		NotebookGetDto result = notebookMapper.toGetDto(notebook);

		if (tag != null) {
			result.getNotes().removeIf(it -> !it.getTags().contains(tag));
		}
		return result;
	}

	@Transactional
	public NotebookDto addNotebook(NotebookModel notebookModel) {
		Notebook notebook = notebookMapper.fromModel(notebookModel);

		notebookRepository.persist(notebook);

		return notebookMapper.toDto(notebook);
	}

	@Transactional
	public void deleteNotebook(long id) {
		Notebook notebook = findNotebookById(id);

		notebookRepository.delete(notebook);
	}

	protected Notebook findNotebookById(long id) {
		return Optional.ofNullable(notebookRepository.findById(id))
			.orElseThrow(() -> new NotFoundException(String.format("Notebook with id %s doesn't exist", id)));
	}
}
