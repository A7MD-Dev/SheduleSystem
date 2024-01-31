
# Scheduling System
Medical clinic with one doctor needs an appointment management system, where the clinic admin can review appointments by date or patient.
Also, admin can check patient history on demand, add new appointment, cancel appointment with cancellation reason (No show, based on patient request, Physician apology)


## Features
- Admin can open a home page which lists all today's appointments.
- Admin can add new appointments.
- Admin can cancel and log the reason.
- Admin can filter appointments by date (future or history).
- Admin can filter appointments by patient name (search field).
- Admin can preview patient appointments history.
- & more (like Patient CRUD ..etc), check the API Reference section



## Deployment

**Prerequisites:**

- Java Development Kit (JDK) 8 or later
- Maven Tool
- DBMS

**Database Init**

1. Create a database with a name of your choice.
2. Import the `db.sql` file into the newly created database:



**Installation Steps:**

1. Clone the repository:
   ```bash
   git clone 
   ```
2. Install dependencies:
    - Maven: `mvn install`


**Configuration:**

1. **Database Configuration (if using a database):**
    - Modify the `application.properties` file in the `src/main/resources` directory:
        - Set the `spring.datasource.url` property to the JDBC URL of your database.
        - Set the `spring.datasource.username` and `spring.datasource.password` properties to your database credentials.


2. **Running the API Server:**
   Maven: `mvn spring-boot:run`


3. **Accessing the API:**
- Once the server is running, the API endpoints will be accessible at: http://localhost:8080/api/


## API Reference

**Patient Endpoints**
#### Get all patients

```http
GET /api/patients
```

| Parameter | Type | Description |
|-----------|------|-------------|
| None | | Returns a list of all patients. |

#### Get patient by ID

```http
GET /api/patients/{patientId}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `patientId` | Long | **Required**. The ID of the patient to retrieve. |

#### Create a patient

```http
POST /api/patients
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `name` | String | **Required**. The patient's name. |
| `phone` | String | **Required**. The patient's phone number. |
| `email` | String | **Required**. The patient's email address. |
| `medicalHistory` | String | The patient's medical history (optional). |

#### Update a patient

```http
PUT /api/patients/{patientId}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `patientId` | Long | **Required**. The ID of the patient to update. |
| `name` | String | The updated name (optional). |
| `phone` | String | The updated phone number (optional). |
| `email` | String | The updated email address (optional). |
| `medicalHistory` | String | The updated medical history (optional). |

#### Get patient history

```http
GET /api/patients/{patientId}/history
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `patientId` | Long | **Required**. The ID of the patient to retrieve history for. |

#### Search patients by name

```http
GET /api/patients/search?name={name}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `name` | String | The name to search for. |

#### Filter patients by start letter

```http
GET /api/patients/filter/{startLetter}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `startLetter` | Character | The first letter of the patient's name to filter by. |

**Appointment Endpoints**

#### Get all appointments

```http
GET /api/appointments
```

| Parameter | Type | Description |
|-----------|------|-------------|
| None | | Returns a list of all appointments. |

#### Get appointments by date

```http
GET /api/appointments/by-date?date={date}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `date` | String | The date in YYYY-MM-DD format. |

#### Get appointment by ID

```http
GET /api/appointments/{appointmentId}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `appointmentId` | Long | **Required**. The ID of the appointment to retrieve. |

#### Create an appointment

```http
POST /api/appointments
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `patientId` | Long | **Required**. The ID of the patient for the appointment. |
| `appointmentTime` | LocalDateTime | **Required**. The date and time of the appointment. |
| `status` | AppointmentStatus | **Required**. The status of the appointment ("SCHEDULED", "CANCELLED", or "COMPLETED"). |

#### Update an appointment

```http
PUT /api/appointments/{appointmentId}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `appointmentId` | Long | **Required**. The ID of the appointment to update. |
| `patientId` | Long | The updated patient ID (optional). |
| `appointmentTime` | LocalDateTime | The updated date and time (optional). |
| `status` | AppointmentStatus | The updated status (optional). |
| `cancellationReason` | String | The reason for cancellation, if applicable (optional). |



#### Cancel an appointment

```http
DELETE /api/appointments/{appointmentId}?reason={reason}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `appointmentId` | Long | **Required**. The ID of the appointment to cancel. |
| `reason` | String | **Optional**. The reason for cancellation (e.g., "Patient illness", "Schedule conflict"). |




**Error Handling:**
- Appointment not found error (404) if the provided ID is invalid.
- Patient not found error (404) if the provided ID is invalid.
- Appointment Conflict (409) 
