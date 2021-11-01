package com.example.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.dto.NoteDto;
import com.example.dto.NoteModel;
import com.example.service.NoteService;

@Path("/notebooks/{notebookId}/notes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NoteResource {

	@Inject
	NoteService noteService;

	@POST
	@Path("")
	public Response addNote(@PathParam("notebookId") long notebookId, NoteModel model) {
		NoteDto noteDto = noteService.addNote(notebookId, model);

		return Response.ok(noteDto, MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("/{id}")
	public Response updateNotebook(@PathParam("id") long id, NoteModel model) {
		noteService.updateNote(id, model);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteNote(@PathParam("notebookId") long notebookId, @PathParam("id") long noteId) {
		noteService.deleteNote(notebookId, noteId);
		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	public NoteDto getNote(@PathParam("id") long id) {
		return noteService.getNote(id);
	}

	@GET
	@Path("")
	public List<NoteDto> getNotes(@PathParam("notebookId") long notebookId) {
		return noteService.getNotes(notebookId);
	}
}
