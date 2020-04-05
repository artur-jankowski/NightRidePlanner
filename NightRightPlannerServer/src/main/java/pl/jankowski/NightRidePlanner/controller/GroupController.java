package pl.jankowski.NightRidePlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.jankowski.NightRidePlanner.entity.EventEntity;
import pl.jankowski.NightRidePlanner.entity.GroupEntity;
import pl.jankowski.NightRidePlanner.entity.UserEntity;
import pl.jankowski.NightRidePlanner.repository.EventRepository;
import pl.jankowski.NightRidePlanner.repository.GroupRepository;
import pl.jankowski.NightRidePlanner.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/all")
    public @ResponseBody
    ResponseEntity<Iterable<GroupEntity>> listAllGroups() {
        Iterable<GroupEntity> entities = groupRepository.findAll();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Long createGroup(@RequestBody GroupEntity group, Authentication authentication) {
        Set<UserEntity> usersInGroup = new HashSet<>();
        UserEntity user = userRepository.findUserByUsername(authentication.getName()).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User doeas not exist"));
        usersInGroup.add(user);
        System.out.println("ADDED USER TO GROUP. USER: " + user.getUsername() + " GROUP: " + group.getName());
        group.setEvents(new HashSet<>());
        group.setUsersInGroup(usersInGroup);
        for (UserEntity usr : group.getUsersInGroup()
        ) {
            System.out.println("User in group: " + usr.getUsername());
        }
        return groupRepository.save(group).getId();
    }

    @PostMapping("{id}/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean updateGroup(@PathVariable("id") Long id, @RequestBody GroupEntity groupNew, Authentication authentication) throws Exception {
        GroupEntity group = groupRepository.findById(id).orElseThrow(() -> new Exception("Group does not exist"));
        UserEntity user = userRepository.findUserByUsername(authentication.getName()).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User doeas not exist"));
        if (group.getUsersInGroup().contains(user)) {
            group.setDescription(groupNew.getDescription() == null ? group.getDescription() : groupNew.getDescription());
            group.setName(groupNew.getName() == null ? group.getName() : groupNew.getName());
            groupRepository.save(group);
            return true;
        }
        throw new Exception("User does not belong to the group");
    }

    @GetMapping("{id}/getUserList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    Set<UserEntity> getUsersInGroup(@PathVariable("id") Long id) {
        Optional<GroupEntity> groupOptional = groupRepository.findById(id);
        return groupOptional.map(GroupEntity::getUsersInGroup).orElse(null);
    }

    @PostMapping("{id}/join")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean joinGroup(@PathVariable("id") Long id, Authentication authentication) throws Exception {
        GroupEntity group = groupRepository.findById(id).orElseThrow(() -> new Exception("Group does not exist"));
        UserEntity user = userRepository.findUserByUsername(authentication.getName()).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User does not exist"));
        if (group.getUsersInGroup().contains(user)) {
            return false;
        }
        group.getUsersInGroup().add(user);
        groupRepository.save(group);
        return true;
    }

    @PostMapping("{id}/createEvent")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean createEvent(@PathVariable("id") Long id, @RequestBody EventEntity event, Authentication authentication) throws Exception {
        GroupEntity group = groupRepository.findById(id).orElseThrow(() -> new Exception("Group does not exist"));
        UserEntity user = userRepository.findUserByUsername(authentication.getName()).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User does not exist"));
        if (!group.getUsersInGroup().contains(user)) {
            return false;
        }
        group.getEvents().add(event);
        eventRepository.save(event);
        groupRepository.save(group);
        return true;
    }
}
