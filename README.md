# Smart Campus REST API

##  Overview

This project is a RESTful API developed using **JAX-RS (Jersey)** for managing a Smart Campus environment. The system enables the management of **Rooms**, **Sensors**, and **Sensor Readings**.

The API follows RESTful principles:
- Resource-based architecture
- Proper HTTP methods (GET, POST, DELETE)
- Meaningful HTTP status codes
- Nested resource design (sub-resources)
- JSON responses
- Centralized error handling and logging



##  API Design

###  Resources

- Rooms → Represents physical locations
- Sensors → Devices assigned to rooms
- Sensor Readings → Historical sensor data


###  Features

- Room management (CRUD)
- Sensor creation with validation
- Filtering sensors using query parameters
- Nested sub-resource for readings
- Auto-update sensor current value
- Exception handling (409, 422, 403, 500)
- Logging using filters


##  How to Run

1. Clone the repository:
2. git clone <your-repo-link>
3. Open in NetBeans or any Java IDE
4. Build using Maven: mvn clean install
5. Deploy to Apache Tomcat
6. Start server
7. Access API: http://localhost:8080/SmartCampusAPI/api/v1


##  Endpoints
### Discovery
GET /api/v1


### Rooms
GET /api/v1/rooms
POST /api/v1/rooms
GET /api/v1/rooms/{id}
DELETE /api/v1/rooms/{id}


### Sensors
GET /api/v1/sensors
GET /api/v1/sensors?type=Temperature
POST /api/v1/sensors


### Sensor Readings
GET /api/v1/sensors/{sensorId}/readings
POST /api/v1/sensors/{sensorId}/readings


## CURL Commands
### 1. Get API Info
curl -X GET http://localhost:8080/SmartCampusAPI/api/v1

### 2. Create Room
curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/rooms
-H "Content-Type: application/json"
-d '{"id":"R1","name":"Computer Lab","capacity":40}'


### 3. Get All Rooms
curl -X GET http://localhost:8080/SmartCampusAPI/api/v1/rooms


### 4. Create Sensor
curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors
-H "Content-Type: application/json"
-d '{"id":"TEMP-001","type":"Temperature","status":"ACTIVE","currentValue":25,"roomId":"R1"}'


### 5. Add Sensor Reading
curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors/TEMP-001/readings
-H "Content-Type: application/json"
-d '{"id":"READ-001","timestamp":1710000000,"value":26.5}'


##  Error Handling
- 409 Conflict → Room has sensors
- 422 Unprocessable Entity → Invalid room reference
- 403 Forbidden → Sensor under maintenance
- 500 Internal Server Error → Unexpected errors
All errors return JSON responses.


## Logging
A JAX-RS filter logs:
- Request method and URI
- Response status code






