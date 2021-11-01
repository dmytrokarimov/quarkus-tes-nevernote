package com.example;

import java.time.format.DateTimeFormatter;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.data.Note;
import com.example.data.Notebook;
import com.example.dto.NoteModel;
import com.example.dto.NotebookDto;
import com.example.dto.NotebookModel;
import com.example.repository.NotebookRepository;
import com.example.service.NoteService;
import com.example.service.NotebookService;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
// @TestHTTPEndpoint(NotebookResource.class)
public class NoteResourceTest {

	@Inject
	NotebookService notebookService;

	@Inject
	NoteService noteService;

	@Inject
	NotebookRepository notebookRepository;

	Notebook notebook1;
	String note1Tag1 = "tag1", note1Tag2 = "tag2";

	Notebook notebook2;

	@BeforeEach
	public void setup() {
		NotebookDto notebookDto1 = notebookService.addNotebook(NotebookModel.builder()
			.title("someTitle")
			.note(NoteModel.builder()
				.title("some body")
				.body("some body")
				.tag(note1Tag1)
				.tag(note1Tag2)
				.build())
			.build());
		notebook1 = notebookRepository.findById(notebookDto1.getId());

		NotebookDto notebookDto2 = notebookService.addNotebook(NotebookModel.builder()
			.title("someTitle2")
			.note(NoteModel.builder()
				.title("some body2")
				.body("some body2")
				.tag("tag12")
				.tag("tag22")
				.build())
			.build());
		notebook2 = notebookRepository.findById(notebookDto2.getId());
	}

	@AfterEach
	@Transactional
	public void after() {
		notebookRepository.deleteAll();
	}

	@Test
	public void testPostNotebook() {
		NotebookModel newNotebook = NotebookModel.builder()
			.title("someTitle3")
			.note(NoteModel.builder()
				.title("some body3")
				.body("some body3")
				.tag("tag13")
				.tag("tag23")
				.build())
			.build();

		given()
			.contentType("application/json")
			.body(newNotebook)
			.when().post("/notebooks")
			.then()
			.log().body()
			.statusCode(200)
			.body("id", is(notebookRepository.find("title", newNotebook.getTitle()).singleResult().getId().intValue()))
			.body("title", is(newNotebook.getTitle()));
	}

	@Test
	public void testGetAllNotebooks() {
		given()
			.contentType("application/json")
			.when().get("/notebooks")
			.then()
			.log().body()
			.statusCode(200)
			.body("size()", is(2));
	}


	@Test
	public void testGetNotebook() {
		given()
			.contentType("application/json")
			.when().get("/notebooks/{id}", notebook1.getId())
			.then()
			.log().body()
			.statusCode(200)
			.body("id", is(notebook1.getId().intValue()))
			.body(
				"notes[0].tags[0]", is(note1Tag1),
				"notes[0].tags[1]", is(note1Tag2))
			.body("title", is(notebook1.getTitle()));
	}

	@Test
	public void testGetNotesByTag() {
		given()
			.contentType("application/json")
			.when().get("/notebooks/{id}?tag=tag1", notebook1.getId())
			.then()
			.log().body()
			.statusCode(200)
			.body("id", is(notebook1.getId().intValue()))
			.body("notes.size()", is(notebook1.getNotes().size()))
			.body(
				"notes[0].tags[0]", is(note1Tag1),
				"notes[0].tags[1]", is(note1Tag2))
			.body("title", is(notebook1.getTitle()));
	}

	@Test
	public void testGetNotesByTagNegative() {
		given()
			.contentType("application/json")
			.when().get("/notebooks/{id}?tag=tag12", notebook1.getId())
			.then()
			.log().body()
			.statusCode(200)
			.body("id", is(notebook1.getId().intValue()))
			.body("notes.size()", is(0))
			.body("title", is(notebook1.getTitle()));
	}

	@Test
	public void testGetNote() {
		NotebookDto notebookDto = notebookService.addNotebook(NotebookModel.builder()
			.title("someTitle")
			.note(NoteModel.builder()
				.title("before update")
				.body("some body")
				.tag("tag1")
				.build())
			.build());
		Notebook notebook = notebookRepository.findById(notebookDto.getId());

		NoteModel updateModel = NoteModel.builder()
			.title("updated note")
			.body("some body2")
			.tag("tag12")
			.build();
		Note noteToUpdate = notebook.getNotes().get(0);
		noteService.updateNote(noteToUpdate.getId(), updateModel);

		given()
			.contentType("application/json")
			.when().get("/notebooks/{notebookId}", notebook.getId())
			.then()
			.log().body()
			.statusCode(200)
			.body("notes[0].title", is(updateModel.getTitle()))
			.body("notes[0].created", is(DateTimeFormatter.ISO_DATE_TIME.format(noteToUpdate.getCreated())))
			.body("notes[0].id", is(noteToUpdate.getId().intValue()))
			.body("lastModified", anything())
			.body("notes[0].tags[0]", is(updateModel.getTags().get(0)));
	}
}
