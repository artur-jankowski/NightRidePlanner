package pl.jankowski.NightRidePlanner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import pl.jankowski.NightRidePlanner.util.Role;

import javax.persistence.*;
import java.util.HashSet;

@Entity
@Table(name = "UserDetailsEntity")
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsEntity {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    @Column(nullable = false)
    private HashSet<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
    }
}
