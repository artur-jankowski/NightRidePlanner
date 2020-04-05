package pl.jankowski.NightRidePlanner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
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
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @ManyToMany
    private Set<UserEntity> usersInGroup;

    @Getter
    @Setter
    @OneToMany(mappedBy = "group")
    private Set<EventEntity> events;
}
