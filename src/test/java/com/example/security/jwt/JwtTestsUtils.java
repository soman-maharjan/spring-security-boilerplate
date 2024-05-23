package com.example.security.jwt;

import com.example.security.user.enduser.EndUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtTestsUtils {
    final public String TEST_EMAIL = "soman.maharjan@machnetinc.com";

    @Autowired
    JwtService jwtService;

    public String generateTestJwtToken(){
        EndUser endUser = new EndUser();
        endUser.setEmail("soman.maharjan@machnetinc.com");

        return jwtService.generateToken(endUser);
    }
}
