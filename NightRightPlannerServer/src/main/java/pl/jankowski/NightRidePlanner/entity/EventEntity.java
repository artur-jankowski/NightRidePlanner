package pl.jankowski.NightRidePlanner.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jankowski.NightRidePlanner.util.EventType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Events")
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name="type")
    private EventType type;

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
    @ManyToOne
    @JoinColumn(name = "group", nullable = false)
    private GroupEntity group;

    @Getter
    @Setter
    @OneToMany
    private List<UserEntity> attendants = new ArrayList<>();


}
