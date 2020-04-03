package pl.jankowski.NightRidePlanner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Users")
@AllArgsConstructor
@NoArgsConstructor

public class UserEntity {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name="username", nullable = false)
    private String username;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_info_id", referencedColumnName = "id")
    private UserDetailsEntity userInfo;

    @Getter
    @Setter
    @Column(name="description")
    private String description;

    @Getter
    @Setter
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Users_in_groups",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") }
    )
    Set<GroupEntity> groups = new HashSet<>();

}
