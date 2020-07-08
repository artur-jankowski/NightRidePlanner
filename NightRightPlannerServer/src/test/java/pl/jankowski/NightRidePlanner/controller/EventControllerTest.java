package pl.jankowski.NightRidePlanner.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import pl.jankowski.NightRidePlanner.entity.*;
import pl.jankowski.NightRidePlanner.repository.EventRepository;
import pl.jankowski.NightRidePlanner.repository.GroupRepository;
import pl.jankowski.NightRidePlanner.repository.UserRepository;
import pl.jankowski.NightRidePlanner.util.EventType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EventControllerTest {

    private static final String TEST_NAME_1 = "test1";
    private static final String TEST_NAME_2 = "test2";

    @Mock
    private EventRepository eventRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private EventController eventController;

    private EventEntity testEvent1;

    private EventEntity testEvent2;

    private UserEntity testUser1;

    private UserEntity testUser2;

    private GroupEntity testGroup1;

    private LocalizationEntity localization;

    private ArrayList<EventEntity> events = new ArrayList<>();


    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        HashSet<UserEntity> usersInGroup = new HashSet<>();
        UserDetailsEntity details = new UserDetailsEntity(0L, TEST_NAME_1, new HashSet<>());
        testUser1 = new UserEntity(0L, TEST_NAME_1, details, TEST_NAME_1, new HashSet<>());
        testUser2 = new UserEntity(0L, TEST_NAME_2, details, TEST_NAME_2, new HashSet<>());
        usersInGroup.add(testUser1);
        ArrayList<UserEntity> attendants = new ArrayList<>();
        attendants.add(testUser1);
        localization = new LocalizationEntity(0L, 1000.0, -1000.0, testEvent1);
        List<LocalizationEntity> localizations = new ArrayList<>();
        localizations.add(localization);
        testGroup1 = new GroupEntity(0L, TEST_NAME_1, TEST_NAME_1, usersInGroup, new HashSet<>(events));
        testEvent1 = new EventEntity(0L, EventType.GENERAL_MEET, TEST_NAME_1, TEST_NAME_1, testGroup1, attendants, localizations);
        testEvent2 = new EventEntity(1L, EventType.GENERAL_MEET, TEST_NAME_2, TEST_NAME_2, testGroup1, attendants, new ArrayList<>());
        events.add(testEvent1);
        events.add(testEvent2);
        when(eventRepository.findById(0L)).thenReturn(Optional.of(testEvent1));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent2));
        when(eventRepository.save(testEvent1)).thenReturn(testEvent1);
        when(eventRepository.save(testEvent2)).thenReturn(testEvent2);
        when(userRepository.findUserByUsername(TEST_NAME_1)).thenReturn(Optional.of(testUser1));
        when(userRepository.findUserByUsername(TEST_NAME_2)).thenReturn(Optional.of(testUser2));
        when(authentication.getName()).thenReturn(TEST_NAME_1);
        when(eventRepository.findAll()).thenReturn(events);
    }
    @Test
    void joinEventShouldAddUserToAttendants() throws Exception {
        when(authentication.getName()).thenReturn(TEST_NAME_2);
        eventController.joinEvent(0L, authentication);
        assertEquals(2, testEvent1.getAttendants().size());
        assertTrue(testEvent1.getAttendants().contains(testUser2));
    }

    @Test
    void leaveEventShouldRemoveUserFromAttendants() throws Exception {
        when(authentication.getName()).thenReturn(TEST_NAME_2);
        eventController.joinEvent(0L, authentication);
        assertEquals(2, testEvent1.getAttendants().size());
        assertTrue(testEvent1.getAttendants().contains(testUser2));
        eventController.leaveEvent(0L, authentication);
        assertEquals(1, testEvent1.getAttendants().size());
        assertFalse(testEvent1.getAttendants().contains(testUser2));
    }

    @Test
    void getAllShouldReturnAllEvents() {
        List<EventEntity> result = eventController.getAll();
        assertEquals(events, result);
    }

    @Test
    void updateEventShouldModifyEvent() throws Exception {
        EventEntity newEvent = new EventEntity();
        newEvent.setDescription(TEST_NAME_2);
        eventController.updateEvent(0L, newEvent, authentication);
        assertEquals(TEST_NAME_2, testEvent2.getDescription());
    }

    @Test
    void getEventShouldReturnEvent() {
        assertEquals(testEvent1, eventController.getEvent(0L));
    }

    @Test
    void getLocalizationsShouldReturnLocalizations() throws Exception {
        List<LocalizationEntity> result = eventController.getLocalization(0L);
        assertEquals(1, result.size());
        assertTrue(result.contains(localization));
    }

}
