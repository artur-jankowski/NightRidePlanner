package pl.jankowski.NightRidePlanner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Groups")
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "groups")
    private Set<UserEntity> usersInGroup = new HashSet<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "group")
    private Set<EventEntity> events = new HashSet<>();
}
