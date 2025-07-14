
# Course Search Application

A Spring Boot application with Elasticsearch integration for searching educational courses with filters, pagination, and sorting.

## Features
- Full-text search on course titles/descriptions
- Filter by category, type, age range, price, and session dates
- Pagination and sorting support
- Elasticsearch 8.x integration
- Dockerized deployment

## Prerequisites
- Java 17
- Docker Desktop
- Maven 3.8+

## Setup Instructions

### 1. Start Elasticsearch
```bash
docker-compose up -d
```

Verify Elasticsearch is running:
```bash
curl http://localhost:9200
```

### 2. Build and Run the Application
```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

The application will start at: `http://localhost:8080`

### 3. API Endpoints

#### Search Courses
```
GET /api/search
Parameters:
- q: Search term (optional)
- category: Filter by category (optional)
- type: Filter by type [ONE_TIME, COURSE, CLUB] (optional)
- minAge/maxAge: Age range filter (optional)
- minPrice/maxPrice: Price range filter (optional)
- startDate: Show courses after this date (ISO-8601 format) (optional)
- sort: Sorting [upcoming, priceAsc, priceDesc] (default: upcoming)
- page: Page number (default: 0)
- size: Results per page (default: 10)
```

Example Request:
```bash
curl "http://localhost:8080/api/search?q=math&category=Science&minAge=10&sort=priceAsc"
```

### 4. Sample Data
The application automatically loads sample data from:
`src/main/resources/sample-courses.json`

### Project Structure
```
src/
├── main/
│   ├── java/com/ahmedhussain/demo/
│   │   ├── documents/      # Elasticsearch entity classes
│   │   ├── dto/           # Data transfer objects
│   │   ├── repository/    # Elasticsearch repositories
│   │   ├── service/       # Business logic
│   │   └── controller/    # REST endpoints
│   └── resources/
│       ├── sample-courses.json
│       └── application.properties
docker-compose.yml
```

## Troubleshooting
- **Elasticsearch connection issues**: Verify Docker container is running (`docker ps`)
- **Data not loading**: Check application logs for indexing errors
- **Port conflicts**: Ensure ports 8080 (app) and 9200 (ES) are available

## License
[MIT](https://choosealicense.com/licenses/mit/)
```

### How to Add This to Your Project:

1. **Create the file**:
   ```bash
   touch README.md
   ```

2. **Paste the content** (using your preferred method):
   - **VS Code**: Open the file and paste
   - **Command Line** (PowerShell):
     ```powershell
     @'
     [PASTE THE ENTIRE README CONTENT HERE]
     '@ | Out-File -Encoding utf8 README.md
     ```

3. **Customize**:
   - Update the package paths (`com.ahmedhussain/demo`)
   - Add any project-specific details
   - Include your name in the license 
