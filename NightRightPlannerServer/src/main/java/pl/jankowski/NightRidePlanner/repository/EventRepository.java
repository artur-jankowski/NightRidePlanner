package pl.jankowski.NightRidePlanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.jankowski.NightRidePlanner.entity.EventEntity;

import java.util.List;

//@RepositoryRestResource(exported = false)
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @Query("SELECT e FROM EventEntity e WHERE e.group.id = :id")
    List<EventEntity> findByGroupId(@Param("id") Long id);
}
