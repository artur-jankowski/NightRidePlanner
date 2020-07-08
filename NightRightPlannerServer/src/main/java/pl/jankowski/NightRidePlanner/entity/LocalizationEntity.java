package pl.jankowski.NightRidePlanner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "localizations")
@AllArgsConstructor
@NoArgsConstructor
public class LocalizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Double latitude;

    @Getter
    @Setter
    private Double longitude;

    @Getter
    @Setter
    @ManyToOne
    private EventEntity event;
}
