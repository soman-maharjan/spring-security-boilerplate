package com.example.security.enduser;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.security.jwt.JwtTestsUtils;
import com.example.security.user.enduser.EndUserController;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EndUserControllerTests {
    @Autowired
    EndUserController endUserController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTestsUtils jwtTestsUtils;

    @Test
    void testEndUserControllerIsInitialized(){
        assertThat(endUserController).isNotNull();
    }

    @Test
    void testUserListEndpointIsProtected() throws Exception{
        mockMvc.perform(get("/user"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testUserListWithMockUser() throws Exception{
        mockMvc.perform(get("/user")
                        .cookie(new Cookie("TOKEN", jwtTestsUtils.generateTestJwtToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
