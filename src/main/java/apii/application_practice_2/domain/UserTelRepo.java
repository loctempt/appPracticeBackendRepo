package apii.application_practice_2.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTelRepo extends JpaRepository<UserTel, String> {
    UserTel findByUserTel(String tel);
}
