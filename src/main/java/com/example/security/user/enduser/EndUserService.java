package com.example.security.user.enduser;

import com.example.security.auth.oauth2.user.Oauth2User;
import com.example.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class EndUserService {
    private final EndUserRepository endUserRepository;

    public String findOrRegisterUser(Oauth2User oauth2User) {
        if(endUserRepository.existsByEmail(oauth2User.getEmail())){
            return oauth2User.getEmail();
        }else{
            EndUser enduser = registerUser(oauth2User);
            return enduser.getEmail();
        }
    }

    private EndUser registerUser(Oauth2User oauth2User){
        EndUser endUser = new EndUser();
        endUser.setReferenceId(UUID.randomUUID());
        endUser.setEmail(oauth2User.getEmail());
        endUser.setRole(Role.USER);
        endUserRepository.save(endUser);
        return endUser;
    }

    public EndUser findByEmail(String email){
        return endUserRepository.findByEmail(email);
    }

    public List<EndUser> findAll(){
        return endUserRepository.findAll();
    }
}



