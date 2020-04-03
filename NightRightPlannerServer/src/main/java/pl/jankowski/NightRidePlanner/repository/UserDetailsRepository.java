package pl.jankowski.NightRidePlanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jankowski.NightRidePlanner.entity.UserDetailsEntity;

//@RepositoryRestResource(exported = false)
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {
}
