package com.example.service;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class CustomExceptionHandler
	implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception e) {
		log.error(e.getMessage(), e);

		if (e instanceof NotFoundException) {
			return Response.status(Response.Status.NOT_FOUND).
				entity(e.getMessage()).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST).
				entity(e.getMessage()).build();
		}
	}
}
