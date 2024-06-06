# HireHub - NUWorks Clone 

NUWorks Clone is a Java enterprise application that replicates the functionality of the campus career portal NUWorks. It provides features for both administrators and users to manage job postings and applications.

## Technologies Used

- **Backend:** Spring Boot, Hibernate ORM, MySQL
- **Frontend:** Bootstrap
- **Authentication:** Spring Security
- **Email Verification:** SMTP
- **AJAX:** Used for lazy loading and pagination

## Features

- **Admin Features:**
  - CRUD operations on job postings
  - Modify and delete user accounts
- **User Features:**
  - View and apply for job postings
  - Manage job applications

## Installation

1. Clone the repository.
2. Set up MySQL database and update application.properties file with the database credentials.
3. Run the application using Maven: `mvn spring-boot:run`

## Usage

- Access the application at `http://localhost:8080`
- Admin login: Use the provided admin credentials to access the admin dashboard.
- User login: Users can register for an account or use the provided user credentials to access user features.


