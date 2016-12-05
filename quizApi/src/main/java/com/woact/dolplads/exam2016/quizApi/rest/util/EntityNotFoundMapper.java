package com.woact.dolplads.exam2016.quizApi.rest.util;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by dolplads on 05/12/2016.
 */
@Provider
public class EntityNotFoundMapper implements ExceptionMapper<EntityNotFoundException> {
    @Override
    public Response toResponse(EntityNotFoundException exception) {
        return Response.status(404).entity(exception.getMessage()).type(MediaType.APPLICATION_JSON).build();
    }
}
