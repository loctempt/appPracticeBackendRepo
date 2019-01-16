package apii.application_practice_2.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.print.Doc;
import java.util.List;

public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
    Doctor findByDoctorId(int doctorId);

    @Query("select distinct d.doctorDepartment from Doctor d")
    List<String> getDepartmentList();

//    @Query("select d from Doctor d where d.doctorDepartment=?1")
//    List<Doctor> findByDoctorDepartment(String doctorDepartment);
    List<Doctor> findByDoctorDepartment(String dept);
}
