package pl.jankowski.NightRidePlanner.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.jankowski.NightRidePlanner.entity.EventEntity;
import pl.jankowski.NightRidePlanner.entity.GroupEntity;
import pl.jankowski.NightRidePlanner.entity.UserEntity;
import pl.jankowski.NightRidePlanner.repository.GroupRepository;
import pl.jankowski.NightRidePlanner.requestBody.JoinGroupBody;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/all")
    public @ResponseBody
    ResponseEntity<Iterable<GroupEntity>> listAllGroups() {
        Iterable<GroupEntity> entities = groupRepository.findAll();
        return new ResponseEntity<Iterable<GroupEntity>>(entities, HttpStatus.OK);
    }

    @GetMapping(value = "/hello")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String hello() {
     System.out.println("Hello");
        return "hello";
    }

    @PostMapping("/create")
    public Long createGroup(@RequestBody GroupEntity group) {
        group.setUsersInGroup(new HashSet<>());
        return groupRepository.save(group).getId();
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void updateGroup(@RequestBody GroupEntity group) {
        groupRepository.save(group);
    }

    @GetMapping("/getUserList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody Set<UserEntity> getUsersInGroup(@RequestParam long id) {
        Optional<GroupEntity> groupOptional = groupRepository.findById(id);
        return groupOptional.map(GroupEntity::getUsersInGroup).orElse(null);
    }

    @PostMapping("/join")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean joinGroup(@RequestBody JoinGroupBody body) {
        GroupEntity group = body.getGroup();
        UserEntity user = body.getUser();

        if (group.getUsersInGroup().contains(user)) {
            return false;
        }
        group.getUsersInGroup().add(user);
        groupRepository.save(group);
        return true;
    }

    @PostMapping("/createEvent")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean createEvent(@RequestBody CreateEventBody body) {
        UserEntity user = body.getUser();
        EventEntity event = body.getEvent();
        GroupEntity group = body.getGroup();

        if (!group.getUsersInGroup().contains(user)) {
            return false;
        }
        group.getEvents().add(event);
        groupRepository.save(group);
        return false;
    }



    private class CreateEventBody {
        @Getter
        @Setter
        private GroupEntity group;

        @Getter
        @Setter
        private UserEntity user;

        @Getter
        @Setter
        private  EventEntity event;
    }

}
