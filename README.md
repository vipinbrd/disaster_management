# 🌪️ Disaster Response Coordination Platform - Backend (Spring Boot)

This project is a backend system for managing disaster reports, coordinating resources, processing real-time updates, and leveraging AI to automatically detect and geotag disaster information.

---

## 🚀 Tech Stack

- **Backend Framework:** Spring Boot 3.x  
- **Database:** MySQL with Hibernate Spatial  
- **WebSockets:** STOMP over SockJS  
- **AI Integration:** Google Gemini API (via REST)  
- **Geocoding:** OpenCageData API  
- **Mapping Tools:** JTS Topology Suite  
- **Build Tool:** Maven  
- **Deployment Ready:** Vercel/Render-compatible

---

## 📦 Main Modules

### 1. 👥 Users (Assumed Pre-existing)

- Three types of users: `Admin`, `Normal`, `Consultant` (handled via roles, assumed managed outside this codebase).

---

### 2. 🌍 Disaster Management

#### Entity: `Disaster`
- Fields: `title`, `locationName`, `description`, `tags`, `createdAt`, `ownerId`, `location (POINT)`, `auditTrail (JSON)`
- Geo-spatial point for locating disasters
- JSON audit trail logs for each update

#### Endpoints:

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/disasters` | Create a disaster with coordinates |
| `GET`  | `/api/disasters` | List all disasters (or by tag) |
| `PUT`  | `/api/disasters/{id}` | Update an existing disaster |
| `DELETE` | `/api/disasters/{id}` | Delete a disaster by ID |

**Audit Trail**: Automatically logs actions like creation and updates in JSON format using Jackson.

---

### 3. 📦 Resource Management

#### Entity: `Resource`
- Fields: `name`, `type`, `locationName`, `location (POINT)`, `disasterId`, `createdAt`

#### Endpoints:

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/resources?lat=X&lng=Y` | Create a resource at a location |
| `GET`  | `/api/resources/nearby?lat=X&lng=Y&radius=R` | Find nearby resources in meters |

---

### 4. 📄 Report Management

#### Entity: `Report`
- Fields: `content`, `imageUrl`, `disasterId`, `userId`, `verificationStatus`, `createdAt`

#### Endpoints:

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/reports` | Submit a new report |
| `GET`  | `/api/reports/disaster/{disasterId}` | Fetch all reports for a disaster |
| `PUT`  | `/api/reports/{id}/verify?status=APPROVED` | Update report verification status |

---

### 5. 🧠 AI Integration (Gemini)

#### Endpoint:
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/ai` | Uses Gemini API to extract disaster location keywords from a given description |

- Uses structured prompt
- Returns city or place name
- Used internally for `Mock Tweet` simulation and auto-disaster creation

---

### 6. 🗺️ Geocoding (OpenCage)

- Takes extracted place name from AI
- Converts into **latitude** and **longitude**
- Used internally to place `Disaster` or `Resource` at the correct coordinates

---

### 7. 📡 WebSocket (Real-time Updates)

#### Configured using STOMP and SockJS:
- Endpoint: `/ws`
- Topics:
  - `/topic/disaster_updated` – sends on disaster creation/update/delete
  - `/topic/resources_updated` – sends on resource addition
  - `/topic/report_submitted` – when a report is filed
  - `/topic/report_verified` – when a report is verified

---

### 8. 🐦 Mock Tweet Detection

#### Endpoint:
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/mock-twitter/tweet` | Simulate tweet detection for disasters using keywords |

- Keywords: `flood`, `earthquake`, `fire`, `cyclone`, `storm`, `landslide`
- Auto-creates a `Disaster` entity if keyword is detected

---

## 🔧 Environment Configuration

### `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/disasterdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.spatial.dialect.mysql.MySQL8SpatialDialect

# AI + Geocoding keys
GOOGLE_API_KEY=your_gemini_key
OPENCAGE_API_KEY=your_opencage_key
