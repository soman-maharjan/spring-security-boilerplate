# Spring Security Boilerplate
This project contains JWT implementation using Spring Security (Spring Boot) to authorize and authenticate user.

### Features
- Register User
- Login
- Authorize using JWT token for endpoints other than `/api/v1/auth/**`
- User Roles
- Uses hibernate to generate create-drop schemas

### TODOs
- Implement access token and refresh token
- Blacklist tokens
- Logout
- Refresh token endpoints
- Roles and authority validation

### To execute/run
- Create a database and add its configuration in `application.yml` file.
- Run the application. (Tables are auto created and dropped when application is stopped)