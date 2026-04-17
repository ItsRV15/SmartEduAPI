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

    // ✅ GET all rooms
    @GET
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }


}