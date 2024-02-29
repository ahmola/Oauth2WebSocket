package com.example.oauth.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findByUsername(String username){
        User user = userRepository.findByUsername(username);

        if(user == null)
            log.info("There is no such user " + username);
        else
            log.info("Found " + username);

        return user;
    }

    public User updateUser(String username, User user){
        User updated_user = userRepository.findByUsername(username);
        log.info("Find user to update " + updated_user);

        if(updated_user != null) {
            updated_user.update(user);
            log.info("User update Complete");
        }

        return userRepository.save(updated_user);
    }

    public User userDetails(Principal principal){

        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) principal;
        OAuth2User OAuth2User = oauth2Token.getPrincipal();

        String username = OAuth2User.getAttribute("name");
        String email = OAuth2User.getAttribute("email");

        assert username != null;
        if(username.isEmpty() || username.isBlank())
            log.info("User is " + OAuth2User.getAttributes().toString() + "\n"
                    + OAuth2User.getAuthorities().toString());
        else
            log.info("User is " + username);

        User user = userRepository.findByEmail(email);
        if(user == null){
            log.info("Start saving user " + username);
            user = new User();
            user.setUsername(Objects.requireNonNullElse(username, "anonymous"));
            user.setEmail(email);
            userRepository.save(user);
        }

        return user;
    }

    public boolean deleteUser(String username) {
        userRepository.deleteByUsername(username);

        log.info("Check whether deleted or not");
        if(userRepository.findByUsername(username) == null)
            return true;
        return false;
    }
}
