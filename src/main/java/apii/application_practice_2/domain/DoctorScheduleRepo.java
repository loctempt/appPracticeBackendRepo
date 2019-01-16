package apii.application_practice_2.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DoctorScheduleRepo extends JpaRepository<DoctorSchedule, Integer> {
    List<DoctorSchedule> findByDoctorIdAndDoctorOnDutyDateBetweenOrderByDoctorOnDutyDateAscDoctorOnDutyTimeAsc(
            int doctorId, Date startDate, Date endDate);
    DoctorSchedule findByScheduleId(int scheduleId);
}
