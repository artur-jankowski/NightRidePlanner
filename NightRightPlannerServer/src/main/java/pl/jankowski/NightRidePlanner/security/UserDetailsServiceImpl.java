package pl.jankowski.NightRidePlanner.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.jankowski.NightRidePlanner.entity.UserEntity;
import pl.jankowski.NightRidePlanner.repository.UserRepository;
import pl.jankowski.NightRidePlanner.util.Role;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserByUsername(s).orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getUserInfo().getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleValue()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getUserInfo().getPassword(), grantedAuthorities);
    }

    public Optional<UserDetails> loadUserByJwtToken(String token) {
        if (jwtProvider.isValid(token)) {
            return Optional.of(
                    withUsername(jwtProvider.getUsername(token))
                            .authorities(jwtProvider.getRoles(token))
                            .password("")
                            .accountExpired(false)
                            .accountLocked(false)
                            .credentialsExpired(false)
                            .disabled(false)
                            .build());
        }
        return Optional.empty();
    }

    public Optional<UserDetails> loadUserByJwtTokenAndDatabase(String token) {
        if (jwtProvider.isValid(token)) {
            return Optional.of(loadUserByUsername(jwtProvider.getUsername(token)));
        }
        return Optional.empty();
    }
}
