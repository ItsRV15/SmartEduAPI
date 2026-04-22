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


## Error Handling Strategy

The API implements robust error handling using custom exceptions and ExceptionMappers:

| Scenario | Status Code | Explanation |
|--------|--------|------------|
| Room has sensors | 409 Conflict | Prevents data inconsistency |
| Invalid roomId | 422 Unprocessable Entity | Valid request but invalid reference |
| Sensor in maintenance | 403 Forbidden | State-based restriction |
| Unexpected error | 500 Internal Server Error | Global safety net |

All errors return structured JSON responses.

---

## Logging (Observability)

A logging filter is implemented using:
- `ContainerRequestFilter`
- `ContainerResponseFilter`

Logs include:
- Incoming HTTP method and URI
- Outgoing response status

This improves debugging, monitoring, and system observability.





## 📄 Report Answers
### Part 1: Service Architecture & Setup
#### Project & Application Configuration
The per-request lifecycle is the default lifecycle used by all JAX-RS resource classes; this implies that a new object will be instantiated for every single incoming HTTP request. In effect, this guarantees that requests will be handled in an independent manner and that there won't be any issues regarding shared state within the resource class since they will be thread-safe objects.

But the situation is quite different when considering in-memory application storage that relies on data structures like HashMaps or ArrayLists, as they would be shared among several requests at the same time. The main problem lies in ensuring that requests do not interfere with one another while working with shared data, as this might result in race conditions or even data loss.

In conclusion, although the per-request lifecycle makes sure that individual HTTP requests are handled in a thread-safe way, it is still necessary to synchronize any shared data structure in order to achieve consistency and stability within the application.

#### The ”Discovery” Endpoint
One of the main criteria used to define advanced RESTful API design would be Hypermedia (HATEOAS). It means that the API is able to provide the information about the available resources and actions that should be performed using these resources in the form of embedded links into the response messages sent by the server.

Thus, there is no need to use predefined endpoints and rely on static external documentation when using such APIs. Instead, a client application can navigate to the available resources using hyperlinks provided in API responses.

The biggest advantage for the client developer will be in the ability to easily adapt his program for new structures of the API (e.g., if some new endpoints are added) without any need to update anything except for following the updated hyperlinks.

If documentation and URLs for the endpoints were hardcoded into the program in advance, there might be no way for the client to handle any change in the API structure successfully.


### Part 2: Room Management
#### Room Resource Implementation
Returning only the room IDs will have smaller response sizes, thus lowering bandwidth usage, especially when many rooms are being processed. The client can request more data only if required, making it more efficient to handle many records. But returning the room IDs will increase client computation because the client should make more requests to get all data about each room.

But when all room objects are returned by the server, then the client has access to all information, making further requests unnecessary. Hence, developing an application on the server becomes easier and quicker. But the size of the response becomes bigger, leading to higher network usage.

So, returning room IDs is better for performance and scalability issues, while returning objects will make development easier.


#### Room Deletion & Safety Logic
DELETE requests in this scenario are idempotent, as repeating the same request leads to the same end state for the system.

If a user issues a DELETE request for a certain room for the very first time, then the room would be deleted from the DataStore. Upon issuing the same DELETE request, again for the same room, the room won’t even exist in the database, leading the API to respond with a 404 Not Found error.

Nevertheless, the end result in both cases would be the same, i.e., that the room has been successfully deleted from the DataStore.

In other words, multiple requests made using the exact same command do not yield any further side effects beyond the original one.


### Part 3: Sensor Operations & Linking
#### Sensor Resource & Integrity
@Consumes (MediaType.APPLICATION_JSON) annotation is used to denote that the endpoint will only receive requests that have a JSON body. In case the incoming data uses a different media type, such as plain/text or XML, then there will be no available message body reader to consume the message in the JAX-RS implementation.

In this way, JAX-RS framework automatically declines the incoming request, sending back an HTTP 415 Unsupported Media Type response status. The message denotes that the server understands the request but it cannot handle the media content type that was used in the request.

This approach helps ensure that the incoming data is in the correct media format before processing it in the system.


#### Filtered Retrieval & Search
Usage of @QueryParam for filtering purposes would be more appropriate since it adheres to the principles of RESTful services with respect to optional queries and search functionality. The main purpose of query parameters is to filter the content within a certain resource collection, e.g. getting sensors of a particular type.

The use of a path parameter /sensors/type/CO2 implies a completely different approach. This would create a resource hierarchy which could not represent filtering of the existing collection but a different resource instead.

In addition, usage of query parameters gives a lot of advantages related to flexibility. For example, the filtering process can involve numerous criteria at once (e.g. /sensors?type=CO2&status=ACTIVE).

As a result, @QueryParam can be regarded as a more suitable option for filtering and search operations on resources.


### Part 4: Deep Nesting with Sub- Resources

#### The Sub-Resource Locator Pattern
One way to enhance the architecture and design of the API is by employing the Sub-Resource Locator pattern. This means that different resources in the application would be managed using dedicated classes rather than trying to manage all endpoints inside one giant controller.

In turn, it would improve readability, maintenance and development of the application. For instance, managing all endpoints like sensors/{id}/readings and sensors/{id}/readings/{rid} would result in a bulky controller that would be hard to maintain.

Another benefit of using the sub-resource locator would be the ability to extend the application or update specific components of it without breaking other elements of the application.

Therefore, this approach would make the API more scalable, maintainable and organized.


### Part 5: Advanced Error Handling, Exception Mapping & Logging

#### Dependency Validation

However, HTTP 422 Unprocessable Entity should be used instead of 404 Not Found because the problem lies in the contents inside the request rather than in the request’s structure, even though it is correct. The JSON content in the request is valid, but it includes an invalid roomId value.

It is important to mention that HTTP 404 Not Found is used in case there is no such resource available, but HTTP 422 Unprocessable Entity suggests that the request can be processed, but something went wrong in its semantics.

#### The Global Safety Net (500)
Showing the internal stack traces from Java can be dangerous since this will expose sensitive information related to the actual classes used for implementation, their packaging, and internal workings of the application.

Using this kind of information, an attacker can detect some vulnerabilities within the system, exploit them, and gain unauthorized access to the internal workings of the application. Hence, we need to mask our error responses to protect our systems.

#### API Request & Response Logging Filters
It is a good idea to use JAX-RS filters for logging due to the fact that such cross-cutting concerns allow you to avoid duplication of code since there is some functionality that is needed to apply to all requests and responses. It means that you do not need to insert Logger.info() into all your methods.

In this case, it becomes possible to add an ability to log all requests automatically. It helps avoid duplication of code, make the application more consistent (since all the requests will be logged using a common algorithm), and facilitate scalability of the system (for example, it is easy to make changes related to logging).

If you have no filters and need to log requests manually, it may become very difficult to make these changes because of the repetitive code and risk of missed logging statements.






