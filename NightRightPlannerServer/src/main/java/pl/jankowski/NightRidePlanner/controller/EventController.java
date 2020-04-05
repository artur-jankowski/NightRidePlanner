package pl.jankowski.NightRidePlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.jankowski.NightRidePlanner.entity.EventEntity;
import pl.jankowski.NightRidePlanner.entity.GroupEntity;
import pl.jankowski.NightRidePlanner.entity.UserEntity;
import pl.jankowski.NightRidePlanner.repository.EventRepository;
import pl.jankowski.NightRidePlanner.repository.GroupRepository;
import pl.jankowski.NightRidePlanner.repository.UserRepository;
import pl.jankowski.NightRidePlanner.util.EventType;

import java.util.List;
import java.util.Set;

@RestController(value = "/event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/{id}/join")
    public void joinEvent(@PathVariable("id") Long id, Authentication authentication) throws Exception {
        UserEntity user = userRepository.findUserByUsername(authentication.getName()).orElseThrow(() -> new Exception("User does not exist"));
        EventEntity event = eventRepository.findById(id).orElseThrow(() -> new Exception("Event does not exist"));
        if (!event.getAttendants().contains(user)) {
            event.getAttendants().add(user);
        }
        eventRepository.save(event);
    }

    @PostMapping(value = "/{id}/leave")
    public void leaveEvent(@PathVariable("id") Long id, Authentication authentication) throws Exception {
        UserEntity user = userRepository.findUserByUsername(authentication.getName()).orElseThrow(() -> new Exception("User does not exist"));
        EventEntity event = eventRepository.findById(id).orElseThrow(() -> new Exception("Event does not exist"));
        if (!event.getAttendants().remove(user)) {
            throw new Exception("User wasn't attending event");
        }
        eventRepository.save(event);
    }

    @GetMapping(value = "/all")
    public List<EventEntity> getAll() {
        return eventRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public EventEntity getEvent(@PathVariable("id") Long id) {
        return eventRepository.findById(id).orElse(null);
    }

}
