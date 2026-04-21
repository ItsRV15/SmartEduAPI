/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smarteduapi.exception;

/**
 *
 * @author Ravindu Ranasinghe
 */
import com.mycompany.smarteduapi.model.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException exception) {

        ErrorMessage error = new ErrorMessage(
                exception.getMessage(),
                409
        );

        return Response.status(Response.Status.CONFLICT)
                .entity(error)
                .build();
    }
}