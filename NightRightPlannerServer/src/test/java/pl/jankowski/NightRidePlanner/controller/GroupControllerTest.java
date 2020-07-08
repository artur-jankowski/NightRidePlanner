package pl.jankowski.NightRidePlanner.controller;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import pl.jankowski.NightRidePlanner.entity.EventEntity;
import pl.jankowski.NightRidePlanner.entity.GroupEntity;
import pl.jankowski.NightRidePlanner.entity.UserDetailsEntity;
import pl.jankowski.NightRidePlanner.entity.UserEntity;
import pl.jankowski.NightRidePlanner.repository.EventRepository;
import pl.jankowski.NightRidePlanner.repository.GroupRepository;
import pl.jankowski.NightRidePlanner.repository.UserRepository;
import pl.jankowski.NightRidePlanner.util.EventType;
import pl.jankowski.NightRidePlanner.util.Role;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GroupControllerTest {
    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    Authentication authentication;

    @InjectMocks
    private static GroupController groupController = new GroupController();

    private static Set<UserEntity> usersInGroup;

    private static final String TEST_NAME_1 = "test1";

    private static final String TEST_NAME_2 = "test2";

    private static Set<EventEntity> events = new HashSet<>();

    private static GroupEntity testGroup1;

    private static GroupEntity testGroup2;

    private HashSet<GroupEntity> testGroups;
    private UserEntity testUser;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        usersInGroup = new HashSet<>();
        testGroups = new HashSet<>();
        HashSet<Role> roles = new HashSet<>();
        UserDetailsEntity details = new UserDetailsEntity(0L, TEST_NAME_1, roles);
        testUser = new UserEntity(0L, TEST_NAME_1, details, TEST_NAME_1, new HashSet<>());
        usersInGroup.add(testUser);
        testGroup1 = new GroupEntity(0L, TEST_NAME_1, TEST_NAME_1, usersInGroup, events);
        testGroup2 = new GroupEntity(1L, TEST_NAME_2, TEST_NAME_2, usersInGroup, events);
        testGroups.add(testGroup1);
        testGroups.add(testGroup2);
        when(authentication.getName()).thenReturn("test");
        when(userRepository.findUserByUsername("test")).thenReturn(Optional.of(testUser));
        when(groupRepository.findById(0L)).thenReturn(Optional.of(testGroup1));
    }

    @Test
    void listAllGroupShouldReturnListFromRepository() {

        when(groupRepository.findAll()).thenReturn(Lists.newArrayList(testGroups));
        Iterable<GroupEntity> result = groupController.listAllGroups().getBody();
        assertNotNull(result, "Result is null");
        assertEquals(2, ((List) result).size());
        assertEquals(testGroup1, ((List) result).get(0));
        assertEquals(testGroup2, ((List) result).get(1));
    }

    @Test
    void createGroupShouldReturnGroupId() {
        when(groupRepository.save(testGroup1)).thenReturn(testGroup1);
        Long resultId = groupController.createGroup(testGroup1, authentication);
        assertEquals(testGroup1.getId(), resultId);
    }

    @Test
    void updateGroupShouldReturnTrueAndUpdateGroup() throws Exception {
        when(groupRepository.save(testGroup1)).thenReturn(testGroup1);
        UserEntity entityStub = new UserEntity();
        testGroup1.getUsersInGroup().add(entityStub);
        GroupEntity expectedGroup = new GroupEntity();
        expectedGroup.setDescription("Changed description");
        when(userRepository.findUserByUsername("test")).thenReturn(Optional.of(testUser));
        boolean result = groupController.updateGroup(0L, expectedGroup, authentication);
        assertTrue(result);
        assertEquals(expectedGroup.getDescription(), testGroup1.getDescription());
    }

    @Test
    void getUsersInGroupShouldReturnListOfUsers() {
        Set<UserEntity> result = groupController.getUsersInGroup(0L);
        assertTrue(result.contains(testUser));
        assertEquals(1, result.size());
    }

    @Test
    void joinGroupShouldAddToGroupList() throws Exception {
        UserDetailsEntity details = new UserDetailsEntity(0L, TEST_NAME_2, new HashSet<>());
        UserEntity newUser = new UserEntity(41L, TEST_NAME_2, details, TEST_NAME_2, new HashSet<>());
        when(authentication.getName()).thenReturn(TEST_NAME_2);
        when(userRepository.findUserByUsername(TEST_NAME_2)).thenReturn(Optional.of(newUser));
        groupController.joinGroup(0L, authentication);
        assertEquals(2, testGroup1.getUsersInGroup().size());
        assertTrue(testGroup1.getUsersInGroup().contains(newUser));
    }

    @Test
    void createEventShouldCreateAndAddEventToGroup() throws Exception {
        EventEntity event = new EventEntity(0L, EventType.GENERAL_MEET, TEST_NAME_1, TEST_NAME_1, testGroup1, new ArrayList<>());
        groupController.createEvent(0L, event, authentication);
        assertEquals(1, testGroup1.getEvents().size());
        assertTrue(testGroup1.getEvents().contains(event));
    }
}
