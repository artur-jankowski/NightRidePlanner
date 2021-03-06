package pl.jankowski.NightRidePlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.jankowski.NightRidePlanner.entity.UserEntity;
import pl.jankowski.NightRidePlanner.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/getUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody UserEntity getUser(@RequestParam long id) {
        return userService.getUser(id);
    }

    @GetMapping(value = "/hello")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
    public String hello() {
        return "hello";
    }

    @PostMapping(value = "/createUser")
    @ResponseBody
    public Optional<UserEntity> createUser(@RequestParam String username, @RequestParam String password) {
        return Optional.of(userService.createUser(username, password));
    }

    @GetMapping(value = "/login")
    public @ResponseBody Optional<String> login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @PostMapping(value = "/changePassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, Authentication authentication) {
        UserEntity user = userService.findByUsername(authentication.getName());
        return  userService.changePassword(user, oldPassword, newPassword);
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean updateProfile(@RequestBody UserEntity user, @RequestParam String password,  Authentication authentication) throws Exception {
        UserEntity oldUser = userService.findByUsername(authentication.getName());
        return userService.updateProfile(oldUser, user, password);
    }
}
