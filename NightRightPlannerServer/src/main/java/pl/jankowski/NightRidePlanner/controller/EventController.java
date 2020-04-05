package pl.jankowski.NightRidePlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.jankowski.NightRidePlanner.entity.EventEntity;
import pl.jankowski.NightRidePlanner.entity.GroupEntity;
import pl.jankowski.NightRidePlanner.entity.UserEntity;
import pl.jankowski.NightRidePlanner.repository.EventRepository;
import pl.jankowski.NightRidePlanner.repository.GroupRepository;
import pl.jankowski.NightRidePlanner.repository.UserRepository;

import java.util.Optional;

@RestController(value = "/event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public @ResponseBody
    EventEntity createEvent(@RequestBody long groupId, @RequestBody EventEntity event, Authentication authentication) throws Exception {
        GroupEntity group = groupRepository.findById(groupId).orElseThrow(() -> new Exception("Group does not exist"));
        UserEntity user = userRepository.findUserByUsername(authentication.getName()).orElse(null);
        if (group.getUsersInGroup().contains(user)) {
            event = eventRepository.save(event);
            group.getEvents().add(event);
        } else {
            throw new Exception("User does not belong to the group");
        }
        return event;
    }
}
