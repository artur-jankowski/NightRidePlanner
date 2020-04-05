package pl.jankowski.NightRidePlanner.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jankowski.NightRidePlanner.util.EventType;

import javax.persistence.*;

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
    private EventType type;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @ManyToOne
    private GroupEntity group;


}
