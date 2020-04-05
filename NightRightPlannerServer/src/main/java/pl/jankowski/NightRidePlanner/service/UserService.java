package pl.jankowski.NightRidePlanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import pl.jankowski.NightRidePlanner.entity.UserDetailsEntity;
import pl.jankowski.NightRidePlanner.entity.UserEntity;
import pl.jankowski.NightRidePlanner.repository.UserDetailsRepository;
import pl.jankowski.NightRidePlanner.repository.UserRepository;
import pl.jankowski.NightRidePlanner.security.JwtProvider;
import pl.jankowski.NightRidePlanner.util.Role;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }


    public UserEntity createUser(String username, String password) {
        UserEntity user = new UserEntity();
        UserDetailsEntity userDetails = new UserDetailsEntity();
        userDetails.setPassword(passwordEncoder.encode(password));
        user.setUserInfo(userDetails);
        user.setUsername(username);
        userDetails.addRole(Role.USER);
        userRepository.save(user);
        userDetailsRepository.save(userDetails);
        return user;
    }

    public Optional<String> login(String username, String password) {
        Optional<String> token = Optional.empty();
        Optional<UserEntity> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            token = Optional.of(jwtProvider.createToken(username, user.get().getUserInfo().getRoles()));
        }
        return token;
    }


    public boolean changePassword(UserEntity user, String oldPassword, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findUserByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            UserDetailsEntity details = userOptional.get().getUserInfo();
            if (details.getPassword().equals(oldPassword)) {
                details.setPassword(passwordEncoder.encode(newPassword));
                userDetailsRepository.save(details);
                return true;
            }
        }
        return false;
    }

    public boolean updateProfile(UserEntity oldUser, UserEntity newUser, String password) throws Exception {
        UserDetailsEntity details = userRepository.findUserByUsername(newUser.getUsername()).orElseThrow(() -> new Exception("User does not exist")).getUserInfo();
        if (details.getPassword().equals(password)) {
            oldUser.setDescription(newUser.getDescription() == null ? oldUser.getDescription() : newUser.getDescription());
            oldUser.setUserInfo(setUserDetails(oldUser, newUser));
            userRepository.save(oldUser);
            return true;
        }
        return false;
    }

    private UserDetailsEntity setUserDetails(UserEntity oldUser, UserEntity newUser) {
        UserDetailsEntity oldUserDetails = oldUser.getUserInfo();
        UserDetailsEntity newUserDetails = newUser.getUserInfo();
        oldUserDetails.setPassword(newUserDetails.getPassword() == null ? oldUserDetails.getPassword() : newUserDetails.getPassword());
        return oldUserDetails;
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findUserByUsername(username).orElse(null);
    }
}
