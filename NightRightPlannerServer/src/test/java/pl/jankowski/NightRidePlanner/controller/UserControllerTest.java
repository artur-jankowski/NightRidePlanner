package pl.jankowski.NightRidePlanner.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pl.jankowski.NightRidePlanner.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Mock
    private static UserRepository userRepository;

    @InjectMocks
    private static UserController userController = new UserController();

    @Test
    void testHelloWorldEndpoint() {
        assertEquals("hello", userController.hello());
    }

    //This class methods returninvocation value of service methods - no need to test them in separate test class

}
