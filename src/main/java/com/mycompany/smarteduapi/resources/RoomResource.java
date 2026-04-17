package com.mycompany.smarteduapi.resources;

import com.mycompany.smarteduapi.database.DataStore;
import com.mycompany.smarteduapi.model.Room;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    
    @GET
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }
    
    @POST
    public Response addRoom(Room room) {

        DataStore.rooms.put(room.getId(), room);

        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }
    
     // ✅ GET single room (FIXED)
    @GET
    @Path("/{roomId}")
    public Response getRoom(@PathParam("roomId") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Room not found"))
                    .build();
        }

        return Response.ok(room).build();
    }
    
    // ✅ DELETE room (IMPROVED)
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Room not found"))
                    .build();
        }
        
        // ❗ Business rule
        if (!room.getSensorIds().isEmpty()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("error", "Room has sensors, cannot delete"))
                    .build();
        }

        DataStore.rooms.remove(id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    
    
    
    
  

}