package apii.application_practice_2.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    @Query("select count(u) from User u where u.username=?1")
    Integer usersHavingUsername(String username);
}
