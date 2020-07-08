package pl.jankowski.NightRidePlanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.jankowski.NightRidePlanner.entity.GroupEntity;
import pl.jankowski.NightRidePlanner.entity.UserDetailsEntity;
import pl.jankowski.NightRidePlanner.entity.UserEntity;
import pl.jankowski.NightRidePlanner.repository.UserDetailsRepository;
import pl.jankowski.NightRidePlanner.repository.UserRepository;
import pl.jankowski.NightRidePlanner.security.JwtProvider;
import pl.jankowski.NightRidePlanner.util.Role;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private static AuthenticationManager authenticationManager;

    @Mock
    private static JwtProvider jwtProvider;

    @Mock
    private static UserDetailsRepository userDetailsRepository;

    @Mock
    private static UserRepository userRepository;

    @Mock
    private static PasswordEncoder passwordEncoder;

    private static final String ENCODED_PASSWORD = "This%password%is%encoded";

    private static final String TEST_NAME_1 = "test1";

    private static final String TEST_NAME_2 = "test2";

    private static final String TOKEN = "ThisIsSomeMockToken";

    private static final Long TEST_ID = 0L;

    private static UserEntity testUser1;

    private static UserEntity testUser2;

    private static UserDetailsEntity testUser1Details;

    private static UserDetailsEntity testUser2Details;

    @InjectMocks
    private final UserService userService = new UserService();


    @BeforeEach
    void setup() {
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        HashSet<GroupEntity> groups = new HashSet<>();
        UserDetailsEntity testUser1Details = new UserDetailsEntity(TEST_ID, TEST_NAME_1, roles);
        testUser2Details = new UserDetailsEntity(TEST_ID, TEST_NAME_2, roles);
        testUser1 = new UserEntity(TEST_ID, TEST_NAME_1, testUser1Details, TEST_NAME_1, groups);
        testUser2 = new UserEntity(TEST_ID + 1, TEST_NAME_2, testUser2Details, TEST_NAME_2, groups);
    }

    @Test
    void shouldReturnUserWhenValidUserIsProvided() {
        when(userRepository.findUserByUsername(eq(TEST_NAME_1))).thenReturn(Optional.of(testUser1));
        UserEntity user = userService.findByUsername(TEST_NAME_1);
        assertEquals(testUser1, user);
    }

    @Test
    void shouldReturnNullWhenInvalidUsernameIsProvided() {
        when(userRepository.findUserByUsername(eq(TEST_NAME_1 + "Invalid"))).thenReturn(Optional.empty());
        UserEntity user = userService.findByUsername(TEST_NAME_1 + "Invalid");
        assertNull(user);
    }

    @Test
    void shouldReturnUserWhenValidUserIdIsProvided() {
        when(userRepository.findById(eq(TEST_ID))).thenReturn(Optional.of(testUser1));
        UserEntity user = userService.getUser(TEST_ID);
        assertEquals(testUser1, user);
    }

    @Test
    void shouldReturnNullWhenInvalidUserIdIsProvided() {
        when(userRepository.findById(eq(TEST_ID + 1))).thenReturn(Optional.empty());
        UserEntity user = userService.getUser(TEST_ID + 1);
        assertNull(user);
    }

    @Test
    void shouldBeAbleToCreateUserWhenUniqueUsernameIsProvided() {

        when(passwordEncoder.encode(any())).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(UserEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(userDetailsRepository.save(any(UserDetailsEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        UserEntity user = userService.createUser(TEST_NAME_2, TEST_NAME_2);
        assertEquals(user.getUsername(), testUser2.getUsername());
        assertEquals(user.getUserInfo().getPassword(), ENCODED_PASSWORD);
        assertTrue(user.getUserInfo().getRoles().contains(Role.USER), "Expected roles to contain USER role, but it didn't");
    }

    @Test
    void shouldBeAbleToLoginWithProperAccountCredentialsProvided() {
        when(authenticationManager.authenticate(eq(new UsernamePasswordAuthenticationToken(TEST_NAME_1, TEST_NAME_1)))).thenAnswer(i -> null);
        when(userRepository.findUserByUsername(eq(TEST_NAME_1))).thenReturn(Optional.of(testUser1));
        when(jwtProvider.createToken(eq(TEST_NAME_1), any())).thenReturn(TOKEN);
        String token = userService.login(TEST_NAME_1, TEST_NAME_1).orElseThrow(() -> new AssertionError("Expected to get string token, but it didn't"));
        assertTrue(token.length() > 10, "Token is too short!");
    }

    @Test
    void shouldBeAbleToUpdateProfile() throws Exception {
        when(userRepository.findUserByUsername(TEST_NAME_1)).thenReturn(Optional.of(testUser1));
        UserEntity updatedUser = new UserEntity();
        updatedUser.setDescription(TEST_NAME_2);
        updatedUser.setUserInfo(new UserDetailsEntity());
        updatedUser.getUserInfo().setPassword(TEST_NAME_2);
        assertTrue(userService.updateProfile(testUser1, updatedUser, TEST_NAME_1));
        assertEquals(TEST_NAME_2, testUser1.getDescription());
        assertEquals(TEST_NAME_2, testUser1.getUserInfo().getPassword());
        verify(userRepository, times(1)).findUserByUsername(TEST_NAME_1);
        verify(userRepository, times(1)).save(testUser1);
    }

    @Test
    void shouldBeAbleToUpdatePassword() {
        when(userRepository.findUserByUsername(TEST_NAME_1)).thenReturn(Optional.of(testUser1));
        when(passwordEncoder.encode(anyString())).thenReturn(TEST_NAME_2);
        assertTrue(userService.changePassword(testUser1, TEST_NAME_1, TEST_NAME_2));
        assertEquals(TEST_NAME_2, testUser1.getUserInfo().getPassword());
        verify(userRepository, times(1)).findUserByUsername(TEST_NAME_1);
        verify(userDetailsRepository, times(1)).save(testUser1.getUserInfo());
        verify(passwordEncoder, times(1)).encode(anyString());
    }
}
