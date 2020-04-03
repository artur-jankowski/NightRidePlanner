package pl.jankowski.NightRidePlanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jankowski.NightRidePlanner.entity.GroupEntity;

//@RepositoryRestResource(exported = false)
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
}
