package com.mycompany.smarteduapi.database;

import com.mycompany.smarteduapi.model.Room;
import java.util.*;
import sun.management.Sensor;

public class DataStore {

    // Store all rooms
    public static Map<String, Room> rooms = new HashMap<>();

    // Store all sensors (for Part 3)
    public static Map<String, Sensor> sensors = new HashMap<>();

    // Store sensor readings (for Part 4)
    public static Map<String, List<SensorReading>> readings = new HashMap<>();
}