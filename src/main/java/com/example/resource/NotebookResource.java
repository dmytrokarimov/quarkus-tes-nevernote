package com.example.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.dto.NotebookDto;
import com.example.dto.NotebookGetDto;
import com.example.dto.NotebookModel;
import com.example.service.NotebookService;

@Path("/notebooks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotebookResource {

	@Inject
	NotebookService notebookService;

	@GET
	public List<NotebookDto> getAllNotebooks() {
		return notebookService.getAllNotebooks();
	}

	@GET
	@Path("/{id}")
	public NotebookGetDto getNotebook(@PathParam("id") long id, @QueryParam("tag") String tag) {
		return notebookService.getNotebook(id, tag);
	}

	@POST
	public Response addNotebook(NotebookModel notebookModel) {
		return Response.ok(notebookService.addNotebook(notebookModel), MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteNotebook(@PathParam("id") long id) {
		notebookService.deleteNotebook(id);
		return Response.ok().build();
	}
}
