package com.example.oauth.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class UserController {

    private final UserService userService;

    @GetMapping("/")
    public List<User> users(){
        return userService.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) throws Exception{
        User user = userService.findByUsername(username);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        throw new UserException();
    }

    @GetMapping("/user-details")
    public ResponseEntity<User> userDetails(Principal principal){
        return new ResponseEntity<>(userService.userDetails(principal), HttpStatus.FOUND);
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(username, user),HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) throws UserException {
        if(userService.deleteUser(username))
            return new ResponseEntity<>(username, HttpStatus.OK);
        throw new UserException();
    }
}
