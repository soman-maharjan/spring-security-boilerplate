# Spring Security Boilerplate
This project contains JWT implementation using Spring Security (Spring Boot) to authorize and authenticate user.

### Features
- Register User
- Login
- Social Login (Login with Google, Github, linkedin, facebook, twitter)
- Authorize using JWT token for endpoints other than `/oauth2/**`
- User Roles
- Uses hibernate to generate create-drop schemas
- HTTPONLY cookie for JWT token after the user gets logged in.

### TODOs
- Implement access token and refresh token
- Blacklist tokens
- Logout
- Refresh token endpoints
- Roles and authority validation

### To execute/run
- Create a database and add its configuration in `application.yml` file.
- Run the application. (Tables are auto created and dropped when application is stopped)
