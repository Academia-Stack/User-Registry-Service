# API documentation

The **User Registry Service** is a microservice responsible for managing students, teachers, subjects, and system logs within the platform.
It exposes REST APIs for creating, updating, retrieving, and deleting student and teacher records, managing enrolments, assigning teachers to subjects, and retrieving system logs.

---

# Base URL

```
http://localhost:8000
```

---

# Student Endpoints

Base path:

```
/student
```

### Welcome Endpoint

**GET /student**

Returns a welcome message for the Student Management System.

---

### Get All Students

**GET /student/showAllStudents**

Returns a list of all students in the system.

---

### Get Student by ID

**GET /student/showStudent/{studentId}**

Retrieves a student using their unique ID.

---

### Get Students by Name

**GET /student/showStudentsByName/{studentName}**

Returns a list of students that match the given name.

---

### Add New Student

**POST /student/addStudent**

Creates a new student record.

Consumes:

```
application/json
application/xml
```

Produces:

```
application/json
application/xml
```

Returns the generated `studentId`.

---

### Update Student

**POST /student/updateStudent/{studentId}**

Updates the details of an existing student.

---

### Get Subjects of a Student

**GET /student/getStudentSubjects/{studentId}**

Returns all subjects in which a student is enrolled.

---

### Delete Students

**POST /student/deleteStudent**

Deletes one or more students using a list of student IDs.

Example request body:

```json
[1,2,3]
```

Returns a success message after deletion.

---

### Enrol Student to Subject

**POST /student/enrolStudent/{studentId}/{subjectId}**

Enrolls a student in a subject.
If the student is already enrolled, an error is thrown.

---

# Teacher Endpoints

Base path:

```
/Teacher
```

---

### Welcome Endpoint

**GET /Teacher**

Returns a welcome message for the Teacher Management System.

---

### Get All Teachers

**GET /Teacher/showAllTeachers**

Returns a list of all teachers in the system.

---

### Get Teacher by ID

**GET /Teacher/showTeacher/{TeacherId}**

Retrieves a teacher using their unique ID.

---

### Add Teacher

**POST /Teacher/addTeacher**

Creates a new teacher record.

Consumes:

```
application/json
application/xml
```

Produces:

```
application/json
application/xml
```

Returns the generated `TeacherId`.

---

### Get Subjects of a Teacher

**GET /Teacher/getSubjects/{teacherId}**

Returns all subjects assigned to a teacher.

---

### Delete Teachers

**POST /Teacher/deleteTeacher**

Deletes one or more teachers using a list of teacher IDs.

Example request body:

```json
[1,2,3]
```

Returns a success message after deletion.

---

### Assign Teacher to Subject

**POST /Teacher/assignTeacher/{teacherId}/{subjectId}**

Assigns a teacher to a specific subject.

---

# Log Endpoints

Base path:

```
/logs
```

---

### Get All Logs

**GET /logs**

Returns all system logs formatted for readability.

---

### Get Log by ID

**GET /logs/{logId}**

Retrieves a specific log entry by its ID.

---

# Error Handling

The service throws domain-specific exceptions such as:

* `StudentNotFoundException`
* `TeacherNotFoundException`
* `EnrolmentAlreadyExists`
* `LogNotFoundException`

These exceptions indicate issues such as missing records or duplicate enrolments.

---

# Technology Stack

* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **Redis (Caching)**
* **MySQL**
* **RESTful API Design**

---

# Role in the Microservice Architecture

The **User Registry Service** acts as the central identity and academic registry system responsible for managing:

* Students
* Teachers
* Subjects
* Enrollments
* System logs

It will later integrate with other services in the ecosystem using **Netflix Eureka for service discovery**.
