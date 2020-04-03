package pl.jankowski.NightRidePlanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jankowski.NightRidePlanner.entity.EventEntity;

//@RepositoryRestResource(exported = false)
public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
