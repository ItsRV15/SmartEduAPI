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




