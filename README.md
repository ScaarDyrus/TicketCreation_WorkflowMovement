# TicketCreation_WorkflowMovement
A Spring Boot application that implements a ticket creation and workflow movement system with activity tracking, similar to Jira.

## Features

- Create, read, update, and delete tickets
- Add and remove tags from tickets
- Move tickets through workflow stages (Backlog, To-do, In Progress, Review, Dev Done, Live, Blocked)
- Track all activities on tickets (status changes, assignee changes, comments, etc.)
- Tag auto-suggestion
- Get most used tags
- Find tickets with specific tags or combinations of tags

## Technologies Used

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database (in-memory)
- Caffeine Cache
- Maven

## API Endpoints

### Tag APIs

- `GET /api/tags` - Get all tags
- `GET /api/tags/{id}` - Get tag by ID
- `GET /api/tags/name/{name}` - Get tag by name
- `POST /api/tags` - Create a new tag
- `PUT /api/tags/{id}` - Update a tag
- `DELETE /api/tags/{id}` - Delete a tag
- `GET /api/tags/search?query={query}` - Search tags by name
- `GET /api/tags/autocomplete?prefix={prefix}` - Autocomplete tags by prefix
- `GET /api/tags/most-used?limit={limit}` - Get most used tags
- `GET /api/tags/ticket/{ticketId}` - Get tags for a specific ticket

### Ticket APIs

- `GET /api/tickets` - Get all tickets (paginated)
- `GET /api/tickets/{id}` - Get ticket by ID
- `POST /api/tickets` - Create a new ticket
- `PUT /api/tickets/{id}` - Update a ticket
- `DELETE /api/tickets/{id}` - Delete a ticket
- `POST /api/tickets/{id}/tags` - Add tags to a ticket
- `DELETE /api/tickets/{ticketId}/tags/{tagId}` - Remove a tag from a ticket
- `GET /api/tickets/by-tag/{tagId}` - Get tickets by tag ID
- `GET /api/tickets/by-tag-name/{tagName}` - Get tickets by tag name
- `GET /api/tickets/by-tag-ids?tagIds={id1,id2,...}` - Get tickets by multiple tag IDs
- `GET /api/tickets/by-tag-names?tagNames={name1,name2,...}` - Get tickets by multiple tag names
- `GET /api/tickets/by-all-tag-names?tagNames={name1,name2,...}` - Get tickets that have all specified tags
- `PATCH /api/tickets/{id}/status` - Update ticket status
- `PATCH /api/tickets/{id}/assignee` - Update ticket assignee
- `POST /api/tickets/{id}/comments` - Add a comment to a ticket

### Activity APIs

- `GET /api/activities/ticket/{ticketId}` - Get activity logs for a specific ticket

## Workflow Stages

The system supports the following workflow stages:

1. **Backlog** - Tickets that are not yet ready to be worked on
2. **To-do** - Tickets that are ready to be worked on
3. **In Progress** - Tickets that are currently being worked on
4. **Review** - Tickets that are ready for review
5. **Dev Done** - Tickets that have been reviewed and approved
6. **Live** - Tickets that have been deployed to production
7. **Blocked** - Tickets that are blocked by some issue

## Activity Tracking

The system tracks the following activities:

1. **Ticket Created** - When a new ticket is created
2. **Status Changed** - When a ticket's status is changed
3. **Assignee Changed** - When a ticket's assignee is changed
4. **Title Changed** - When a ticket's title is changed
5. **Description Changed** - When a ticket's description is changed
6. **Tag Added** - When a tag is added to a ticket
7. **Tag Removed** - When a tag is removed from a ticket
8. **Comment Added** - When a comment is added to a ticket

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application using Maven:

```bash
mvn spring-boot:run
```

4. The application will be available at `http://localhost:8080`
5. H2 Console is available at `http://localhost:8080/h2-console`

## Example Usage

### Create a ticket

```bash
curl -X POST http://localhost:8080/api/tickets \
  -H 'Content-Type: application/json' \
  -d '{
    "title": "Implement user authentication",
    "description": "Add user authentication using JWT",
    "tagNames": ["security", "backend"]
  }'
```

### Move a ticket to In Progress

```bash
curl -X PATCH http://localhost:8080/api/tickets/1/status \
  -H 'Content-Type: application/json' \
  -d '{
    "status": "IN_PROGRESS",
    "userId": "user123",
    "userName": "John Doe",
    "comment": "Starting work on this ticket"
  }'
```

### Assign a ticket to someone

```bash
curl -X PATCH http://localhost:8080/api/tickets/1/assignee \
  -H 'Content-Type: application/json' \
  -d '{
    "assignee": "jane.doe@example.com",
    "userId": "user456",
    "userName": "Jane Doe",
    "comment": "Assigning to Jane who has experience with JWT"
  }'
```

### Add a comment to a ticket

```bash
curl -X POST "http://localhost:8080/api/tickets/1/comments?comment=I%20suggest%20using%20Spring%20Security%20for%20this&userId=user789&userName=Bob%20Smith"
```

### View activity log for a ticket

```bash
curl -X GET http://localhost:8080/api/activities/ticket/1
```