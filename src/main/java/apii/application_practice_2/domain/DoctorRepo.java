package apii.application_practice_2.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
    Doctor findByDoctorId(int doctorId);
}
