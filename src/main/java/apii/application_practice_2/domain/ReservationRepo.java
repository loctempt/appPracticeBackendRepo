package apii.application_practice_2.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByUserId(int userId);

    @Query(nativeQuery = true, value = "select patient_name, doctor_name, doctor_on_duty_date, doctor_on_duty_time, doctor_positional_title from doctor join (select * from reservation join doctor_schedule using(schedule_id) where user_id=?1) a\n" +
            "\tusing(doctor_id)\n" +
            "    where doctor_on_duty_date >= ?2\n" +
            "    order by doctor_on_duty_date asc;")
    List<Object[]> getFutureReservationList(int userId, Date date);

    @Query(nativeQuery = true, value = "select patient_name, doctor_name, doctor_on_duty_date, doctor_on_duty_time, doctor_positional_title from doctor join (select * from reservation join doctor_schedule using(schedule_id) where user_id=?1) a\n" +
            "\tusing(doctor_id)\n" +
            "    where doctor_on_duty_date < ?2\n" +
            "    order by doctor_on_duty_date desc ;")
    List<Object[]> getHistoryReservationList(int userId, Date date);
}
