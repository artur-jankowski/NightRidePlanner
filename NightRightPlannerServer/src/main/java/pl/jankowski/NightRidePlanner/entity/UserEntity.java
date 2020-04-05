package pl.jankowski.NightRidePlanner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
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
    private String username;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private UserDetailsEntity userInfo;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.ALL})
    Set<GroupEntity> groups = new HashSet<>();

}
