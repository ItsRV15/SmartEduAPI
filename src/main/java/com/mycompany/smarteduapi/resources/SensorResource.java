/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smarteduapi.resources;


import com.mycompany.smarteduapi.database.DataStore;
import com.mycompany.smarteduapi.model.Sensor;
import com.mycompany.smarteduapi.model.Room;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/sensors")
public class SensorResource {

    // GET with filtering
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getSensors(@QueryParam("type") String type) {

        if (type == null) {
            return DataStore.sensors.values().stream().collect(Collectors.toList());
        }

        return DataStore.sensors.values().stream()
                .filter(sensor -> sensor.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

}
