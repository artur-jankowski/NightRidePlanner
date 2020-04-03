package pl.jankowski.NightRidePlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
    public @ResponseBody UserEntity getUser(@RequestParam long id) {
        return userService.getUser(id);
    }

    @GetMapping(value = "/hello")
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
    public boolean changePassword(@RequestBody UserEntity user, @RequestBody String oldPassword, @RequestBody String newPassword) {
        return  userService.changePassword(user, oldPassword, newPassword);
    }

    @PostMapping(value = "/update")
    public boolean updateProfile(@RequestBody UserEntity user, @RequestBody String password) {
        return userService.updateProfile(user, password);
    }

    @PostMapping(value = "/logout")
    public String logout() {
        return "Logged out";
    }
}
