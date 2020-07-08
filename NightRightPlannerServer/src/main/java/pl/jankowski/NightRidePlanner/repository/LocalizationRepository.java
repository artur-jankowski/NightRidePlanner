package pl.jankowski.NightRidePlanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.jankowski.NightRidePlanner.entity.LocalizationEntity;

import java.util.List;

public interface LocalizationRepository extends JpaRepository<LocalizationEntity, Long> {
    @Query("SELECT e FROM LocalizationEntity e WHERE e.event.id = :id")
    List<LocalizationEntity> findByEventId(@Param("id") Long id);
}
