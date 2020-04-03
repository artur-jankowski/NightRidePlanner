package pl.jankowski.NightRidePlanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.jankowski.NightRidePlanner.entity.UserEntity;

import java.util.Optional;

//@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u where u.username = :username")
    Optional<UserEntity> findUserByUsername(@Param("username") String name);
}
