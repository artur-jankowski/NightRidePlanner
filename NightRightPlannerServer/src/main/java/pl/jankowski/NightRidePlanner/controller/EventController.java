package pl.jankowski.NightRidePlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
    public @ResponseBody EventEntity createEvent(@RequestBody long groupId, @RequestBody EventEntity event) {
        Optional<GroupEntity> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isPresent()) {
            GroupEntity group = groupOptional.get();
            event = eventRepository.save(event);
            group.getEvents().add(event);
            return event;
        }
        return null;
    }
}
