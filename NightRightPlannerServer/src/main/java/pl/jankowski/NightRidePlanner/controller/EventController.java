package pl.jankowski.NightRidePlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.jankowski.NightRidePlanner.entity.EventEntity;
import pl.jankowski.NightRidePlanner.entity.GroupEntity;
import pl.jankowski.NightRidePlanner.repository.EventRepository;
import pl.jankowski.NightRidePlanner.repository.GroupRepository;

import java.util.Optional;

@RestController(value = "/event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GroupRepository groupRepository;

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public @ResponseBody
    EventEntity createEvent(@RequestBody EventEntity event, @RequestParam Long groupId) {
        Optional<GroupEntity> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            GroupEntity group = groupOptional.get();
            event = eventRepository.save(event);
            group.getEvents().add(event);
            return event;
        }
        return null;
    }

    @GetMapping(value = "/{id}/join")
    public boolean joinEvent(Authentication authentication) {
        return false;
    }
}
