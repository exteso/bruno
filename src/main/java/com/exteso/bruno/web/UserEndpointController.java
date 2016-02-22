package com.exteso.bruno.web;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exteso.bruno.repository.UserRepository;

@RestController
public class UserEndpointController {

    private final UserRepository userRepository;

    @Autowired
    public UserEndpointController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/api/user")
    public Map<String, String> user(Principal principal) {
        return Collections.singletonMap("name", principal.getName());
    }
}
