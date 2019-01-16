package apii.application_practice_2.controller;

import apii.application_practice_2.domain.*;
import apii.application_practice_2.utility.DateUtil;
import apii.application_practice_2.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/data_alter")
public class DataAlterController {

    private DoctorRepo doctorRepo;
    private DoctorScheduleRepo scheduleRepo;

    @Autowired
    DataAlterController(DoctorRepo doctorRepo, DoctorScheduleRepo scheduleRepo) {
        this.doctorRepo = doctorRepo;
        this.scheduleRepo = scheduleRepo;
    }

    // 添加新的预约记录
    @RequestMapping("/new_reservation")
    GeneralResponse handleNewReservation(@RequestBody Reservation reservation, HttpServletRequest request){

    }

    // 记录新的医生日程
    @RequestMapping("/new_schedule")
    GeneralResponse handleCreateSchedule(@RequestBody DoctorSchedule schedule) {
        try {
            scheduleRepo.save(schedule);
            int doctorId = schedule.getDoctorId();
            Doctor doctor = doctorRepo.findByDoctorId(doctorId);
            return GeneralResponse.success(String.format("已创建新日程：%s 医生， %s %s",
                    doctor.getDoctorName(),
                    DateUtil.parseDateObject(schedule.getDoctorOnDutyDate()),
                    DoctorSchedule.parseOnDutyTime(schedule.getDoctorOnDutyTime())
            ));
        } catch (Exception e) {
            return GeneralResponse.failure("创建新日程失败");
        }
    }

    // 记录新医生
    @RequestMapping("/new_doctor")
    GeneralResponse handleCreateDoctor(@RequestBody Doctor doctor) {
        try {
            doctorRepo.save(doctor);
            return GeneralResponse.success("已记录新医生：" + doctor.getDoctorName());
        } catch (Exception e) {
            return GeneralResponse.failure("记录新医生失败");
        }
    }

}
