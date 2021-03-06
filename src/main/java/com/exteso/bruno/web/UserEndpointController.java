package com.exteso.bruno.web;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exteso.bruno.model.RegisterAsServiceProvider;
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
        return userRepository.findBy(principal);
    }

    @RequestMapping("/api/user/{userId}")
    public User getUserById(@PathVariable("userId") long userId) {
        return userRepository.findById(userId);
    }

    @RequestMapping(value = "/api/user/request-as-service-provider", method = RequestMethod.POST)
    public void requestAsServiceProvider(@RequestBody RegisterAsServiceProvider request, Principal principal) {
        UserIdentifier ui = UserIdentifier.from(principal);
        userRepository.setRequestAs(ui.getProvider(), ui.getUsername(), UserType.SERVICE_PROVIDER, new Date(),//
                request.getCompanyName(),//
                request.getFieldOfWork(),//
                request.getAddress(),//
                request.getPhoneNumber(),//
                request.getContactEmail(),//
                request.getVatNumber(),//
                request.getNotes()//
                );
    }

    // FIXME add ROLE checking directly in security conf...
    @RequestMapping("/api/admin/user-list")
    public List<User> findAllUsers(Principal principal) {
        userRepository.ensureUserIsAdmin(principal);
        return userRepository.findAll();
    }

    @RequestMapping(value = "/api/admin/user/{userId}/confirm-as-service-provider", method = RequestMethod.POST)
    public void confirm(@PathVariable("userId") long userId, Principal principal) {
        userRepository.ensureUserIsAdmin(principal);
        userRepository.confirmUserAsServiceProvider(userId);
    }
}
