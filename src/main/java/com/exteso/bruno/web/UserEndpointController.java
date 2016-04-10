package com.exteso.bruno.web;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exteso.bruno.model.User;
import com.exteso.bruno.model.User.UserType;
import com.exteso.bruno.model.UserIdentifier;
import com.exteso.bruno.repository.UserRepository;

@RestController
public class UserEndpointController {

    private final UserRepository userRepository;

    @Autowired
    public UserEndpointController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/api/user")
    public User user(Principal principal) {
        UserIdentifier ui = UserIdentifier.from(principal);
        return userRepository.findBy(ui.getProvider(), ui.getUsername());
    }
    
    @RequestMapping("/api/user/{userId}")
    public User getUserById(@PathVariable("userId") long userId) {
        return userRepository.findById(userId);
    }
    
    @RequestMapping(value = "/api/user/request-as-service-provider", method = RequestMethod.POST)
    public void requestAsServiceProvider(Principal principal) {
        UserIdentifier ui = UserIdentifier.from(principal);
        userRepository.setRequestAs(ui.getProvider(), ui.getUsername(), UserType.SERVICE_PROVIDER, new Date());
    }
}
