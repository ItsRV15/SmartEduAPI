package com.mycompany.smarteduapi.database;

import com.mycompany.smarteduapi.model.Room;
import java.util.*;
import com.mycompany.smarteduapi.model.Sensor;
import com.mycompany.smarteduapi.model.SensorReading;

public class DataStore {
    
    public static Map<String, Room> rooms = new HashMap<>();

    static {
        rooms.put("R1", new Room("R1", "Staff Room", 50));
        rooms.put("R2", new Room("R2", "Computer Lab", 30));
    }
    
    public static Map<String, Sensor> sensors = new HashMap<>();
    public static Map<String, List<SensorReading>> readings = new HashMap<>();
    
}