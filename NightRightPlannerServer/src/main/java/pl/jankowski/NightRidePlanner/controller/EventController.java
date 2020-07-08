package pl.jankowski.NightRidePlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.jankowski.NightRidePlanner.entity.EventEntity;
import pl.jankowski.NightRidePlanner.entity.LocalizationEntity;
import pl.jankowski.NightRidePlanner.entity.UserEntity;
import pl.jankowski.NightRidePlanner.repository.EventRepository;
import pl.jankowski.NightRidePlanner.repository.GroupRepository;
import pl.jankowski.NightRidePlanner.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/{id}/join")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void joinEvent(@PathVariable("id") Long id, Authentication authentication) throws Exception {
        UserEntity user = userRepository.findUserByUsername(authentication.getName()).orElseThrow(() -> new Exception("User does not exist"));
        EventEntity event = eventRepository.findById(id).orElseThrow(() -> new Exception("Event does not exist"));
        if (!event.getAttendants().contains(user)) {
            event.getAttendants().add(user);
        }
        eventRepository.save(event);
    }

    @PostMapping(value = "/{id}/leave")
    @PreAuthorize("hasRole('ROLE_USER')")
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

    @PostMapping(value = "/{id}/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean updateEvent(@PathVariable("id") Long id, EventEntity event, Authentication authentication) throws Exception {
        EventEntity oldEvent = eventRepository.findById(id).orElseThrow(() -> new Exception("Event does not exist"));
        if (oldEvent.getAttendants().contains(userRepository.findUserByUsername(authentication.getName()).orElseThrow(() -> new Exception("User does not belong to the event")))) {
            oldEvent.setType(event.getType() == null ? oldEvent.getType() : event.getType());
            oldEvent.setDescription(event.getDescription() == null ? oldEvent.getDescription() : event.getDescription());
            oldEvent.setName(event.getName() == null? oldEvent.getName() : event.getName());
        }
        return true;
    }

    @GetMapping(value = "/{id}/")
    public EventEntity getEvent(@PathVariable("id") Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @GetMapping(value = "/{id}/localization")
    public List<LocalizationEntity> getLocalization(@PathVariable("id") Long id) throws Exception {
        return eventRepository.findById(id).orElseThrow(() -> new Exception("Event does not exist")).getLocalizations();
    }

}
