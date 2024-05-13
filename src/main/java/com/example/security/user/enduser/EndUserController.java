package com.example.security.user.enduser;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enduser")
public class EndUserController {
    private final EndUserService endUserService;

    @GetMapping("/list")
    public List<EndUser> userList(){
        return endUserService.findAll();
    }
}
